package gui;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.ProjectManager;
import server.Command;
import server.Command.CommandType;

public class AddProjectPopUp extends JDialog {
	public static final long serialVersionUID = 1;
	private JPanel background; //where you add all components
	private JLabel nameLabel; //"project name" 
	private JTextField textField; //just blank textfield
	private JButton joinButton;
	private JButton cancelButton;
	private String projectName;
	private JPanel topPanel;
	private JPanel centerPanel;
	private JPanel bottomPanel;
	
	private ProjectManager pm;
	
	private Set<Integer> projectIDset;
	
	public AddProjectPopUp(ProjectManager pm, Set<Integer> projectIDset){
		this.pm = pm;
		this.projectIDset = projectIDset;
		instantiateComponents();
		createGUI();
		addActions();
	}
	
	private void instantiateComponents() {
		background = new JPanel();
		background.setLayout(new BorderLayout());
		topPanel = new JPanel();
		nameLabel = new JLabel("Enter your project ID");
		topPanel.add(nameLabel);
		background.add(topPanel, BorderLayout.NORTH);
		
		centerPanel = new JPanel();
		textField = new JTextField(20);
		centerPanel.add(textField);
		background.add(centerPanel, BorderLayout.CENTER);
		
		bottomPanel = new JPanel();
		joinButton = new JButton("Join");
		cancelButton = new JButton("Cancel");
		bottomPanel.add(joinButton);
		bottomPanel.add(cancelButton);
		background.add(bottomPanel, BorderLayout.SOUTH);
		
		add(background);
		
	}
	
	private void createGUI() {
		setSize(400,200);
		setLocation(500,50);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	private void addActions() {
		joinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				if (addProject()) {
					dispose();
				}
			}
		});
		
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				dispose();
			}
		});
	}
	
	private boolean addProject(){
		projectName = textField.getText();
		int projectID = (int) Integer.parseInt(projectName);
		if (projectIDset.contains(projectID)) {
			return false;
		} else {
			Command c = new Command();
			c.commandType = CommandType.AddProject; 
			c.setProjectID(projectID);
			c.setUsername(pm.currentUser().getUsername());
			pm.sendCommand(c);
			return true;
		}
		
		
	}
}
