package client;

import java.util.Vector;

import javax.swing.JPanel;

import gui.PollsGUI;
import gui.PollsWindow;
import gui.ResultsWindow;
import objects.Poll;
import objects.User;
import server.Command;
import server.Command.CommandType;
import testing.TestProject;

public class Polls {

	private Vector<Poll> polls;
	PollsGUI pGUI;
	Project project;
	TestProject tp;
	
	public Polls(Project p){
		project = p;
		polls = new Vector<Poll>();
		pGUI = new PollsGUI(this);
	}
	
	public Polls(Vector<Poll> polls, Project p, TestProject tp){
		project = p;
		this.polls = polls;
		this.tp = tp;
		pGUI = new PollsGUI(this, polls);
	}
	
	public void readCommand(Command c){
		if(c.commandType == CommandType.PollResults){
			Poll p = (Poll)c.getGoods();
			tp.setPollsWindow(new ResultsWindow(p));
			for(Poll pp : polls){
				if(pp.getID() == p.getID()){
					pp.setCanVote(false);
					return;
				}
			}
		}
		else{
			Poll p = (Poll) c.getGoods();
			polls.add(p);
			pGUI.refresh(p);
			tp.setPollsWindow2(new PollsWindow(p, this));
		}
	}
	
	public void sendCommand(Command cmd){
		project.sendCommand(cmd);
	}
	
	public void sendGoods(Poll p){
		Command c = new Command();
		c.commandType = CommandType.Poll;
		c.setGoods(p);
		project.sendCommand(c);
	}
	
	public PollsGUI getGUI(){
		return pGUI;
	}
	
	public User getUser(){
		return project.getUser();
	}
	
	public void setPollWindow(JPanel p){
		tp.setPollsWindow(p);
	}
	
	public Project getProject(){
		return project;
	}
}
