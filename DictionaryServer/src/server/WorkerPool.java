/**
 * A worker pool.  
 *
 * Author: Hui Yee Tan
 * Student ID: 1128168
 */

package server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class WorkerPool {
	
	private BlockingQueue<Request> requestQueue; 
	private Worker[] workers; 
	
	public WorkerPool (int numThreads) {
		requestQueue = new LinkedBlockingQueue<>(); 
		workers = new Worker[numThreads]; 
		
		for(Thread worker: workers) {
			worker = new Worker(requestQueue); 
			worker.start();
		}
	}
	
	public void submitRequest(Request request) {
		requestQueue.add(request); 
	}
	
	public void shutdown() {
		for(Thread worker: workers) {
			worker.interrupt();
		} 
	}
}
