package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import CustomUI.IconButton;
import client.Project;
import objects.TodoTask;
import server.Command;
import server.Command.CommandType;

public class TaskGUI extends JPanel{
	private static final long serialVersionUID = 2856877950935722094L;
	private JLabel taskName;
	private JPanel tempButtonBox, left;
	private IconButton completeButton;
	private TodoTask t;
	private Project project;
	private Color backgroundColor = Color.decode("#FB516F");
	private Color panelColor = Color.decode("#FC7A91");
	private Color lightPanelColor = Color.decode("#FEADBB");
	
	public TaskGUI(TodoTask t, Project p){
		super();
		this.t = t;
		this.project = p;
		taskName = new JLabel(t.getTaskName());
		taskName.setForeground(Color.white);
		try {
			completeButton = new IconButton("./icons/checkbox.png", "Mark Complete", null, null);
			completeButton.doesHoverText(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(t.isCompleted()){
			completeButton.setBackgroundColor(Color.black);
			completeButton.setHoverColor(Color.white);
			repaint();
		} else {
			completeButton.setBackgroundColor(Color.white);
			completeButton.setHoverColor(Color.black);
			repaint();
		}
		instantiateComponents();
		if(!p.isLoggedIn()){
			completeButton.setEnabled(false);
		}
		addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(lightPanelColor);
				left.setBackground(lightPanelColor);
				tempButtonBox.setBackground(lightPanelColor);
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(panelColor);
				left.setBackground(panelColor);
				tempButtonBox.setBackground(panelColor);
				repaint();
			}
		});
		setBorder(BorderFactory.createLineBorder(backgroundColor));
		setBackground(panelColor);
		repaint();
	}
	
	public void instantiateComponents(){
		
		setLayout(new BorderLayout());
		tempButtonBox = new JPanel();
		tempButtonBox.setBackground(panelColor);
		tempButtonBox.add(completeButton);
	
		left = new JPanel();
		left.setBackground(panelColor);
		left.add(taskName);
		add(left, BorderLayout.WEST);
		add(tempButtonBox, BorderLayout.EAST);
		
		completeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Command cmd = new Command();
				cmd.commandType = CommandType.UpdateTodo;
				cmd.setGoods(t);
				project.sendCommand(cmd);
			}
		});
		
		setBorder(BorderFactory.createLineBorder(Color.black));
		
	}

	public TodoTask getTask() {
		return t;
	}
	
	public void toggleCompleted(){
		t.toggleCompleted();
		if(t.isCompleted()){
			completeButton.setBackgroundColor(Color.black);
			completeButton.setHoverColor(Color.white);
		} else {
			completeButton.setBackgroundColor(Color.white);
			completeButton.setHoverColor(Color.black);
		}
	}
	
}
