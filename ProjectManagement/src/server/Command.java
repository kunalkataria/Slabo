package server;

import java.io.Serializable;

public class Command implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public enum CommandType {
		Login, Register, Calendar, RSVP, Poll, PollVote, PollResults,
		ProjectFile, Download, TodoList, Chat, MembersList, 
		ProjectList, Project, ProjectObject,
		AddProject, UpdateTodo
	}
	
	private String username;
	private String password;
	private String email;
	private boolean successful;
	private Object goods;
	private int projectID;
	
	public CommandType commandType;
	
	public Command(){}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isSuccessful() {
		return successful;
	}
	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
	
	public void setGoods(Object goods){
		this.goods = goods;
	}
	public Object getGoods(){
		return goods;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setProjectID(int projectID){
		this.projectID = projectID;
	}
	
	public int getProjectID(){
		return projectID;
	}
	
}
