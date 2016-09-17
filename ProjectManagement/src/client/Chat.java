package client;

import gui.ChatGUI;
import objects.ChatMessage;
import server.Command;
import server.Command.CommandType;

public class Chat {
	
	private ChatGUI cGUI;
	private Project project;
	
	public Chat(Project p){
		project = p;
		cGUI = new ChatGUI(this);
	}
	
	public void sendMessage(String content){
		ChatMessage cm = new ChatMessage(project.getUser(), content, project.getID());
		Command c = new Command();
		c.setProjectID(project.getID());
		c.commandType = CommandType.Chat;
		c.setGoods(cm);
		project.sendCommand(c);
	}
	
	public void readCommand(Command c){
		ChatMessage m = (ChatMessage) c.getGoods();
		cGUI.refresh(m);
		cGUI.revalidate();
	}
	
	public Project getProject(){
		return project;
	}
	
	public ChatGUI getGUI(){
		return cGUI;
	}
	
}
