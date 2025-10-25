/**
 * To implement GUI for the action, adding a word. 
 *
 * Author: Hui Yee Tan
 * Student ID: 1128168
 */

package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.simple.JSONObject;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.Color;

public class AddGui extends JFrame {
	
	private final String DEFAULT_ADD = "Word to add"; 
	private final String DEFAULT_TEXT = "Meaning(s)\r\nIf the word "
			+ "has more than \r\none meaning, please \r\nseparate the meaning by "
			+ "\r\nusing the punctuation ; ";
	
	private Controller controller; 
	private JPanel contentPane;
	private JTextField wordField;
	private JButton addButton;
	private JTextArea statusLabel;
	private JTextArea meaningField; 

	/**
	 * Create the frame.
	 */
	public AddGui(Controller controller) {
		this.controller = controller; 
		initialize(); 
	}
	
	public void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(220, 237, 233));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton home = new JButton("Home");
		home.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		home.setBackground(new Color(255, 255, 255));
		home.setBounds(0, 0, 77, 23);
		home.addActionListener(controller);
		home.setActionCommand("home");
		contentPane.add(home);
		
		wordField = new JTextField();
		wordField.setFont(new Font("Monospaced", Font.PLAIN, 11));
		wordField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				wordField.setText("");
			}
		});
		wordField.setText(DEFAULT_ADD);
		wordField.setBounds(78, 52, 188, 23);
		contentPane.add(wordField);
		wordField.setColumns(10);
		
		statusLabel = new JTextArea("");
		statusLabel.setDisabledTextColor(new Color(0, 0, 0));
		statusLabel.setSelectionColor(new Color(0, 0, 0));
		statusLabel.setBorder(null);
		statusLabel.setBackground(new Color(220, 237, 233));
		statusLabel.setEnabled(false);
		statusLabel.setFont(new Font("Monospaced", Font.PLAIN, 8));
		statusLabel.setBounds(78, 194, 255, 23);
		contentPane.add(statusLabel);
		
		meaningField = new JTextArea();
		meaningField.setLineWrap(true);
		meaningField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				meaningField.setText("");
			}
		});
		meaningField.setFont(new Font("Monospaced", Font.PLAIN, 11));
		meaningField.setText(DEFAULT_TEXT);
		meaningField.setBounds(78, 80, 188, 112);
		contentPane.add(meaningField);
		
		addButton = new JButton("Add");
		addButton.setBackground(new Color(255, 255, 255));
		addButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		addButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String word = wordField.getText(); 
				String meaning = meaningField.getText(); 
				if ((word.equals(DEFAULT_ADD) && meaning.equals(DEFAULT_TEXT)) || 
					(word.length() == 0 && meaning.length() == 0)) {
					statusLabel.setText("Error: No words and meanings have been entered.");
					return; 
					
				}else if(word.equals(DEFAULT_ADD) || word.length() == 0) {
					statusLabel.setText("Error: No words have been entered."); 
					return; 
					
				}else if(meaning.equals(DEFAULT_TEXT) || meaning.length() == 0){
					statusLabel.setText("Error: No meanings have been entered.");
					return; 
				}
				
				for(char character: word.toCharArray()) {
					if (!('a' <= character && character <= 'z' 
						 || 'A' <= character && character <= 'Z')) {
						statusLabel.setText("Error: Word entered is invalid.");
						return; 
					}
				}
				
				try {
					controller.sendData("ADD", word, meaning); 
					JSONObject obj = controller.receiveJSON(); 
					String type = (String) obj.get("type"); 
					
					if (type.equals("ERROR")) {
						String message = (String) obj.get("message"); 
						if (message != null) {
							statusLabel.setText("Error"); 
						}else {
							statusLabel.setText(message); 
						}
						
					}else if (type.equals("RESPONSE")) {
						String status = (String) obj.get("status"); 
						if (status.equals("Success")) {
							statusLabel.setText(status); 
							
						}else if (status.equals("Duplicate")) {
							statusLabel.setText("Error: Duplicate"); 
							
						}
						
					}else {
						statusLabel.setText("Error");
					}
					
				}catch (Exception ex) {
					String message = ex.getMessage(); 
					if (message != null) {
						statusLabel.setText(message); 
					}else {
						statusLabel.setText("Error"); 
					}
				}
			}
		});
		addButton.setBounds(276, 52, 57, 140);
		contentPane.add(addButton);
	}
	
	public void open() {
		this.setVisible(true); 
	}
	
	public void close() {
		wordField.setText(DEFAULT_ADD);
		meaningField.setText(DEFAULT_TEXT);
		statusLabel.setText("");
		this.setVisible(false);
	}
	
}
