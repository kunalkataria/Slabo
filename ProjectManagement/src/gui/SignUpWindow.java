package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import CustomUI.CustomLoginPanel;
import client.ClientCommunicator;
import server.Command;
import server.Command.CommandType;
import testing.TestProject;
import utility.Encrypt;

public class SignUpWindow extends CustomLoginPanel{

	private static final long serialVersionUID = 1L;
	private SignUpWindow suw = this;
	private CustomLoginPanel signUpPane, buttonPane;
	
	private JLabel userL, passL, confirmL, emailL;
	private JTextField userText, emailText;
	private JPasswordField passText, confirmText;
	private JButton createB, cancelB;
	private ClientCommunicator cc;
	private TestProject tp;
	private CustomLoginPanel logo;
	
	public SignUpWindow(ClientCommunicator cc, TestProject tp) {
		this.cc = cc;
		this.tp = tp;
		initiateVariables();
		addActions();
		createGUI();
		setVisible(true);
	}
	
	private void initiateVariables(){
		signUpPane = new CustomLoginPanel();
		buttonPane = new CustomLoginPanel();
		
		userL = new JLabel("username:");
		passL = new JLabel("password:");
		confirmL = new JLabel("confirm:");
		emailL = new JLabel("e-mail:");
		
		userL.setForeground(Color.WHITE);
		passL.setForeground(Color.WHITE);
		confirmL.setForeground(Color.WHITE);
		emailL.setForeground(Color.WHITE);
		
		userText = new JTextField();
		userText.setBorder(BorderFactory.createLineBorder(Color.black));
		emailText = new JTextField();
		emailText.setBorder(BorderFactory.createLineBorder(Color.black));
	
		passText = new JPasswordField();
		passText.setBorder(BorderFactory.createLineBorder(Color.black));
		confirmText = new JPasswordField();
		confirmText.setBorder(BorderFactory.createLineBorder(Color.black));
		
		createB = new JButton("create");
		Dimension buttonD = createB.getPreferredSize();
		createB.setBorder(BorderFactory.createLineBorder(Color.black));
		createB.setMinimumSize(buttonD);
		createB.setPreferredSize(buttonD);
		createB.setBackground(Color.GRAY);
		createB.setForeground(Color.WHITE);
		
		cancelB = new JButton("cancel");
		buttonD = cancelB.getPreferredSize();
		cancelB.setBorder(BorderFactory.createLineBorder(Color.black));
		cancelB.setMinimumSize(buttonD);
		cancelB.setPreferredSize(buttonD);
		cancelB.setBackground(Color.GRAY);
		cancelB.setForeground(Color.WHITE);
	}
	
	private void createGUI(){
		logo = new CustomLoginPanel();
		ImageIcon imageIcon = new ImageIcon("./img/sign_up_logo.png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(220, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);
		JLabel pic = new JLabel();
		pic.setIcon(imageIcon);
		logo.add(pic);
		
		signUpPane.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(3,3,3,3);
		signUpPane.add(userL, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.ipadx = 120;
		signUpPane.add(userText, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.ipadx = 0;
		signUpPane.add(passL, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.ipadx = 120;
		signUpPane.add(passText, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.ipadx = 0;
		signUpPane.add(confirmL, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.ipadx = 120;
		signUpPane.add(confirmText, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.ipadx = 0;
		signUpPane.add(emailL, gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.ipadx = 120;
		signUpPane.add(emailText,gbc);
		
		buttonPane.add(cancelB);
		buttonPane.add(createB);
		
		this.setLayout(new BorderLayout());
		CustomLoginPanel container = new CustomLoginPanel();
		container.setLayout(new BorderLayout());
		container.add(signUpPane, BorderLayout.CENTER);
		container.add(buttonPane, BorderLayout.SOUTH);
		container.add(logo, BorderLayout.NORTH);
		CustomLoginPanel pnl = new CustomLoginPanel();
		pnl.setPreferredSize(new Dimension(200, 150));
		CustomLoginPanel pnl2 = new CustomLoginPanel();
		pnl2.setPreferredSize(new Dimension(200, 140));
		add(pnl2, BorderLayout.NORTH);
		add(container, BorderLayout.CENTER);
		add(pnl, BorderLayout.SOUTH);
	}
	private void addActions(){
		createB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = userText.getText();
				char[] passW = passText.getPassword();
				String password = new String(passW);
				char[] confirmW = confirmText.getPassword();
				String confirmation = new String (confirmW);
				String email = emailText.getText();
				if (username.length() > 0 && username.length() <= 10) {
					Pattern p = Pattern.compile
							("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
					Matcher m = p.matcher(email);
					if (m.find()) {
						if (password.length() > 0 && password.length() <= 10) {
							if (password.equals(confirmation)) {
								Command newUserCommand = new Command();
								newUserCommand.commandType = CommandType.Register;
								newUserCommand.setUsername(username);
								newUserCommand.setPassword(Encrypt.MD5(password));
								newUserCommand.setEmail(email);
								try {
									cc.sendCommand(newUserCommand);
								} catch (IOException e1) {
									JOptionPane.showMessageDialog(suw, "Server cannnot be reached."
											, "Sign-up Failed", JOptionPane.ERROR_MESSAGE);
								}
								//SignUpWindow.this.dispose();
							} else {
								JOptionPane.showMessageDialog(suw, "Passwords do not match."
										, "Sign-up Failed", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(suw, "Password must be between 1 and 10 characters."
									, "Sign-up Failed", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(suw, "Email was not valid."
								, "Sign-up Failed", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(suw, "Username must be between 1 and 10 characters."
							, "Sign-up Failed", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		cancelB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tp.cancelSignUp();
			}
			
		});
		
		
	}
	
}
