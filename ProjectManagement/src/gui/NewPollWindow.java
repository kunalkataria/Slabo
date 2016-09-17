package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import CustomUI.IconButton;
import client.Polls;
import objects.Poll;
import objects.PollItem;
import objects.User;

public class NewPollWindow extends JPanel{

	private static final long serialVersionUID = 1;

	private Polls poll;
	private NewPollWindow npw = this;
	
	private JPanel signUpPane, buttonPane;
	
	private JLabel titleL, optionsL;
	private JTextField titleText;
	private Vector <JTextField> options;
	private JScrollPane optionsScroll;
	
	private JPanel optionsP;
	private JButton moreOptions;
	private IconButton createB;
	private Color baseColor = Color.decode("#FF9B3E");
	private Color lightBase = Color.decode("#FFAD61");
	
	NewPollWindow(PollsGUI pGUI, Polls p){
		poll = p;
		initiateVariables();
		createGUI();
		addActions();
		setVisible(true);
	}
	
	private void initiateVariables(){
		signUpPane = new JPanel();
		buttonPane = new JPanel();
		signUpPane.setBackground(baseColor);
		buttonPane.setBackground(baseColor);
		titleL = new JLabel("Title:");
		titleL.setFont(titleL.getFont().deriveFont(Font.PLAIN, 24));
		titleL.setBackground(lightBase);
		titleL.setBorder(BorderFactory.createEmptyBorder());
		titleL.setForeground(Color.white);
		optionsL = new JLabel("Options:");
		optionsL.setFont(optionsL.getFont().deriveFont(Font.PLAIN, 24));
		optionsL.setBackground(lightBase);
		optionsL.setBorder(BorderFactory.createEmptyBorder());
		optionsL.setForeground(Color.white);
		
		titleText = new JTextField(30);
		titleText.setBorder(BorderFactory.createLineBorder(baseColor, 1));
		titleText.setForeground(Color.white);
		titleText.setBackground(lightBase);
		
		options = new Vector<JTextField>();
		JTextField jtf = new JTextField();
		jtf.setBorder(BorderFactory.createLineBorder(baseColor, 1));
		jtf.setForeground(Color.white);
		jtf.setBackground(lightBase);
		JTextField jtf2 = new JTextField();
		jtf2.setBorder(BorderFactory.createLineBorder(baseColor, 1));
		jtf2.setForeground(Color.white);
		jtf2.setBackground(lightBase);
		options.add(jtf);
		options.add(jtf2);
		
		try {
			createB = new IconButton("icons/addbutton.png", "Create Poll!", lightBase, null);
			createB.doesHoverText(false);;
			createB.setPreferredSize(new Dimension(200,50));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		moreOptions = new JButton("add more options");
		moreOptions.setBackground(lightBase);
		moreOptions.setForeground(Color.white);
		moreOptions.setPreferredSize(new Dimension(150,30));
		moreOptions.setBorder(BorderFactory.createLineBorder(Color.white, 1));
		optionsP = new JPanel();
		optionsP.setBorder(BorderFactory.createEmptyBorder());
		optionsP.setBackground(baseColor);
		optionsP.setLayout(new BoxLayout(optionsP, BoxLayout.Y_AXIS));
		optionsP.add(options.elementAt(0));
		optionsP.add(options.elementAt(1));
		optionsScroll = new JScrollPane(optionsP);
		optionsScroll.setBorder(BorderFactory.createEmptyBorder());
		optionsScroll.setBackground(baseColor);
	}
	
	private void createGUI(){
		
		signUpPane.setLayout(new BoxLayout(signUpPane, BoxLayout.Y_AXIS));
		JPanel titlePanel = new JPanel();
		//itlePanel.setLayout(new BorderLayout());
		titlePanel.setBackground(baseColor);
		titlePanel.add(titleL);
		
		JPanel textPanel = new JPanel();
		textPanel.setBackground(baseColor);
		textPanel.add(titleText);
		
		
		JPanel optionLPanel = new JPanel();
		optionLPanel.setLayout(new BorderLayout());
		optionLPanel.setBackground(baseColor);
		optionLPanel.add(optionsL, BorderLayout.NORTH);
		
		
		JPanel optionPanel = new JPanel();
		optionPanel.setBackground(baseColor);
		optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
		optionPanel.add(optionsScroll);
		JPanel alignPanel = new JPanel();
		alignPanel.setLayout(new BorderLayout());
		alignPanel.setBackground(baseColor);
		alignPanel.add(optionPanel, BorderLayout.NORTH);
		
		JScrollPane temp = new JScrollPane();
		temp.setViewportView(alignPanel);
		optionLPanel.add(temp, BorderLayout.CENTER);
		
		JPanel moreOptionsPanel = new JPanel();
		moreOptionsPanel.add(moreOptions);
		optionLPanel.add(moreOptions, BorderLayout.SOUTH);
		
		
		signUpPane.add(titlePanel);
		signUpPane.add(textPanel);
		signUpPane.add(optionLPanel);
		//moreOptionsPanel.setLayout();
		/*
		signUpPane.add(titleL);
		signUpPane.add(titleText);
		signUpPane.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(3,3,3,3);
		signUpPane.add(titleL, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.ipadx = 120;
		signUpPane.add(titleText, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.ipadx = 0;
		signUpPane.add(optionsL, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridheight = 4;
		gbc.ipady = 200;
		gbc.ipadx = 120;
		signUpPane.add(optionsScroll, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.ipady = 0;
		gbc.ipadx = 0;
		
		
		signUpPane.add(moreOptions, gbc);*/
		JLabel createPoll = new JLabel("Create Poll!    ");
		createPoll.setFont(createPoll.getFont().deriveFont(Font.PLAIN, 24));
		createPoll.setForeground(Color.white);
		createPoll.setBackground(baseColor);
		createPoll.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
		buttonPane.add(createPoll);
		buttonPane.add(createB);
		buttonPane.setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 20));
		
		this.setLayout(new BorderLayout());
		add(signUpPane, BorderLayout.CENTER);
		add(buttonPane, BorderLayout.SOUTH);
	}
	
	private void addActions(){
		moreOptions.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField jtf = new JTextField();
				jtf.setBorder(BorderFactory.createLineBorder(baseColor, 1));
				jtf.setForeground(Color.white);
				jtf.setBackground(lightBase);
				options.addElement(jtf);
				int i = options.size();
				optionsP.add(options.elementAt(i-1));
				revalidate();
			}
			
		});
		
		createB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int nonempty = 0;
				String title = titleText.getText();
				Vector<PollItem> ops = new Vector<PollItem>();
				for(int i = 0 ; i < options.size(); i++){
					String toAdd = options.elementAt(i).getText();
					if (!toAdd.equals("")){
						PollItem convertToAdd = new PollItem(toAdd);
						ops.addElement(convertToAdd);
						nonempty++;
					}
				}
				//checks if there are enough options and a title
				if (nonempty > 1 && !title.equals("")){
					User currUser = poll.getUser();
					Poll toReturn = new Poll(currUser.getUsername(), title, -1, ops);
					//send to server
					poll.sendGoods(toReturn);
				}
				else{
					JOptionPane.showMessageDialog(npw, "Must include a title and at least 2 options"
							, "Create Poll Failed", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
	}	
}
