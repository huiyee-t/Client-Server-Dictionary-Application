/**
 * To implement a controller which allows user to switch between user interface. 
 *
 * Author: Hui Yee Tan
 * Student ID: 1128168
 */

package client;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.HashMap;

public class Controller implements ActionListener{
	
	private final static int QUERY = 0; 
	private final static int ADD = 1; 
	private final static int REMOVE = 2; 
	private final static int UPDATE = 3; 
	private final String REQUEST = "REQUEST"; 
	private final String TYPE = "type"; 
	private final String ACTION = "action"; 
	private final String WORD = "word";
	private final String STATUS = "status"; 
	private final String SUCCESS = "Success"; 
	private final String MEANING = "meaning"; 
	
	private HomeGui window; 
	private Socket socket; 
	private QueryGui queryFrame = null; 
	private AddGui addFrame = null; 
	private RemoveGui removeFrame = null; 
	private UpdateGui updateFrame = null; 
	private Boolean[] isOpened = {false, false, false, false};  

	public Controller(Socket socket) {
		window = new HomeGui(this); 
		this.socket = socket; 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("query")) {
			this.window.close();
			if (queryFrame == null) {
				queryFrame = new QueryGui(this); 
			}
			isOpened[QUERY] = true; 
			queryFrame.open();
			
		}else if(e.getActionCommand().equals("add")) {
			this.window.close();
			if (addFrame == null) {
				addFrame = new AddGui(this); 
			}
			isOpened[ADD] = true; 
			addFrame.open();
			
		}else if(e.getActionCommand().equals("remove")) {
			this.window.close();
			if (removeFrame == null) {
				removeFrame = new RemoveGui(this); 
			}
			isOpened[REMOVE] = true; 
			removeFrame.open(); 
			
		}else if(e.getActionCommand().equals("update")) {
			this.window.close();
			if (updateFrame == null) {
				updateFrame = new UpdateGui(this); 
			}
			isOpened[UPDATE] = true; 
			updateFrame.open(); 
			
		}else if (e.getActionCommand().equals("home")) {
			
			if (isOpened[QUERY]) {
				queryFrame.close();
				isOpened[QUERY]= false; 
			}else if (isOpened[ADD]) {
				addFrame.close(); 
				isOpened[ADD] = false; 
			}else if (isOpened[REMOVE]) {
				removeFrame.close(); 
				isOpened[REMOVE] = false; 
			}else if (isOpened[UPDATE]) {
				updateFrame.close(); 
				isOpened[UPDATE] = false; 
			}
			this.window.open();
		}
	}
	
	public void sendData(String action, String word) 
			throws UnsupportedEncodingException, IOException {
		
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		InputStreamReader in = new InputStreamReader(socket.getInputStream(), "UTF-8"); 
		
		JSONObject data = new JSONObject(); 
		data.put(TYPE, REQUEST);
		data.put(ACTION, action); 
		data.put(WORD, word); 
		
		StringWriter dataString = new StringWriter(); 
		data.writeJSONString(dataString);
		String jsonString = dataString.toString(); 
		out.write(jsonString);
		out.write("\n"); 
		out.flush();
		
	}
	
	public void sendData(String action, String word, String meaning) 
			throws UnsupportedEncodingException, IOException {
		
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		JSONObject data = new JSONObject(); 
		data.put(TYPE, REQUEST);
		data.put(ACTION, action); 
		data.put(WORD, word);
		data.put(MEANING, meaning);
		
		StringWriter dataString = new StringWriter(); 
		data.writeJSONString(dataString);
		String jsonString = dataString.toString(); 
		out.write(jsonString);
		out.write("\n"); 
		out.flush();
		
	}
	
	
	public JSONObject receiveJSON() 
			throws UnsupportedEncodingException, IOException, ParseException {
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
		String objString = in.readLine();
		JSONParser parser = new JSONParser(); 
		JSONObject obj = (JSONObject) parser.parse(objString);
		
		return obj; 
	}
	

}
