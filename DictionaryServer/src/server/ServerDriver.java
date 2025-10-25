/**
 * An entry point for server program.
 *
 * Author: Hui Yee Tan
 * Student ID: 1128168
 */

package server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.net.ServerSocketFactory;

public class ServerDriver {
	private final static int PORT_INDEX = 0; 
	private final static int DICTIONARY_INDEX = 1; 
	private static WorkerPool workerPool = null;

	public static void main(String[] args) {
		
		ServerSocket listeningSocket = null;  
		 
		
		try {
			final int PORT = Integer.parseInt(args[PORT_INDEX]); 
			final String DICTIONARY = args[DICTIONARY_INDEX]; 
			final int NUM_THREADS = 10; 
			Dictionary dictionary = new Dictionary(DICTIONARY); 
			
			ServerSocketFactory socketFactory = ServerSocketFactory.getDefault();  
			workerPool = new WorkerPool(NUM_THREADS); 
			
			listeningSocket = socketFactory.createServerSocket(PORT); 
			listeningSocket.setReuseAddress(true);
			
			ServerGui serverGui = new ServerGui(); 
			serverGui.newInfo("Waiting for client connection-");
		
			// Wait for connections.
			while(true){
				Socket client = listeningSocket.accept();
				serverGui.newInfo("Client "+client.getInetAddress()+": Applying for connection!");
				serverGui.acceptConnection(); 
				Request request = new Request(client, dictionary, serverGui);
				workerPool.submitRequest(request);
			}
			
			
		}catch(ArrayIndexOutOfBoundsException e){
			System.err.println("Please enter server address followed by server port as command line arguments.");
		
		}catch(NumberFormatException e) {
			System.err.println("Please enter port number which contains digit only.");
		
		}catch(BindException e) {
			String error = e.getMessage();
			if (error != null) {
				System.err.println(error);
			}else {
				System.err.println("Error occured when binding address with a local address and port");
			}
		
		}catch(SocketException e) { 
			System.err.println("Error occured when creating or accessing the socket.");
		
		}catch(IOException e) {
			String error = e.getMessage();
			if (error != null) {
				System.err.println(error);
			}else {
				System.err.println("Error occured.");
			}
			
		} finally {
			workerPool.shutdown();
			if (listeningSocket != null) {
				try {
					listeningSocket.close();  
				}catch (IOException e) {
					e.printStackTrace(); 
				}
			}
			
 		}
		
	}
}
