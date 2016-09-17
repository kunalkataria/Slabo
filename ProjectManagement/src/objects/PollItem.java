package objects;

import java.io.Serializable;

public class PollItem implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String item;
	private int voteCount;
	
	public PollItem(String item) {
		this.item = item;
		this.voteCount = 0;
	}
	
	public PollItem(String item, int votes){
		this.item = item;
		voteCount = votes;
	}
	
	public void addVote() {
		voteCount++;
	}
	
	public int getVotes() {
		return voteCount;
	}
	
	public String getItem() {
		return item;
	}
}
