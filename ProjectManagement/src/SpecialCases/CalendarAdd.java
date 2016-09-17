package SpecialCases;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.Calendar;
import objects.Event;
import server.Command;
import server.Command.CommandType;

public class CalendarAdd extends JPanel{

	private JPanel contents, namePanel, titlePanel, descriptPanel, topPanel, bottomPanel, labelPanel, datePanel;
	private JLabel nameLabel, titleLabel, descriptLabel, dateLabel; 
	private JTextField nameField, titleField;
	private JTextArea descriptField;
	private JButton createB;
	private JScrollPane dScroll;
	private String date;
	private CalendarProgram p;
	private int row;
	private int col;
	private HashMap<String, Integer> getMonth;
	private Calendar c;
	
	private Color baseColor = Color.decode("#45B3D7");
	private Color lightBase = Color.decode("#6BC6E4");
	private Color lightSelect = Color.decode("#9CDCF1");
	
	public CalendarAdd(String s, CalendarProgram p, Calendar c, int row, int col){
		super();
		setBackground(Color.decode("#45B3D7"));
		getMonth = new HashMap<String, Integer>();
		createMap();
		this.c = c;
		this.row = row;
		this.col = col;
		date = s;
		setLayout(new BorderLayout());
		datePanel = new JPanel();
		datePanel.setBackground(Color.decode("#45B3D7"));
		dateLabel = new JLabel("Event on " + date);
		dateLabel.setFont(dateLabel.getFont().deriveFont(Font.PLAIN, 24));
		dateLabel.setForeground(Color.WHITE);
		datePanel.add(dateLabel);
		
		titlePanel = new JPanel();
		titlePanel.setBackground(Color.decode("#45B3D7"));
		titleLabel = new JLabel("Event name: ");
		titleLabel.setForeground(Color.WHITE);
		titleField = new JTextField(20);
		titleField.setBackground(lightBase);
		titleField.setForeground(Color.white);
		titleField.setBorder(BorderFactory.createEmptyBorder());
		titlePanel.add(titleLabel);
		titlePanel.add(titleField);
		
		namePanel = new JPanel();
		namePanel.setBackground(Color.decode("#45B3D7"));
		nameLabel = new JLabel("Created by: ");
		nameLabel.setForeground(Color.WHITE);
		nameField = new JTextField(20);
		nameField.setBorder(BorderFactory.createEmptyBorder());
		nameField.setForeground(Color.white);
		nameField.setBackground(lightBase);
		namePanel.add(nameLabel);
		namePanel.add(nameField);
		
		topPanel = new JPanel();
		topPanel.setBackground(Color.decode("#45B3D7"));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		topPanel.add(datePanel);
		topPanel.add(titlePanel);
		topPanel.add(namePanel);
		
		labelPanel = new JPanel();
		labelPanel.setBackground(Color.decode("#45B3D7"));
		labelPanel.setLayout(new BorderLayout());
		descriptLabel = new JLabel("Description");
		descriptLabel.setForeground(Color.WHITE);
		labelPanel.add(descriptLabel, BorderLayout.WEST);
		topPanel.add(labelPanel);
		
		descriptField = new JTextArea();
		descriptField.setBorder(BorderFactory.createEmptyBorder());
		descriptField.setForeground(Color.WHITE);
		descriptField.setBackground(lightBase);
		dScroll = new JScrollPane();
		dScroll.setBorder(BorderFactory.createEmptyBorder());
		dScroll.setViewportView(descriptField);
		dScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.decode("#45B3D7"));
		createB = new JButton("Create Event");
		bottomPanel.add(createB);
		
		
		JPanel topContainer = new JPanel();
		topContainer.setBackground(Color.decode("#45B3D7"));
		topContainer.setLayout(new BorderLayout());
		topContainer.add(topPanel, BorderLayout.WEST);
		add(topContainer, BorderLayout.NORTH);
		add(dScroll, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
		
		setVisible(true);
		addActions();
		
	}
	
	public void addActions(){
		createB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				String createdBy = nameField.getText();
				String eventTitle = titleField.getText();
				String description = descriptField.getText();
				Event newEvent = new Event(createdBy, -1, date, eventTitle, description, row, col);
				
				//send command to server
				Command cmd = new Command();
				cmd.commandType = CommandType.Calendar;
				cmd.setGoods(newEvent);
				c.sendCommand(cmd);
				
				JPanel empty = new JPanel();
				empty.setBackground(Color.WHITE);
				c.setCalendarWindow(empty);
				
			}
		});
		
	}
	
	private void createMap(){
		String[] numMonths = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		Integer[] intMonths = {1,2,3,4,5,6,7,8,9,10,11,12};
		for(int i = 0; i < numMonths.length; i++){
			getMonth.put(numMonths[i], intMonths[i]);
		}
		
	}
	
}

