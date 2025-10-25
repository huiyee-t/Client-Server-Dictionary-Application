/**
 * A worker thread.  
 *
 * Author: Hui Yee Tan
 * Student ID: 1128168
 */

package server;

import java.util.concurrent.BlockingQueue;

public class Worker extends Thread{

	private BlockingQueue<Request> requestQueue; 
	
	public Worker(BlockingQueue<Request> requestQueue) {
		this.requestQueue = requestQueue; 
	}
	

	
	public void run() {
		while (!Worker.interrupted()) {
			try {
				requestQueue.take().run();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
