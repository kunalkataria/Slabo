package objects;

import java.io.Serializable;
import java.util.Vector;

public class Event implements Serializable{
	
	private static final long serialVersionUID = -5472332849013149754L;
	private String title;
	private String description;
	private String creator;
	private String date;
	private int eventID;
	private int row;
	private int col;
	private Vector<String> yes, no, maybe; //rsvp statuses
	
	public Event(String creator, int eventID, String date, String title, String description, int row, int col) {
		this.creator = creator;
		this.eventID = eventID;
		this.date = date;
		this.title = title;
		this.description = description;
		this.row = row;
		this.col = col;
		yes = new Vector<String>();
		no = new Vector<String>();
		maybe = new Vector<String>();
	}
	
	public Vector<String> getYes(){
		return yes;
	}
	public void setYes(Vector<String> yes){
		this.yes = yes;
	}
	public void addYes(String username){
		yes.add(username);
	}
	
	public Vector<String> getNo(){
		return no;
	}
	public void setNo(Vector<String> no){
		this.no = no;
	}
	public void addNo(String username){
		no.add(username);
	}
	
	public Vector<String> getMaybe(){
		return maybe;
	}
	public void setMaybe(Vector<String> maybe){
		this.maybe = maybe;
	}
	public void addMaybe(String username){
		maybe.add(username);
	}
	
	public String getCreator() {
		return creator;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getEventID(){
		return eventID;
	}
	
	public int getRow(){
		return row;
	}
	
	public int getCol(){
		return col;
	}
	
	public String getMonth(){
		return date.substring(0,2);
	}
	
	public String getYear(){
		return date.substring(6);
	}
	
	
}
