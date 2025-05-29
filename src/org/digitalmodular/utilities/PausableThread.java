/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2024 Mark Jeronimus. All Rights Reversed.
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
	public static final int THREAD_STATUS_DISPOSE = 0;
	public static final int THREAD_STATUS_PAUSED  = 1;
	public static final int THREAD_STATUS_RUNNING = 2;

	private final PausableRunnable target;
	/**
	 * The number of milliseconds per run
	 */
	private       long             runningMillis;
	/**
	 * The number of milliseconds per pause
	 */
	private       long             pauseMillis;

	private int     currentStatus = THREAD_STATUS_PAUSED;
	private boolean running       = false;

	/**
	 * The Lock and Condition to synchronize operations.
	 */
	private final Lock      lock      = new ReentrantLock();
	private final Condition lockSleep = lock.newCondition();

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

		if (faster && currentStatus == THREAD_STATUS_RUNNING) {
			try {
				lock.lock();

				lockSleep.signalAll();
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

		if (faster && currentStatus == THREAD_STATUS_PAUSED) {
			try {
				lock.lock();

				lockSleep.signalAll();
			} finally {
				lock.unlock();
			}
		}
	}

	public void setRelativePriority(int difference) {
		setPriority(Math.min(MAX_PRIORITY, Math.max(MIN_PRIORITY, getPriority() - difference)));
	}

	public void start(int initialThreadStatus) {
		if (!running) {
			running = true;
			super.start();

			setThreadStatus(initialThreadStatus);
		}
	}

	public void setThreadStatus(int newStatus) {
		if (running) {
			if (currentStatus != newStatus) {
				// interrupt();
			}

			currentStatus = newStatus;
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

			while (currentStatus != THREAD_STATUS_DISPOSE) {
				switch (currentStatus) {
					case THREAD_STATUS_RUNNING: {
						lock.unlock();
						try {
							target.doStarting();
						} catch (Throwable ex) {
							ex.printStackTrace();
						}
						lock.lock();
						if (currentStatus == THREAD_STATUS_RUNNING) {
							long start = System.currentTimeMillis();
							while (currentStatus == THREAD_STATUS_RUNNING) {
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
										lockSleep.await(delay, TimeUnit.MILLISECONDS);
									} catch (InterruptedException ex) {
									}
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
					case THREAD_STATUS_PAUSED: {
						long start = System.currentTimeMillis();
						while (currentStatus == THREAD_STATUS_PAUSED) {
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
									lockSleep.await(delay, TimeUnit.MILLISECONDS);
								} catch (InterruptedException ex) {
								}
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

	@Override
	public synchronized void start() {
		throw new IllegalArgumentException("'initialThreadStatus' required");
	}
}
