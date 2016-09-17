package client;

import java.util.Vector;

import javax.swing.JPanel;

import gui.ToDoGUI;
import javafx.concurrent.Task;
import objects.TodoTask;
import server.Command;
import testing.TestProject;

public class TodoList {

	private Vector<TodoTask> tasks;
	private ToDoGUI tGUI;
	private Project project;
	private TestProject tp;
	
	//new TodoList
	public TodoList(Project project) {
		this.project = project;
		tasks = new Vector<TodoTask>();
		tGUI = new ToDoGUI(this);
	}
	
	//populate with existing TodoList
	public TodoList(Vector<TodoTask> tasks, Project project, TestProject tp){
		this.tasks = tasks;
		this.tp = tp;
		this.project = project;
		tGUI = new ToDoGUI(this);
		for (TodoTask t : tasks){
			tGUI.refresh(t);
			tGUI.revalidate();
		}
	}
	
	public void readCommand(Command c){
		TodoTask task = (TodoTask) c.getGoods();
		refresh(task);
	}
	
	public void sendCommand(Command c){
		project.sendCommand(c);
	}
	
	public void refresh(TodoTask task){
		tGUI.refresh(task);
		tGUI.repaint();
		tGUI.revalidate();
	}
	
	public ToDoGUI getGUI(){
		return tGUI;
	}
	
	public Project getProject(){
		return project;
	}
	
	public void setToDoWindow(JPanel p){
		tp.setToDoWindow(p);
	}
}
