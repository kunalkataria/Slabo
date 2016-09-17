package objects;

import java.io.Serializable;

public class PollVote implements Serializable{
	
	private static final long serialVersionUID = 1L;
		
	private int pollID;
	private String option;
	
	public PollVote(int pollID, String option){
		this.setPollID(pollID);
		this.setOption(option);
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public int getPollID() {
		return pollID;
	}

	public void setPollID(int pollID) {
		this.pollID = pollID;
	}
	
}
