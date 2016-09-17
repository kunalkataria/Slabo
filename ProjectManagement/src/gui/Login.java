package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import CustomUI.CustomLoginPanel;
import client.ClientCommunicator;
import server.Command;
import server.Command.CommandType;
import testing.TestProject;
import utility.Encrypt;

public class Login extends CustomLoginPanel{
	private static final long serialVersionUID = 1;
	
	//Containers
	CustomLoginPanel overall;
	CustomLoginPanel members;
	JPanel guests;
	
	////members
	//User
	CustomLoginPanel userP;
	JLabel user;
	JTextField userText;	
	
	//Password
	CustomLoginPanel passP;
	JLabel pass;
	JPasswordField passText;
	
	//Button
	CustomLoginPanel buttonP;
	JButton loginB;
	JButton guestB;
	JButton signUpB;
	
	////guests
	JPanel projectIDP;
	JLabel projectID;
	JTextField projectIDText;
	
	JPanel guestBP;
	JButton backB;
	JButton submitB;
	
	ClientCommunicator cc;
	TestProject tp;
	
	public Login(ClientCommunicator cc){
		super();
		this.cc = cc;
		instantiateComponents();
		createGUI();
		addAction();
		setVisible(true);
		//setBackground("#ECEFF1");
	}
	
	/*protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawString("Welcome to Slabo", 200, 200);
	}*/
	
	public void setTestProject (TestProject tp){
		this.tp = tp;
	}
	
	private void instantiateComponents(){
		//
		overall = new CustomLoginPanel();
		members = new CustomLoginPanel();
		guests = new CustomLoginPanel();
		
		//members
		userP = new CustomLoginPanel();
		user = new JLabel("Username");
		user.setForeground(Color.WHITE);
		userText = new JTextField(20);
		userText.setBorder(BorderFactory.createLineBorder(Color.black));
		
		passP = new CustomLoginPanel();
		pass = new JLabel("Password ");
		pass.setForeground(Color.WHITE);
		pass.setBorder(BorderFactory.createLineBorder(Color.black));

		passText = new JPasswordField(20);
		passText.setBorder(BorderFactory.createLineBorder(Color.black));
		passText.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent ke){
				if(ke.getKeyCode() == KeyEvent.VK_ENTER){
					String username = userText.getText();
					if (username.length() > 0 && username.length() <= 10) {
						String usernameString = userText.getText();
						char[] password = passText.getPassword();
						String plainTextPassword = String.valueOf(password);
						String encryptedPassword = Encrypt.MD5(plainTextPassword);
						Command c = new Command();
						c.setUsername(usernameString);
						c.setPassword(encryptedPassword);
						c.commandType = CommandType.Login;
						try {
							cc.sendCommand(c);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						// error message that username is not within the length bounds
					}
				}
			}
		});
		
		buttonP = new CustomLoginPanel();
		
		loginB = new JButton("Login");
		Dimension buttonD = loginB.getPreferredSize();
		loginB.setBorder(BorderFactory.createLineBorder(Color.black));
		loginB.setMinimumSize(buttonD);
		loginB.setPreferredSize(buttonD);
//		loginB.setBackground(Color.LIGHT_GRAY);
		loginB.setForeground(Color.WHITE);
		
		guestB = new JButton("Guest Login");
		buttonD = guestB.getPreferredSize();
		guestB.setBorder(BorderFactory.createLineBorder(Color.black));
		guestB.setMinimumSize(buttonD);
		guestB.setPreferredSize(buttonD);
//		guestB.setBackground(Color.LIGHT_GRAY);
		guestB.setForeground(Color.WHITE);
		
		signUpB = new JButton("Sign Up");
		buttonD = signUpB.getPreferredSize();
		signUpB.setBorder(BorderFactory.createLineBorder(Color.black));
		signUpB.setMinimumSize(buttonD);
		signUpB.setPreferredSize(buttonD);
