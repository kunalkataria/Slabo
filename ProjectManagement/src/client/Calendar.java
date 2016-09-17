package client;

import java.util.Vector;

import javax.swing.JPanel;

import SpecialCases.CalendarProgram;
import gui.CalendarGUI;
import objects.Event;
import objects.RSVP;
import server.Command;
import server.Command.CommandType;
import testing.TestProject;

public class Calendar {
	
	private Vector<Event> events;
	private CalendarGUI cGUI;
	private CalendarProgram cp;
	private Project project;
	private TestProject tp;
	private boolean isLoggedIn;
	
	public Calendar(Project p) {
		project = p;
		events = new Vector<Event>();
		this.isLoggedIn = project.isLoggedIn();
		cGUI = new CalendarGUI(this);
		cp = new CalendarProgram(this, events);
	}
	
	@SuppressWarnings("static-access")
	public Calendar(Project p, Vector<Event> events, TestProject tp){
		project = p;
		this.events = events;
		this.isLoggedIn = project.isLoggedIn();
		cGUI = new CalendarGUI(this);
		for(Event e : events){
			refresh(e, false);
		}
		this.tp = tp;
		cp = new CalendarProgram(this, events);
		cp.refreshCalendar(cp.currentMonth, cp.currentYear, true);
		tp.setCalendarGrid(cp);
	}
	
	public Vector<Event> getEvents(){
		return events;
	}
	
	@SuppressWarnings("static-access")
	public void readCommand(Command c){
		if(c.commandType == CommandType.Calendar){
			Event cur = (Event) c.getGoods();
			events.add(cur);
			refresh(cur, false);
			cp.refreshCalendar(cp.currentMonth, cp.currentYear, true);
		}
		else{
			RSVP r = (RSVP)c.getGoods();
			for(Event e : events){
				if(e.getEventID() == r.getEventID()){
					if(r.getRsvp() == 0){
						e.addNo(r.getUsername());
					}
					else if(r.getRsvp() == 1){
						e.addMaybe(r.getUsername());
					}
					else{
						e.addYes(r.getUsername());
					}
					cGUI.refresh(e, true);
					return;
				}
			}
		}
	}

	public void refresh(Event e, boolean update){
		cGUI.refresh(e, update);
		cGUI.revalidate();
	}
	
	public void sendCommand(Command c){
		project.sendCommand(c);
	}
	
	public CalendarGUI getGUI(){
		return cGUI;
	}
	
	public boolean isLoggedIn(){
		return isLoggedIn;
	}
	
	public Project project(){
		return project;
	}
	
	public void setCalendarWindow(JPanel p){
		tp.setCalendarWindow(p);
	}
	
}
