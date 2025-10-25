/**
 * An entry point for client program. 
 *
 * Author: Hui Yee Tan
 * Student ID: 1128168
 */

package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	
	private final static int ADDRESS_INDEX = 0; 
	private final static int PORT_INDEX = 1; 
	private static Controller controller; 
	
	public static void main(String[] args) {
		Socket socket = null;
		
		try {
			final String SERVER_ADDRESS = args[ADDRESS_INDEX];
			final int PORT = Integer.parseInt(args[PORT_INDEX]); 
			socket = new Socket(SERVER_ADDRESS, PORT);
			System.out.println("Connection established");
			
			InputStreamReader in =  new InputStreamReader(socket.getInputStream(), "UTF-8"); 
			controller = new Controller(socket);
			
		    
		}catch (ArrayIndexOutOfBoundsException e){
			System.err.println("Please enter server address followed by server port as command line arguments.");
		
		}catch (NumberFormatException e) {
			System.err.println("Please enter port number which contains digit only.");
			
		}catch(ConnectException e) {
			System.err.println("There is no service listening on the port, " + args[PORT_INDEX] + ".");
			
		}catch (UnknownHostException e){
			System.err.println("The IP address, " + args[ADDRESS_INDEX] + " could not be determined.");
		
		}catch (Exception e) {
			String error = e.getMessage();
			if (error != null) {
				System.err.println(error);
			}else {
				System.err.println("Error occured.");
			}
		}

	}

}
