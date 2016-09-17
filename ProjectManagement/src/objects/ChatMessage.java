package objects;

import java.io.Serializable;

public class ChatMessage implements Serializable{
	
	private static final long serialVersionUID = 6721365015548959740L;
	private User user;
	private String message;
	private int projectID;
	
	public ChatMessage(User user, String message, int projectID) {
		this.user = user;
		this.message = message;
		this.projectID = projectID;
	}
	
	public User getUser() {
		return user;
	}
	public String getMessageContent() {
		return message;
	}
	public int getProjectID(){
		return projectID;
	}
}
