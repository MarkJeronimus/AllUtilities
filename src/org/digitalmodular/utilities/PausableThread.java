/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2019 Mark Jeronimus. All Rights Reversed.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AllUtilities. If not, see <http://www.gnu.org/licenses/>.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.digitalmodular.utilities;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Mark Jeronimus
 */
// Created 2005-10-23
@Deprecated
public class PausableThread extends Thread {
	public static final int THREADSTATUS_DISPOSE = 0;
	public static final int THREADSTATUS_PAUSED  = 1;
	public static final int THREADSTATUS_RUNNING = 2;

	private PausableRunnable target;
	/**
	 * The number of milliseconds per run
	 */
	private long             runningMillis;
	/**
	 * The number of milliseconds per pause
	 */
	private long             pauseMillis;

	private int     currentStatus = PausableThread.THREADSTATUS_PAUSED;
	private boolean running       = false;

	/**
	 * The Lock and Condition to synchronize operations.
	 */
	private final Lock      lock      = new ReentrantLock();
	private final Condition locksleep = lock.newCondition();

	/**
	 * if runningMillis==0 then maximum speed will be approached without excessive CPU demand (uses yield() instead of
	 * sleep()).
	 *
	 * @param runningMillis Run interval
	 * @param pauseMillis   Pause interval
	 * @param target        the PausableRunnable
	 */
	public PausableThread(long runningMillis, long pauseMillis, PausableRunnable target) {
		super(PausableThread.class.getSimpleName());
		setRunningMillis(runningMillis);
		setPauseMillis(pauseMillis);
		this.target = target;
	}

	public long getRunningMillis() {
		return runningMillis;
	}

	public void setRunningMillis(long runningMillis) {
		boolean faster = this.runningMillis > runningMillis;

		this.runningMillis = runningMillis;

		if (faster && currentStatus == THREADSTATUS_RUNNING) {
			try {
				lock.lock();

				locksleep.signalAll();
			} finally {
				lock.unlock();
			}
		}
	}

	public long getPauseMillis() {
		return pauseMillis;
	}

	public void setPauseMillis(long pauseMillis) {
		boolean faster = this.pauseMillis > pauseMillis;

		this.pauseMillis = pauseMillis;

		if (faster && currentStatus == THREADSTATUS_PAUSED) {
			try {
				lock.lock();

				locksleep.signalAll();
			} finally {
				lock.unlock();
			}
		}
	}

	public void setRelativePriority(int difference) {
		super.setPriority(
				Math.min(Thread.MAX_PRIORITY, Math.max(Thread.MIN_PRIORITY, super.getPriority() - difference)));
	}

	public void start(int initialThreadStatus) {
		if (!running) {
			running = true;
			super.start();

			setThreadStatus(initialThreadStatus);
		}
	}

	public void setThreadStatus(int newstatus) {
		if (running) {
			if (currentStatus != newstatus) {
				// interrupt();
			}

			currentStatus = newstatus;
		}
	}

	public int getThreadStatus() {
		return currentStatus;
	}

	public boolean isRunning() {
		return running;
	}

	public void waitForDispose() {
		while (running) {
			Thread.yield();
		}
	}

	@Override
	public void run() {
		try {
			target.doInitialize();
		} catch (Throwable ex) {
			ex.printStackTrace();
		}

		try {
			lock.lock();

			while (currentStatus != PausableThread.THREADSTATUS_DISPOSE) {
				switch (currentStatus) {
					case PausableThread.THREADSTATUS_RUNNING: {
						lock.unlock();
						try {
							target.doStarting();
						} catch (Throwable ex) {
							ex.printStackTrace();
						}
						lock.lock();
						if (currentStatus == PausableThread.THREADSTATUS_RUNNING) {
							long start = System.currentTimeMillis();
							while (currentStatus == PausableThread.THREADSTATUS_RUNNING) {
								lock.unlock();
								try {
									target.doRunning();
								} catch (Throwable ex) {
									ex.printStackTrace();
								}
								lock.lock();
								long time  = System.currentTimeMillis();
								long delay = Math.max(0, runningMillis - time + start);
								if (delay > 0) {
									try {
										locksleep.await(delay, TimeUnit.MILLISECONDS);
									} catch (InterruptedException ex) {}
								} else {
									Thread.yield();
								}
								start = time + delay;
							}

							lock.unlock();
							try {
								target.doStopping();
							} catch (Throwable ex) {
								ex.printStackTrace();
							}
							lock.lock();
						}
						break;
					}
					case PausableThread.THREADSTATUS_PAUSED: {
						long start = System.currentTimeMillis();
						while (currentStatus == PausableThread.THREADSTATUS_PAUSED) {
							lock.unlock();
							try {
								target.doPause();
							} catch (Throwable ex) {
								ex.printStackTrace();
							}
							lock.lock();
							long time  = System.currentTimeMillis();
							long delay = Math.max(0, pauseMillis - time + start);
							if (delay > 0) {
								try {
									locksleep.await(delay, TimeUnit.MILLISECONDS);
								} catch (InterruptedException ex) {}
							} else {
								Thread.yield();
							}
							start = time + delay;
						}
						break;
					}
				}
			}
		} finally {
			lock.unlock();
			running = false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void start() {
		throw new IllegalArgumentException("'initialThreadStatus' required");
	}
}
