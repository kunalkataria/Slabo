package objects;

public class RSVP {
	private String username;
	private int eventID;
	private int rsvp;
	
	public RSVP(String username, int eventID, int rsvp){
		this.setUsername(username);
		this.setEventID(eventID);
		this.setRsvp(rsvp);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public int getRsvp() {
		return rsvp;
	}

	public void setRsvp(int rsvp) {
		this.rsvp = rsvp;
	}
	
	
}
