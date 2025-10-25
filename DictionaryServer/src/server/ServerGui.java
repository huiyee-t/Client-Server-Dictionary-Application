/**
 * To implement the GUI for the server side. 
 *
 * Author: Hui Yee Tan
 * Student ID: 1128168
 */

package server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import java.awt.CardLayout;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Font;
import javax.swing.border.EmptyBorder;

public class ServerGui {

	private JFrame frame;
	
	private ServerGui window;
	private int numConnections; 
	private JLabel numberLabel;
	private JTextArea requestInfo; 
	private String information = "";


	/**
	 * Create the application.
	 */
	public ServerGui() {
		this.numConnections = 0;
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(238, 232, 249));
		frame.getContentPane().setLayout(null);
		frame.setBounds(100, 100, 450, 300);
		frame.setTitle("SERVER");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel connectionLabel = new JLabel("No of client connected: ");
		connectionLabel.setFont(new Font("Monospaced", Font.PLAIN, 11));
		connectionLabel.setBounds(27, 23, 168, 14);
		frame.getContentPane().add(connectionLabel);
		
		numberLabel = new JLabel("New label");
		numberLabel.setFont(new Font("Monospaced", Font.PLAIN, 11));
		numberLabel.setBounds(202, 23, 49, 14);
		String connectionsString = String.valueOf(numConnections); 
		numberLabel.setText(connectionsString);
		frame.getContentPane().add(numberLabel);
		
		
		JLabel requestLabel = new JLabel("Clients requests information: ");
		requestLabel.setFont(new Font("Monospaced", Font.PLAIN, 11));
		requestLabel.setBounds(27, 50, 224, 14);
		frame.getContentPane().add(requestLabel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setForeground(new Color(255, 255, 255));
		scrollPane_1.setBorder(null);
		scrollPane_1.setBounds(26, 75, 331, 152);
		frame.getContentPane().add(scrollPane_1);
		
		requestInfo = new JTextArea();
		requestInfo.setBorder(null);
		scrollPane_1.setViewportView(requestInfo);
		requestInfo.setDisabledTextColor(new Color(0, 0, 0));
		requestInfo.setEnabled(false);
		requestInfo.setBackground(new Color(238, 232, 249));
		requestInfo.setLineWrap(true);
		requestInfo.setFont(new Font("Monospaced", Font.PLAIN, 10));
		
		
	}
	
	public void acceptConnection() {
		this.numConnections += 1;
		String connectionsString = String.valueOf(numConnections); 
		numberLabel.setText(connectionsString);
	}
	
	public void closeConnection() {
		this.numConnections -= 1;
		String connectionsString = String.valueOf(numConnections); 
		numberLabel.setText(connectionsString);
	}
	
	public void newInfo(String info) {
		information += info + "\n";
		requestInfo.setText(information);
	}
	
	
}
