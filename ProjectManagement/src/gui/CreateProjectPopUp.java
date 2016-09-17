package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.ProjectManager;
import objects.ProjectObject;
import server.Command;
import server.Command.CommandType;

//need to make this entire thing a jdialog essentially?
//the format of a custom jdialog as a jpanel and just adding that panel should work? 
public class CreateProjectPopUp extends JDialog{

		public static final long serialVersionUID = 1;
		private JPanel background; //where you add all components
		private JButton createButton;
		private JButton cancelButton;
		private JPanel bottomPanel;
		private JTextArea description;
		private JTextField monthNum;
		private JTextField dayNum;
		private JTextField yearNum;
		
		private ProjectManager pm;
		
		
		private JTextField projectName;
		public CreateProjectPopUp(ProjectManager pm){
			this.pm = pm;
			instantiateComponents();
			createGUI();
			addActions();
			
		}
		
		private void instantiateComponents() {
			
			background = new JPanel();
			background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
			
			JPanel allContents = new JPanel();
			JPanel namePanel = new JPanel();
			JLabel pNameLabel = new JLabel("Project Name:");
			projectName = new JTextField(20);
			namePanel.add(pNameLabel);
			namePanel.add(projectName);
			background.add(namePanel);
			
			JPanel datePanel = new JPanel();
			JLabel dueDateLabel = new JLabel("Due: ");
			JLabel monthLabel = new JLabel("Month: ");
			monthNum = new JTextField(2);
			
			JLabel dayLabel = new JLabel("Day: ");
			dayNum = new JTextField(2);
			
			JLabel yearLabel = new JLabel("Year: ");
			yearNum = new JTextField(4);
			datePanel.add(dueDateLabel);
			datePanel.add(monthLabel);
			datePanel.add(monthNum);
			datePanel.add(dayLabel);
			datePanel.add(dayNum);
			datePanel.add(yearLabel);
			datePanel.add(yearNum);
			background.add(datePanel);
			
			JPanel labelHolder = new JPanel();
			labelHolder.setLayout(new BorderLayout());
			JLabel proInfoLabel = new JLabel("Project Description ");
			labelHolder.add(proInfoLabel, BorderLayout.WEST);
			background.add(labelHolder);
			
			JPanel infoPanel = new JPanel();			
			description = new JTextArea();
			infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));			
			infoPanel.add(description);
			JScrollPane pScroll = new JScrollPane();
			pScroll.setViewportView(infoPanel);
			pScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			pScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			
			
			bottomPanel = new JPanel();
			createButton = new JButton("Create Project");
			cancelButton = new JButton("Cancel");
			bottomPanel.add(createButton);
			bottomPanel.add(cancelButton);
			
			allContents.setLayout(new BorderLayout());
			allContents.add(background, BorderLayout.NORTH);
			allContents.add(pScroll, BorderLayout.CENTER);
			allContents.add(bottomPanel, BorderLayout.SOUTH);
			add(allContents);
			
		}
		
		private void createGUI() {
			setSize(400,200);
			setLocation(500,50);
			setVisible(true);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
		}
		private void addActions() {	
			createButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
						String projectNameString = projectName.getText();
						if (projectNameString.length() > 0 && projectNameString.length() <= 75) {
							String projectDescription = description.getText();
							if (projectDescription.length() >= 0 && projectDescription.length() <= 1000) {
								if (monthNum.getText().length() == 2 && dayNum.getText().length() == 2 && yearNum.getText().length() == 4) {
									Command sendCommand = new Command();
									sendCommand.commandType = CommandType.Project;
									String date = monthNum.getText() + "/" + dayNum.getText() + "/" + yearNum.getText(); 
									ProjectObject newProject = new ProjectObject(-1, projectNameString, projectDescription, date);
									newProject.setOwnerUsername(pm.currentUser().getUsername());
									sendCommand.setGoods(newProject);
									sendCommand.setUsername(pm.currentUser().getUsername());
									System.out.println("Attaching: " + pm.currentUser().getUsername());
									pm.sendCommand(sendCommand);
									dispose();
								} else {
									showErrorMessage("Date is not valid. \n Year must be 4 digits, " + 
											" and month and day must be two digit numbers (enter 0 as first digit if date is less than 10)");
								}
							} else {
								showErrorMessage("Project description is not valid");
							}
						} else {
							showErrorMessage("Project name is not valid");
						}
				}
				
			});
			
			cancelButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					dispose();
				}
			});
		}
		
		private void showErrorMessage(String message) {
			JOptionPane.showMessageDialog(null, message, "Error!", JOptionPane.ERROR_MESSAGE);
		}
} 



