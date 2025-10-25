/**
 * To implement the GUI of home page.  
 *
 * Author: Hui Yee Tan
 * Student ID: 1128168
 */

package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.JSplitPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Color;
import java.awt.Font;

public class HomeGui {

	private JFrame frame;
	private Controller controller; 

	public HomeGui(Controller controller) {
		this.controller = controller; 
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setForeground(new Color(190, 152, 133));
		frame.setBackground(new Color(247, 242, 238));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("CLIENT");
		frame.getContentPane().setLayout(new GridLayout(2, 2, 0, 0));
		
		JButton button1 = new JButton("QUERY");
		button1.setFont(new Font("Tempus Sans ITC", Font.BOLD, 15));
		button1.setForeground(new Color(0, 0, 0));
		button1.setBackground(new Color(250, 237, 203));
		button1.addActionListener(controller);
		button1.setActionCommand("query");
		
		JButton button2 = new JButton("ADD");
		button2.setFont(new Font("Tempus Sans ITC", Font.BOLD, 15));
		button2.setForeground(new Color(0, 0, 0));
		button2.setBackground(new Color(201, 228, 222));
		button2.addActionListener(controller);
		button2.setActionCommand("add");
		
		JButton button3 = new JButton("REMOVE");
		button3.setFont(new Font("Tempus Sans ITC", Font.BOLD, 15));
		button3.setForeground(new Color(0, 0, 0));
		button3.setBackground(new Color(242, 198, 222));
		button3.addActionListener(controller);
		button3.setActionCommand("remove");
		
		JButton button4 = new JButton("UPDATE");
		button4.setFont(new Font("Tempus Sans ITC", Font.BOLD, 15));
		button4.setForeground(new Color(0, 0, 0));
		button4.setBackground(new Color(198, 222, 241));
		button4.addActionListener(controller);
		button4.setActionCommand("update");
		
		frame.getContentPane().setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		frame.getContentPane().add(button1);
		frame.getContentPane().add(button2);
		frame.getContentPane().add(button3);
		frame.getContentPane().add(button4);
		
	}
	
	public void open() {
		this.frame.setVisible(true);
	}
	
	public void close() {
		this.frame.setVisible(false); 
	}
}
