package objects;

import java.io.Serializable;
import java.util.Vector;

public class ProjectObject implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String projectName, projectDescription, projectDueDate;
	private int projectID;
	private Vector<Event> calendarEvents;
	private Vector<User> membersList;
	private Vector<Poll> polls;
	private Vector<ProjectFile> projectFiles;
	private Vector<TodoTask> todoList;
	private String ownerUsername;
	
	public ProjectObject(int projectID, String projectName, String projectDescription, String duedate){
		this.projectID = projectID;
		this.projectName = projectName;
		this.projectDescription = projectDescription;
		this.projectDueDate = duedate;
		
		calendarEvents = new Vector<Event>();
		membersList = new Vector<User>();
		polls = new Vector<Poll>();
		projectFiles = new Vector<ProjectFile>();
		todoList = new Vector<TodoTask>();
		ownerUsername = "";
	}

	public int getProjectID(){
		return projectID;
	}
	public String getProjectName(){
		return projectName;
	}
	public String getProjectDescription(){
		return projectDescription;
	}
	public String getProjectDueDate(){
		return projectDueDate;
	}
	public Vector<Event> getCalendarEvents() {
		return calendarEvents;
	}

	public void setCalendarEvents(Vector<Event> calendarEvents) {
		this.calendarEvents = calendarEvents;
	}
	
	public void addMember(User u) {
		membersList.add(u);
	}

	public Vector<User> getMembersList() {
		return membersList;
	}

	public void setMembersList(Vector<User> membersList) {
		this.membersList = membersList;
	}

	public Vector<Poll> getPolls() {
		return polls;
	}

	public void setPolls(Vector<Poll> polls) {
		this.polls = polls;
	}

	public Vector<ProjectFile> getProjectFiles() {
		return projectFiles;
	}

	public void setProjectFiles(Vector<ProjectFile> projectFiles) {
		this.projectFiles = projectFiles;
	}

	public Vector<TodoTask> getTodoList() {
		return todoList;
	}

	public void setTodoList(Vector<TodoTask> todoList) {
		this.todoList = todoList;
	}

	public String getOwnerUsername() {
		return ownerUsername;
	}

	public void setOwnerUsername(String ownerUsername) {
		this.ownerUsername = ownerUsername;
	}
	
	public void setProjectID(int projectID){
		this.projectID = projectID;
	}
	
}
