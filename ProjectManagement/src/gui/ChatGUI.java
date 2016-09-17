package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import CustomUI.CoolScrollBarUI;
import CustomUI.IconButton;
import client.Chat;
import client.Project;
import objects.ChatMessage;

public class ChatGUI extends JPanel{
	
	private static final long serialVersionUID = 8652194416332785918L;
	private JScrollPane jsp;
	private JTextArea chatArea;
	private JTextField inputMessage;
	private IconButton sendMessage;
	private Color baseColor = Color.black;
	private Color panelColor = Color.DARK_GRAY;
	
	private Chat chat;
	
	public ChatGUI(Chat c){
		super();
		chat = c;
		instantiateComp();
		createGUI();
		addActions();
		if(!c.getProject().isLoggedIn()){
			setGuestMode();
		}
	}
	
	private void instantiateComp(){
		setBackground(baseColor);
		jsp = new JScrollPane();
		jsp.getVerticalScrollBar().setUI(new CoolScrollBarUI());
		jsp.setBackground(panelColor);
		jsp.setViewportBorder(BorderFactory.createEmptyBorder());
		jsp.setBorder(BorderFactory.createEmptyBorder());
		chatArea = new JTextArea();
		chatArea.setBackground(panelColor);
		chatArea.setForeground(Color.white);
		chatArea.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		chatArea.setLineWrap(true);
		chatArea.setEditable(false);
		jsp.setViewportView(chatArea);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		inputMessage = new JTextField();
		inputMessage.setBorder(BorderFactory.createEmptyBorder());
		inputMessage.setBackground(panelColor);
		inputMessage.setForeground(Color.white);
		inputMessage.setSize(getWidth(), 100);
		try {
			sendMessage = new IconButton("icons/message.png", "Send!", Color.gray, null);
			sendMessage.setPreferredSize(new Dimension(70, 30));
			sendMessage.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createGUI(){
		setLayout(new BorderLayout());
		JLabel pChat = new JLabel("Project Chat");
		pChat.setFont(pChat.getFont().deriveFont(Font.PLAIN, 24));
		pChat.setForeground(Color.white);
		pChat.setBorder(BorderFactory.createEmptyBorder());
		add(pChat, BorderLayout.NORTH);
		add(jsp, BorderLayout.CENTER);
		
		Box hBox = Box.createHorizontalBox();
		hBox.add(inputMessage);
		hBox.add(sendMessage);
		hBox.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		add(hBox, BorderLayout.SOUTH);
		
		setBorder(BorderFactory.createEmptyBorder(Project.BORDER, Project.BORDER, Project.BORDER, Project.BORDER));
		
		setVisible(true);
	}
	
	private void addActions(){
		sendMessage.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				sendMessage();
			}
		});
		inputMessage.addKeyListener(new KeyAdapter(){

			@Override
			public void keyPressed(KeyEvent k) {
				if(k.getKeyCode() == KeyEvent.VK_ENTER){
					sendMessage();
				}
			}
			
		});
	}
	
	private void setGuestMode(){
		inputMessage.setEditable(false);
		sendMessage.setEnabled(false);
	}
	
	private void sendMessage(){
		String content = inputMessage.getText();
		if (content.length() > 0) {
			chat.sendMessage(content);
			inputMessage.setText("");
		}
	}
	
	public void refresh(ChatMessage cm){
		String msg = cm.getUser().getUsername() + ":  " + cm.getMessageContent();
		chatArea.append(" " + msg + "\n");;
	}
	
}
