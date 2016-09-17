package objects;

import java.io.Serializable;

public class TodoTask implements Serializable {

	private static final long serialVersionUID = -7439876936247446471L;
	private String taskName;
	private boolean isCompleted;
	private int todoID;
	
	public TodoTask(String taskName){
		this.taskName = taskName;
		isCompleted = false;
	}
	
	public TodoTask(String taskName, boolean isCompleted){
		this.taskName = taskName;
		this.isCompleted = isCompleted;
	}
	
	public int getID() {
		return todoID;
	}
	
	public void setID(int todoID) {
		this.todoID = todoID;
	}
	
	public void toggleCompleted(){
		isCompleted = !isCompleted;
	}
	
	public void setCompleted(boolean status) {
		isCompleted = status;
	}
	
	public boolean isCompleted(){
		return isCompleted;
	}
	
	public String getTaskName(){
		return taskName;
	}
	
}
