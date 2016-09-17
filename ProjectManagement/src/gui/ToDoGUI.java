package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import CustomUI.CoolScrollBarUI;
import client.Project;
import client.TodoList;
import objects.TodoTask;

public class ToDoGUI extends JPanel {

	private static final long serialVersionUID = 5574861171776447602L;
	private JPanel incompletePanel, topPanel;
	private JScrollPane topScroll;
	
	private JPanel completePanel, bottomPanel;
	private JScrollPane bottomScroll;
	
	
	private JLabel toDoLabel;
	private JLabel completedLabel;
	
	private Vector<TaskGUI> taskGUIs;
	private AddTaskDialog atd;
	private Color baseColor = Color.decode("#FB516F");
	private JPanel taskPanel;
	TodoList todoList;
		
	public ToDoGUI(TodoList todoList){
		super();
		this.todoList = todoList;
		setLayout(new BorderLayout());
		instantiateComponents();
	}
	
	private void instantiateComponents(){
		
		taskGUIs = new Vector<TaskGUI>();
		topPanel = new JPanel(){protected void drawComponent(Graphics g){}};
		topPanel.setLayout(new BorderLayout());
		toDoLabel = new JLabel("To Do");
		toDoLabel.setForeground(Color.white);
		toDoLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		toDoLabel.setFont(toDoLabel.getFont().deriveFont(Font.PLAIN, 24));
		incompletePanel = new JPanel(){protected void drawComponent(Graphics g){}};
		incompletePanel.setLayout(new BoxLayout(incompletePanel,BoxLayout.Y_AXIS));
		
		topScroll = new JScrollPane();
		topScroll.setViewportView(topPanel);
		topScroll.setViewportBorder(BorderFactory.createEmptyBorder());
		topScroll.setBorder(BorderFactory.createEmptyBorder());
		topScroll.getVerticalScrollBar().setUI(new CoolScrollBarUI());
		topScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		topScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		topPanel.add(incompletePanel, BorderLayout.NORTH);
		
		JPanel topContainer = new JPanel(){protected void drawComponent(Graphics g){}};;
		topContainer.setLayout(new BorderLayout());
		topContainer.add(toDoLabel, BorderLayout.NORTH);
		topContainer.add(topScroll, BorderLayout.CENTER);
		
		JPanel bottomContainer = new JPanel(){protected void drawComponent(Graphics g){}};;
		bottomContainer.setLayout(new BorderLayout());
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		completedLabel = new JLabel("Completed");
		completedLabel.setForeground(Color.white);
		completedLabel.setFont(completedLabel.getFont().deriveFont(Font.PLAIN, 24));
		completedLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		completePanel = new JPanel();
		completePanel.setLayout(new BoxLayout(completePanel,BoxLayout.Y_AXIS));
		bottomPanel.add(completePanel, BorderLayout.NORTH);
		bottomScroll = new JScrollPane();
		bottomScroll.getVerticalScrollBar().setUI(new CoolScrollBarUI());
		bottomScroll.setViewportView(bottomPanel);
		bottomScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		bottomScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		bottomScroll.setViewportBorder(BorderFactory.createEmptyBorder());
		bottomScroll.setBorder(BorderFactory.createEmptyBorder());
		bottomContainer.add(completedLabel, BorderLayout.NORTH);
		bottomContainer.add(bottomScroll, BorderLayout.CENTER);
		
		taskPanel = new JPanel(){protected void drawComponent(Graphics g){}};;
		taskPanel.setLayout(new GridLayout(2,1));
		taskPanel.add(topContainer);
		taskPanel.add(bottomContainer);
		add(taskPanel, BorderLayout.CENTER);
		
		setBackground(baseColor);
		taskPanel.setBackground(new Color(0,0,0,0));
		topPanel.setBackground(new Color(0,0,0,0));
		bottomPanel.setBackground(new Color(0,0,0,0));
		incompletePanel.setBackground(new Color(0,0,0,0));
		topContainer.setBackground(new Color(0,0,0,0));
		bottomContainer.setBackground(new Color(0,0,0,0));
		bottomScroll.setBackground(new Color(0,0,0,0));
		topScroll.setBackground(new Color(0,0,0,0));
		
		setBorder(BorderFactory.createEmptyBorder(Project.BORDER, Project.BORDER, Project.BORDER, Project.BORDER));
		atd = new AddTaskDialog(todoList.getProject());
		repaint();
	}
	
	public void refresh(TodoTask t){
		for(TaskGUI tg : taskGUIs){
			if(tg.getTask().getID() == t.getID()){
				tg.toggleCompleted();
				if(tg.getTask().isCompleted()){
					incompletePanel.remove(tg);
					completePanel.add(tg);
				}
				else{
					completePanel.remove(tg);
					incompletePanel.add(tg);
				}
				return;
			}
		}
		TaskGUI tp = new TaskGUI(t, todoList.getProject());
		taskGUIs.add(tp);
		tp.setSize(incompletePanel.getWidth(), 100);
		if(t.isCompleted()){
			completePanel.add(tp);
		} else {
			incompletePanel.add(tp);
		}
	}

}
