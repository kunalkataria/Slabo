package client;

import java.io.IOException;

import gui.AboutGUI;
import gui.AddTaskDialog;
import gui.CreateFileWindow;
import objects.ProjectObject;
import objects.User;
import server.Command;
import testing.TestProject;

public class Project{
	
	private int projectID;
	private String projectDescription;
	private String projectTitle;
	private String dueDate;
	private boolean isLoggedIn;
	//client
	private ClientCommunicator cc;
	private ProjectManager pm;
	
	//upper level classes
	private TodoList toDo;
	private Calendar calendar;
	private Chat chat;
	private MembersList membersList;
	private Polls polls;
	private ProjectFiles projectFiles;
	private AboutGUI about;
	
	public static final int BORDER = 10;
	
	public Project(ProjectManager pm, ProjectObject projOb, ClientCommunicator cc, TestProject tp, boolean isLoggedIn) {
//		super();
		this.pm = pm;
		this.isLoggedIn = isLoggedIn;
		this.projectID = projOb.getProjectID();
		toDo = new TodoList(projOb.getTodoList(), this, tp);
		calendar = new Calendar(this, projOb.getCalendarEvents(), tp);
		polls = new Polls(projOb.getPolls(), this, tp);
		membersList = new MembersList(projOb.getMembersList());
		chat = new Chat(this);
		projectFiles = new ProjectFiles(projOb.getProjectFiles(), this, tp);
		about = new AboutGUI(projOb);
		this.projectTitle = projOb.getProjectName();
		this.dueDate = projOb.getProjectDueDate();
		this.projectDescription = projOb.getProjectDescription();
		this.cc = cc;
		
		tp.setToDo(toDo.getGUI());
		tp.setCalendar(calendar.getGUI());
		tp.setMembers(membersList.getGUI());
		tp.setPolls(polls.getGUI());
		tp.setFile(projectFiles.getGUI());
		tp.setAbout(about);
		tp.setChat(chat.getGUI());
		if (isLoggedIn){
			projectFiles.setCreateFile(new CreateFileWindow(projectFiles));
			toDo.setToDoWindow(new AddTaskDialog(this));
		}
		if (!isLoggedIn){
			tp.guestLogin();
		}
		else{
			tp.openProject();
		}
		
	}
	
	@SuppressWarnings("incomplete-switch")
	public void readCommand(Command c){
		switch(c.commandType){
		case Calendar:
			calendar.readCommand(c);
			break;
		case RSVP:
			calendar.readCommand(c);
			break;
		case Chat:
			chat.readCommand(c);
			break;
		case Poll:
			polls.readCommand(c);
			break;
		case PollResults:
			polls.readCommand(c);
			break;
		case ProjectFile:
			projectFiles.readCommand(c);
			break;
		case Download:
			projectFiles.readCommand(c);
			break;
		case TodoList:
			toDo.readCommand(c);
			break;
		case UpdateTodo:
			toDo.readCommand(c);
			break;
		case MembersList:
			membersList.readCommand(c);
			break;
		}
	}
	
	public void sendCommand(Command c){
		try {
			c.setProjectID(projectID);
			cc.sendCommand(c);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//project info getters
	public int getID(){
		return projectID;
	}
	public String getTitle(){
		return projectTitle;
	}
	public String getDescription(){
		return projectDescription;
	}
	public String getDueDate(){
		return dueDate;
	}
	public User getUser(){
		return pm.currentUser();
	}
	
	public boolean isLoggedIn(){
		return isLoggedIn;
	}
	
}
