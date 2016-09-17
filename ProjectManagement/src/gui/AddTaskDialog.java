package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import CustomUI.IconButton;
import client.Project;
import objects.TodoTask;
import server.Command;
import server.Command.CommandType;

public class AddTaskDialog extends JPanel{

	private JLabel taskLabel;
	private JTextField taskField;
	private JPanel header;
	private JPanel background;
	private JPanel buttonPanel;
	private IconButton addButton;
	private Color baseColor = Color.decode("#FB516F");
	private Color lightColor = Color.decode("#FC7A91");
	
	private Project project;
	
	public AddTaskDialog(Project project){
		this.project = project;
		instantiateComponents();
	}
	
	private void instantiateComponents(){
		
		setLayout(new BorderLayout());
		taskLabel = new JLabel("Create Task  ");
		taskLabel.setFont(taskLabel.getFont().deriveFont(Font.PLAIN, 30));
		taskLabel.setForeground(Color.white);
		taskLabel.setHorizontalAlignment(SwingConstants.CENTER);
		background = new JPanel();
		background.setLayout(new BorderLayout());
		background.setBackground(baseColor);
		background.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		setBackground(baseColor);
		header = new JPanel();
		taskField = new JTextField(20);
		taskField.setForeground(Color.white);
		taskField.setBorder(BorderFactory.createEmptyBorder());
		taskField.setBackground(lightColor);
		header.add(taskLabel);
		header.add(taskField);
		header.setBackground(baseColor);
		background.add(header, BorderLayout.CENTER);
		
		try {
			addButton = new IconButton("icons/addbutton.png", "Add!", null, null);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		header.add(addButton);
		
		add(background, BorderLayout.CENTER);
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Command c = new Command();
				TodoTask task = new TodoTask(taskField.getText());
				task.setCompleted(false);
				taskField.setText("");
				c.commandType = CommandType.TodoList;
				c.setGoods(task);
				project.sendCommand(c);
			}
		});
		
	}

}
