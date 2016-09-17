package objects;

import java.io.Serializable;
import java.util.Vector;

public class Poll implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Vector<PollItem> items;
	private String owner;
	private String title;
	private boolean canVote;
	private int pollID;
	
	public Poll(String owner, String title, int pollID) {
		this.owner = owner;
		this.title = title;
		this.pollID = pollID;
		items = new Vector<PollItem>();
		canVote = true;
	}
	
	public Poll(String owner, String title, int pollID, Vector<PollItem> items){
		this.owner = owner;
		this.title = title;
		this.items = items;
		this.pollID = pollID;
		canVote = true;
	}
	
	public Poll(String title, int pollID) {
		this.title = title;
		this.pollID = pollID;
	}
	
	public void addItem(PollItem item) {
		items.add(item);
	}
	
	public Vector<PollItem> getOptions() {
		return items;
	}
	
	public void setOptions(Vector<PollItem> newOptions){
		items = newOptions;
	}
	
	public String getTitle(){
		return title;
	}
		
	public String getOwner() {
		return owner;
	}
	
	public int getID(){
		return pollID;
	}
	
	public void setID(int id) {
		pollID = id;
	}
	
	public void setCanVote(boolean canVote){
		this.canVote = canVote;
	}
	
	public boolean canVote(){
		return canVote;
	}
		
}