//		signUpB.setBackground(Color.GRAY);
		signUpB.setForeground(Color.WHITE);
		//
		//guests
		projectIDP = new JPanel();
		projectID = new JLabel("Project ID");
		projectID.setForeground(Color.white);
		projectIDText = new JTextField(20);
		projectIDText.setBorder(BorderFactory.createLineBorder(Color.black));
		
		guestBP = new JPanel();
		backB = new JButton("Back");
		buttonD = backB.getPreferredSize();
		backB.setBorder(BorderFactory.createLineBorder(Color.black));
		backB.setMinimumSize(buttonD);
		backB.setPreferredSize(buttonD);
		backB.setBackground(Color.GRAY);
		backB.setForeground(Color.WHITE);
		
		submitB = new JButton("Submit");
		buttonD = submitB.getPreferredSize();
		submitB.setBorder(BorderFactory.createLineBorder(Color.black));
		submitB.setMinimumSize(buttonD);
		submitB.setPreferredSize(buttonD);
		submitB.setBackground(Color.GRAY);
		submitB.setForeground(Color.WHITE);
	}
	
	private void createGUI(){
		overall.setLayout(new CardLayout());
		
		CustomLoginPanel login = new CustomLoginPanel();
		
		login.setLayout(new BoxLayout(login, BoxLayout.Y_AXIS));
		
		members.setLayout(new BorderLayout());
		guests.setLayout(new BoxLayout(guests, BoxLayout.Y_AXIS));
		
		userP.add(user);
		userP.add(userText);		

		passP.add(pass);
		passP.add(passText);
		
		buttonP.add(loginB);
		buttonP.add(guestB);
		buttonP.add(signUpB);
		
		CustomLoginPanel infoPanel = new CustomLoginPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.add(userP);
		infoPanel.add(passP);
		infoPanel.add(buttonP);
		//members.add(userP);
		//members.add(passP);
		members.add(infoPanel, BorderLayout.NORTH);
		//members.add(buttonP, BorderLayout.SOUTH);
		
		CustomLoginPanel logoPanel = new CustomLoginPanel();
		ImageIcon imageIcon = new ImageIcon("./img/logo.png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(385, 330,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);
		JLabel pic = new JLabel();
		pic.setIcon(imageIcon);
		logoPanel.add(pic);
		
		login.add(logoPanel, BorderLayout.CENTER);//, BorderLayout.SOUTH);
		login.add(members, BorderLayout.SOUTH);
		
		projectIDP.add(projectID);
		projectIDP.add(projectIDText);
		projectIDP.setBackground(Color.BLACK);
		
		guestBP.add(backB);
		guestBP.add(submitB);
		guestBP.setBackground(Color.BLACK);
		
		guests.add(projectIDP);
		guests.add(guestBP);

		CustomLoginPanel guestPanel = new CustomLoginPanel();
		CustomLoginPanel logoPanel2 = new CustomLoginPanel();
		ImageIcon imageIcon2 = new ImageIcon("./img/logo.png"); // load the image to a imageIcon
		Image image2 = imageIcon2.getImage(); // transform it 
		Image newimg2 = image2.getScaledInstance(385, 330,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg2);
		JLabel pic2 = new JLabel();
		pic2.setIcon(imageIcon);
		logoPanel2.add(pic2);
		guestPanel.setLayout(new BorderLayout());
		guestPanel.add(guests, BorderLayout.SOUTH);
		guestPanel.add(logoPanel2, BorderLayout.CENTER);
		
		overall.add(login, "first");
		overall.add(guestPanel, "second");
		add(overall);
	}
	
	private void addAction(){
		
		guestB.addActionListener(new ButtonClicked("second", overall));
		backB.addActionListener(new ButtonClicked("first", overall));
		
		submitB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				String projectName = projectIDText.getText();
				Command c = new Command();
				c.commandType = CommandType.ProjectObject; 
				c.setProjectID((int) Integer.parseInt(projectName));
				try {
					cc.sendCommand(c);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		});
		
		signUpB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tp.signUp(new SignUpWindow(cc, tp));
			}
		});
		
		loginB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = userText.getText();
				if (username.length() > 0 && username.length() <= 10) {
					String usernameString = userText.getText();
					char[] password = passText.getPassword();
					String plainTextPassword = String.valueOf(password);
					String encryptedPassword = Encrypt.MD5(plainTextPassword);
					Command c = new Command();
					c.setUsername(usernameString);
					c.setPassword(encryptedPassword);
					c.commandType = CommandType.Login;
					try {
						cc.sendCommand(c);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					// error message that username is not within the length bounds
				}
			}
			
		});
	}
	
	class ButtonClicked implements ActionListener{
		private String numberString;
		private JPanel jp;
		public ButtonClicked(String numberString, JPanel jp){
			this.numberString = numberString;
			this.jp = jp;
		}
		public void actionPerformed(ActionEvent ae){
			CardLayout cl = (CardLayout)jp.getLayout();
			cl.show(jp,numberString);
		}
	}
	
}
