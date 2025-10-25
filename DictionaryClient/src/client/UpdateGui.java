/**
 * To implement GUI for the action, updating the word. 
 *
 * Author: Hui Yee Tan
 * Student ID: 1128168
 */

package client;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.json.simple.JSONObject;
import java.awt.Color;

public class UpdateGui extends JFrame {
	
	private final String DEFAULT_UPDATE = "Word to update"; 
	private final String DEFAULT_TEXT = "Meaning(s)\r\nIf the word "
			+ "has more than \r\none meaning, please \r\nseparate the meaning by "
			+ "\r\nusing the punctuation ; ";
	
	private JPanel contentPane;
	private Controller controller; 
	private JTextField wordField;
	private JButton updateButton;
	private JTextArea statusLabel;
	private JTextArea meaningField;

	/**
	 * Create the frame.
	 */
	public UpdateGui(Controller controller) {
		this.controller = controller; 
		this.initialize(); 
	}
	
	public void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(221, 235, 247));
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
		wordField.setBackground(new Color(255, 255, 255));
		wordField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				wordField.setText("");
			}
		});
		wordField.setText(DEFAULT_UPDATE);
		wordField.setBounds(78, 52, 188, 23);
		contentPane.add(wordField);
		wordField.setColumns(10);
		
		statusLabel = new JTextArea("");
		statusLabel.setBackground(new Color(221, 235, 247));
		statusLabel.setBorder(null);
		statusLabel.setDisabledTextColor(new Color(0, 0, 0));
		statusLabel.setEnabled(false);
		statusLabel.setFont(new Font("Monospaced", Font.PLAIN, 8));
		statusLabel.setBounds(78, 194, 284, 23);
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
		
		updateButton = new JButton("Update");
		updateButton.setBackground(new Color(255, 255, 255));
		updateButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		updateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String word = wordField.getText(); 
				String meaning = meaningField.getText(); 
				if ((word.equals(DEFAULT_UPDATE) && meaning.equals(DEFAULT_TEXT)) || 
					(word.length() == 0 && word.length() == 0)) {
					statusLabel.setText("Error: No words and meanings have been entered.");
					return; 
					
				}else if(word.equals(DEFAULT_UPDATE) || word.length() == 0) {
					statusLabel.setText("Error: No words have been entered."); 
					return; 
					
				}else if(meaning.equals(DEFAULT_TEXT) || meaning.length() == 0){
					statusLabel.setText("Error: No meanings have been entered.");
					return; 
				}
				
				try {
					controller.sendData("UPDATE", word, meaning); 
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
						}else if (status.equals("Not found")) {
							statusLabel.setText("Error: The word  does not exist"); 
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
		updateButton.setBounds(276, 52, 86, 140);
		contentPane.add(updateButton);
	}
	
	public void open() {
		this.setVisible(true); 
	}
	
	public void close() {
		wordField.setText(DEFAULT_UPDATE);
		meaningField.setText(DEFAULT_TEXT);
		statusLabel.setText("Output");
		this.setVisible(false);
	}
}
