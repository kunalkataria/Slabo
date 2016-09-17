package client;

import java.util.PriorityQueue;
import java.util.Vector;

import gui.MembersListGUI;
import objects.User;
import server.Command;

public class MembersList {

	private Vector<User> users;
	private MembersListGUI mGUI;
	private PriorityQueue<User> userQueue;
	
	public MembersList(){
		mGUI = new MembersListGUI(this);
		users = new Vector<User>();
		userQueue = new PriorityQueue<User>();
	}
	
	public MembersList( Vector<User> users){
		mGUI = new MembersListGUI(this);
		this.users = users;
		userQueue = new PriorityQueue<User>();
		for(User u : users){
			userQueue.add(u);
		}
		
		for (User u : userQueue) {
			mGUI.refresh(u);
			mGUI.repaint();
			mGUI.revalidate();
		}
	}

	public void readCommand(Command c){
		User u = (User) c.getGoods();
		users.add(u);
		userQueue.add(u);
		mGUI.refresh(u);
		mGUI.repaint();
		mGUI.revalidate();
	}
	
	public MembersListGUI getGUI(){
		return mGUI;
	}
	
	
	
}
