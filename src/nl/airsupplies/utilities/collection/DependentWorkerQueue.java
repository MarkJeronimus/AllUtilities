package nl.airsupplies.utilities.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A set of workers, each with an optional serial dependence on other workers. Workers are either eligible or blocked.
 * Blocked workers depend on another worker to be finished, at which case it can become eligible. When retrieving a
 * worker, only an eligible worker will be returned. If there are none, it will block until there is one.
 * <p>
 * It is neither a {@link Set} nor a {@link BlockingQueue} because most of their methods don't have meaning in this
 * context. It also doesn't scale well if a worker depends on more than a couple of other workers.
 * <p>
 * This container should have negligible cost for small numbers of workers (up to a hundred) but doesn't scale to very
 * high numbers.
 *
 * @param <V> the result type of the worker
 * @author Mark Jeronimus
 */
// Created 2015-08-28
public class DependentWorkerQueue<V> {

	Queue<List<Callable<V>>>         dependencyQueue = new LinkedList<>();
	BlockingQueue<DependentCallable> eligibleQueue   = new LinkedBlockingQueue<>();

	public synchronized void clear() {
		dependencyQueue.clear();
		eligibleQueue.clear();
	}

	/**
	 * Adds a worker with no dependencies. This worker will be immediately eligible.
	 */
	public synchronized void addWorker(Callable<V> worker) {
		eligibleQueue.add(new DependentCallable(worker));
	}

	/**
	 * Adds a worker which will only become eligible after all the dependent workers finish.
	 *
	 * @param worker       the worker
	 * @param dependencies workers that must all finish before the worker becomes eligible
	 */
	public synchronized void addWorker(Callable<V> worker, List<Callable<V>> dependencies) {
		List<Callable<V>> dependencyList = new ArrayList<>();
		dependencyList.add(worker);
		dependencyList.addAll(dependencies);
		dependencyQueue.add(dependencyList);
	}

	public synchronized boolean hasEligibleWorkers() {
		return !eligibleQueue.isEmpty();
	}

	public Callable<V> takeEligibleworker() throws InterruptedException {
		return eligibleQueue.take();
	}

	public synchronized int size() {
		return dependencyQueue.size() + eligibleQueue.size();
	}

	public synchronized boolean isEmpty() {
		return dependencyQueue.isEmpty() && eligibleQueue.isEmpty();
	}

	/**
	 * Removes the specified worker from all blocked worker's dependencies. Any blocked worker that as a result of this
	 * process had all it's dependencies removed will be transferred to the eligible-queue.
	 *
	 * @param dependency the worker that finished, and which other workers might be waiting for.
	 */
	synchronized void releaseBlockedWorkers(Callable<V> dependency) {
		Iterator<List<Callable<V>>> iter = dependencyQueue.iterator();
		while (iter.hasNext()) {
			// Get the worker and it's dependencies
			List<Callable<V>> worker = iter.next();

			// Remove our dependency
			worker.remove(dependency);

			// Transfer worker to eligible queue if it has no dependencies left
			if (worker.size() == 1) {
				iter.remove();
				eligibleQueue.offer(new DependentCallable(worker.get(0)));
			}
		}
	}

	private class DependentCallable implements Callable<V> {

		private final Callable<V> worker;

		public DependentCallable(Callable<V> worker) {
			this.worker = worker;
		}

		@Override
		public V call() throws Exception {
			V result = worker.call();
			releaseBlockedWorkers(worker);
			return result;
		}
	}
}
