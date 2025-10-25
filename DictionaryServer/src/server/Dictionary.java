/**
 * To implement all actions that could be performed on server side.  
 *
 * Author: Hui Yee Tan
 * Student ID: 1128168
 */

package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class Dictionary {
	
	private final String SUCCESS = "Success";
	private final String DUPLICATE = "Duplicate";
	private final String NOT_FOUND = "Not found";
	private HashMap<String, ArrayList<String>> words;
	//private FileWriter dictionary; 
	//private FileWriter newDictionary; 
	private String dictionaryName; 
	
	public Dictionary(String dictionaryName) {
		this.dictionaryName = dictionaryName; 
		File dictionaryFile = new File(dictionaryName);  
		
		try {
			dictionaryFile.createNewFile();
			
		} catch (IOException e) {
			e.printStackTrace(); 
		}
	}
	
	public synchronized String addWord(String word, String meaning) 
		 throws IOException {
		
		String found = queryWord(word); 
		if (found != null) {
			return DUPLICATE; 
		}
		
		meaning.replace("\n", "");
		FileWriter dictionary = new FileWriter(dictionaryName, true); 
		
		dictionary.write(word.toLowerCase() + ": ");
		dictionary.write(meaning);
		dictionary.write("\n");
		dictionary.flush(); 
		dictionary.close();
		
		return SUCCESS; 
	}
	
	
	public synchronized String queryWord(String word) 
			throws IOException {
		
		List<String> allData = Files.readAllLines(Paths.get(dictionaryName)); 
		String matchPattern = word.toLowerCase() + ":";  
		String meaning = null; 
		
		for(String data: allData) {
			if(data.startsWith(matchPattern, 0)) {
				meaning = data.substring(matchPattern.length()); 
			}
		}
		
		return meaning; 
	}
	
	
	public synchronized String removeWord(String word) 
			throws IOException {
		
		List<String> allData = Files.readAllLines(Paths.get(dictionaryName)); 
		FileWriter newDictionary = new FileWriter(dictionaryName, false); 
		String matchPattern = word.toLowerCase() + ":";
		boolean found = false; 
		
		for(String data: allData) {
			if (!(data.startsWith(matchPattern, 0))) {
				newDictionary.write(data + "\n"); 
			}else {
				found = true; 
			}
		}
		newDictionary.close();
		
		if (found) {
			return SUCCESS;
		}
		
		return NOT_FOUND; 
	}
	
	public synchronized String updateWord(String word, String meaning) 
			throws IOException{
		
		List<String> allData = Files.readAllLines(Paths.get(dictionaryName));
		FileWriter newDictionary = new FileWriter(dictionaryName, false);
		String matchPattern = word.toLowerCase() + ":";
		boolean found = false; 
		
		for(String data: allData) {
			if (data.startsWith(matchPattern, 0)) {
				newDictionary.write(word.toLowerCase() + ": "); 
				newDictionary.write(meaning + "\n");
				found = true;
				
			}else {
				newDictionary.write(data + "\n"); 
			}
		}
		newDictionary.close();
		
		if (found) {
			return SUCCESS;
		}
		
		return NOT_FOUND; 
	}
	
	
}
