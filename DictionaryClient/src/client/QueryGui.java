/**
 * To implement GUI for the action, searching a word. 
 *
 * Author: Hui Yee Tan
 * Student ID: 1128168
 */

package client;

import java.awt.EventQueue;

import javax.swing.JTextArea;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.util.HashMap;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;

public class QueryGui extends JFrame {

	private final String DEFAULT_SEARCH = "Word to search"; 
	
	private Controller controller; 
	private JPanel contentPane;
	private JLabel statusLabel;
	private JTextField textField;
	private JLabel wordLabel;
	private JTextArea meaningLabel;

	/**
	 * Create the frame.
	 */
	public QueryGui(Controller controller) {
		this.controller = controller; 
		initialize(); 
	}
	
	public void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(252, 244, 226));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton home = new JButton("Home");
		home.setBackground(new Color(255, 255, 255));
		home.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		home.setBounds(0, -1, 71, 22);
		home.addActionListener(controller);
		home.setActionCommand("home");
		contentPane.add(home);
		
		statusLabel = new JLabel("");
		statusLabel.setFont(new Font("Monospaced", Font.PLAIN, 8));
		statusLabel.setVerticalAlignment(SwingConstants.TOP);
		statusLabel.setBounds(75, 65, 286, 14);
		contentPane.add(statusLabel);
		
		textField = new JTextField(DEFAULT_SEARCH);
		textField.setFont(new Font("Monospaced", Font.PLAIN, 11));
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setText("");
			}
		});
		textField.setBounds(75, 44, 191, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String word = textField.getText(); 
				
				if (word.equals(DEFAULT_SEARCH) || word.length() == 0) {
					statusLabel.setText("Error: No words have been entered.");
					return; 
				}
				
				try {
					controller.sendData("QUERY", word);
					JSONObject obj = controller.receiveJSON(); 
					String type = (String) obj.get("type"); 
					
					if (type.equals("ERROR")) {
						String error = (String) obj.get("message"); 
						
						if (error != null) {
							statusLabel.setText(error);
							wordLabel.setText("");
							meaningLabel.setText("");
							
						}else {
							statusLabel.setText("Error");
							wordLabel.setText("");
							meaningLabel.setText("");
						}
						
						
					}else if (type.equals("RESPONSE")) {
						
						String status = (String) obj.get("status"); 
						if (status.equals("Success")) {
							statusLabel.setText("Success");
							wordLabel.setText(word.toLowerCase()); 
							String meanings[] = ((String) obj.get("meaning")).split(";"); 
							String displayMeaning = ""; 
							for (String meaning: meanings) {
								displayMeaning += "-> " + meaning + "\n"; 
							}
							meaningLabel.setText(displayMeaning); 
							
						}else if (status.equals("Not found")) {
							statusLabel.setText("Error: The word does not exist.");
							wordLabel.setText("");
							meaningLabel.setText("");
						}
						
					}else {
						statusLabel.setText("Error");
						wordLabel.setText("");
						meaningLabel.setText("");
					}
					
				} catch (Exception exception) {
					String message = exception.getMessage(); 
					if (message != null) {
						statusLabel.setText(message);
					}else {
						statusLabel.setText("Error");
					}
				}
			}
		});
		
		btnNewButton.setBounds(276, 43, 85, 23);
		contentPane.add(btnNewButton);
		
		wordLabel = new JLabel("");
		wordLabel.setFont(new Font("Monospaced", Font.BOLD, 15));
		wordLabel.setBounds(22, 99, 105, 23);
		contentPane.add(wordLabel);
		
		meaningLabel = new JTextArea("");
		meaningLabel.setDisabledTextColor(new Color(0, 0, 0));
		meaningLabel.setSelectedTextColor(new Color(0, 0, 0));
		meaningLabel.setSelectionColor(new Color(0, 0, 0));
		meaningLabel.setForeground(new Color(0, 0, 0));
		meaningLabel.setBackground(new Color(252, 244, 226));
		meaningLabel.setLineWrap(true);
		meaningLabel.setEnabled(false);
		meaningLabel.setFont(new Font("Monospaced", Font.PLAIN, 10));
		meaningLabel.setBounds(20, 121, 306, 131);
		contentPane.add(meaningLabel);
	}
	
	public void open() {
		this.setVisible(true); 
	}
	
	public void close() {
		textField.setText(DEFAULT_SEARCH); 
		statusLabel.setText("");
		wordLabel.setText("");
		meaningLabel.setText("");
		this.setVisible(false);
	}

}
