/**
 * A connection request sent by the client.
 *
 * Author: Hui Yee Tan
 * Student ID: 1128168
 */

package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.net.SocketException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Request implements Runnable{
	
	private final String RESPONSE = "RESPONSE"; 
	private final String ERROR = "ERROR"; 
	private final String TYPE = "type";
	private final String STATUS = "status"; 
	private final String ACTION = "action";
	private final String WORD = "word";
	private final String MEANING = "meaning"; 
	private final String MESSAGE = "message";
	private final String SUCCESS = "Success"; 
	private final String NOT_FOUND = "Not found";
	private final String QUERY = "QUERY"; 
	private final String ADD = "ADD";
	private final String REMOVE = "REMOVE";
	private final String UPDATE = "UPDATE";
	
	private Socket clientSocket; 
	private Dictionary dictionary;
	private ServerGui serverGui;
	private BufferedReader in; 
	private BufferedWriter out;
	
	public Request (Socket clientSocket, Dictionary dictionary, ServerGui serverGui) {
		this.clientSocket = clientSocket; 
		this.dictionary = dictionary; 
		this.serverGui = serverGui; 
	}
	
	@Override
	public void run() {
		
		try {
		//Get the input/output streams for reading/writing data from/to the socket
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			String clientMsg = null;
			try {
				while((clientMsg = in.readLine()) != null) {
					JSONParser parser = new JSONParser(); 
					JSONObject data = (JSONObject) parser.parse(clientMsg); 
					if (data.get("type").equals("REQUEST")) {
						processRequest(data);
					}else {
						sendError("Error: Data received is not a request.");
					}
					
				}
				this.serverGui.closeConnection();
				
			} catch(SocketException socketEx){
				this.serverGui.closeConnection();
				String info = "Client " + clientSocket.getInetAddress() + " closed the connection.";
				this.serverGui.newInfo(info);
				
			} catch(ParseException parseEx) {
				sendError("Error: " + parseEx.getMessage()); 
			}
			
		} catch(IOException ioEx) {
			try {
				sendError("Error: " + ioEx.getMessage());
			} catch (IOException e) {
				
				e.printStackTrace();
			} 
		}
	}
	
	
	public void processRequest(JSONObject data) 
			throws IOException {
		String action = (String) data.get(ACTION);
		String word = (String) data.get(WORD);
		String requestInfo = "Client " + clientSocket.getInetAddress() + " "; 
		
		if(action.equals(QUERY)) {
			requestInfo += "Query request"; 
			String meanings = dictionary.queryWord(word); 
			if (meanings == null) {
				sendStatusMeaning(NOT_FOUND, meanings);
			}else {
				sendStatusMeaning(SUCCESS, meanings); 
			}
			
		}else if(action.equals(ADD)) {
			requestInfo += "Add request"; 
			String meanings = (String) data.get(MEANING); 
			String status = dictionary.addWord(word, meanings);
			sendStatus(status); 
		
		}else if(action.equals(REMOVE)) {
			requestInfo += "Remove request"; 
			String status = dictionary.removeWord(word); 
			sendStatus(status); 
		
		}else if(action.equals(UPDATE)) {
			requestInfo += "Update request"; 
			String meanings = (String) data.get(MEANING);
			String status = dictionary.updateWord(word, meanings);
			sendStatus(status); 
		}
		
		serverGui.newInfo(requestInfo);
		
	}
	
	public void sendStatusMeaning (String status, String meaning) 
			throws IOException {
		JSONObject data = new JSONObject(); 
		data.put(TYPE, RESPONSE); 
		data.put(STATUS, status);
		data.put(MEANING, meaning);
		
		StringWriter dataString = new StringWriter(); 
		data.writeJSONString(dataString);
		String jsonString = dataString.toString(); 
		out.write(jsonString);
		out.write("\n"); 
		out.flush();
	}
	
	public void sendStatus(String status)
			throws IOException {
		JSONObject data = new JSONObject(); 
		data.put(TYPE, RESPONSE); 
		data.put(STATUS, status);
		
		StringWriter dataString = new StringWriter(); 
		data.writeJSONString(dataString);
		String jsonString = dataString.toString(); 
		out.write(jsonString);
		out.write("\n"); 
		out.flush(); 
	}
	
	public void sendError(String errorMessage) 
			throws IOException {
		JSONObject data = new JSONObject(); 
		data.put(TYPE, ERROR); 
		data.put(MESSAGE, errorMessage);
		
		StringWriter dataString = new StringWriter(); 
		data.writeJSONString(dataString);
		String jsonString = dataString.toString(); 
		out.write(jsonString);
		out.write("\n"); 
		out.flush();
	}

}
