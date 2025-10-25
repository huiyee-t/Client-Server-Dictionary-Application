/**
 * To implement GUI for the action, removing a word.  
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
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.Color;
import java.awt.Font;

public class RemoveGui extends JFrame {
	
	private String DEFAULT_TEXT = "Word to remove"; 

	private JPanel contentPane;
	private Controller controller; 
	private JTextField wordField;
	private JButton removeButton; 
	private JLabel statusLabel; 

	/**
	 * Create the frame.
	 */
	public RemoveGui(Controller controller) {
		this.controller = controller; 
		initialize(); 
	}
	
	public void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 231, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton home = new JButton("Home");
		home.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		home.setBackground(new Color(255, 255, 255));
		home.setBounds(0, 0, 70, 23);
		home.addActionListener(controller);
		home.setActionCommand("home");
		contentPane.add(home);
		
		wordField = new JTextField(DEFAULT_TEXT);
		wordField.setFont(new Font("Monospaced", Font.PLAIN, 11));
		wordField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				wordField.setText("");
			}
		});
		wordField.setBounds(72, 53, 192, 20);
		contentPane.add(wordField);
		wordField.setColumns(10);
		
		statusLabel = new JLabel("");
		statusLabel.setFont(new Font("Monospaced", Font.PLAIN, 8));
		statusLabel.setBounds(72, 71, 275, 14);
		contentPane.add(statusLabel);
		
		removeButton = new JButton("Remove");
		removeButton.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		removeButton.setBackground(new Color(255, 255, 255));
		removeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String word = wordField.getText(); 
				if (word.equals(DEFAULT_TEXT) || word.length() == 0) {
					statusLabel.setText("Error； No words have been entered.");
					return; 
				}
				
				try {
					controller.sendData("REMOVE", word);
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
						if(status.equals("Success")) {
							statusLabel.setText(status); 
						}else if(status.equals("Not found")) {
							statusLabel.setText("Error: The word does not exist.");
						}else {
							statusLabel.setText("Error");
						}
						
					}else {
						statusLabel.setText("Error");
					}
					
				} catch (Exception ex) {
					String message = ex.getMessage(); 
					if (message != null) {
						statusLabel.setText(message); 
					}else {
						statusLabel.setText("Error"); 
					} 
				}
				
			}
		});
		removeButton.setBounds(274, 52, 90, 23);
		contentPane.add(removeButton);
	}
	
	public void open() {
		this.setVisible(true); 
	}
	
	public void close() {
		wordField.setText(DEFAULT_TEXT);
		statusLabel.setText("");
		this.setVisible(false);
	}

}
