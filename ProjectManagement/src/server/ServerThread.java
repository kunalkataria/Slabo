package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.mysql.jdbc.Driver;

import objects.Event;
import objects.Poll;
import objects.PollItem;
import objects.PollVote;
import objects.ProjectFile;
import objects.ProjectObject;
import objects.TodoTask;
import objects.User;
import server.Command.CommandType;

public class ServerThread extends Thread {
	private ProjectServer psServer;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Connection conn;
	private Socket s;
	private boolean connected;
	public int projectID;
	
	private final String FILE_DIRECTORY = "Files/";
	
	public ServerThread(Socket s, ProjectServer ps) {
		try {
			this.s = s;
			this.psServer = ps;
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			this.start();
			File directory = new File(FILE_DIRECTORY);
			directory.mkdirs();
		} catch (IOException e) {
			e.printStackTrace();
		}
		conn = null;
		try {
			new Driver();
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection
					("jdbc:mysql://localhost/ProjectManagement?user=root&password=kunalkataria");
			connected = true;
		} catch (ClassNotFoundException e) {
			connected = false;
			e.printStackTrace();
		} catch (SQLException e) {
			connected = false;
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		if (s != null) {
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendMessage(Command cmd) {
		try {
			if(cmd.commandType == CommandType.ProjectObject){
				oos.writeObject(cmd);
				oos.flush();
			}
			else if(cmd.getProjectID() == projectID){	
				oos.writeObject(cmd);
				oos.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		// read message
		try {
			Command cmd;
			while (true) {
				cmd = (Command) ois.readObject();
				readCommand(cmd);
			}
		} catch (ClassNotFoundException | IOException e) {
			// do nothing if the client disconnects, just move to finally block
		} finally {
			psServer.removeServerThread(this);
		}
	}
	
	private void readCommand(Command cmd) {
		switch(cmd.commandType) {
		case Login:
			login(cmd);
			break;
		case Register:
			register(cmd);
			break;
		case ProjectList:
			sendProjectList(cmd);
			break;
		case AddProject:
			addProject(cmd);
			break;
		case Calendar:
			calendarChange(cmd);
			break;
		case Chat:
			chatMessage(cmd);
			break;
		case MembersList:
			addMember(cmd);
			break;
		case Poll:
			addPoll(cmd);
			break;
		case PollVote:
			pollVote(cmd);
			break;
		case PollResults:
			getResults(cmd);
			break;
		case ProjectFile:
			addProjectFile(cmd);
			break;
		case Download:
			downloadProjectFile(cmd);
			break;
		case ProjectObject:
			sendProject(cmd);
			break;
		case Project:
			createProject(cmd);
			break;
		case TodoList:
			addTodoListItem(cmd);
			break;
		case UpdateTodo:
			updateTodoListItem(cmd);
			break;
		default:
			break;
		}
	}
	
	private void chatMessage(Command cmd) {
		psServer.sendMessageToAllClients(cmd);
	}
	
	private void downloadProjectFile(Command cmd){
		ProjectFile toSend = (ProjectFile)cmd.getGoods();
		Command sendCommand = new Command();
		sendCommand.setProjectID(cmd.getProjectID());
		sendCommand.commandType = CommandType.Download;
		String filename = FILE_DIRECTORY + cmd.getProjectID()+"_"+toSend.getOwner()+"_"+toSend.getFileName();
		File file = new File(filename);
		if(!file.exists()){
			sendCommand.setSuccessful(false);
			sendMessage(sendCommand);
		}
		else{
			try {
				toSend.importContents(file);
				sendCommand.setGoods(toSend);
				sendCommand.setSuccessful(true);
				
			} catch (FileNotFoundException e) {
				sendCommand.setSuccessful(false);
				e.printStackTrace();
			}
			sendMessage(sendCommand);
		}
	}
	
	private void addProjectFile(Command cmd) {
		ProjectFile pf = (ProjectFile) cmd.getGoods();
		Command sendCommand = new Command();
		sendCommand.commandType = CommandType.ProjectFile;
		sendCommand.setSuccessful(true);
		sendCommand.setProjectID(cmd.getProjectID());
		sendCommand.setGoods(pf);
		try{
			String queryString = "INSERT INTO PROJECTFILES " +
								 "(PROJECTID, FILENAME, DESCRIPTION, USERNAME) " +
								 "VALUES(?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(queryString);
			ps.setInt(1, cmd.getProjectID());
			ps.setString(2, pf.getFileName());
			ps.setString(3, pf.getDescription());
			ps.setString(4, pf.getOwner());
			ps.executeUpdate();
			
			File newFile = new File(FILE_DIRECTORY + cmd.getProjectID()+"_"+pf.getOwner()+"_"+pf.getFileName());
			System.out.println(newFile.getAbsolutePath());
			FileWriter fw = null;
			try {
				newFile.createNewFile();
				fw = new FileWriter(newFile);
				if(pf.getContents() != null){
					fw.write(pf.getContents());
				} else {
					fw.write("[File is Empty]");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				sendCommand.setSuccessful(false);
				e.printStackTrace();
			} finally {
				if(fw != null){
					try {
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (SQLException e){
			sendCommand.setSuccessful(false);
			e.printStackTrace();
		}
		if (sendCommand.isSuccessful()) {
			psServer.sendMessageToAllClients(sendCommand);
		} else {
			sendMessage(sendCommand);
		}
				
	}
	
	private void getResults(Command cmd) {
		Integer pollID = (Integer) cmd.getGoods();
		sendResults(cmd.getUsername(), pollID, true);	
	}
	
	private void sendResults(String username, int pollID, boolean successful) {
		
		Command sendCommand = new Command();
		sendCommand.setProjectID(projectID);
		sendCommand.commandType = CommandType.PollResults;
		
		String title = null;
		try {
			String queryString = "SELECT title " +
									"FROM Polls " +
									"WHERE POLLID=?";
			PreparedStatement ps = conn.prepareStatement(queryString);
			ps.setInt(1, pollID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				title = rs.getString("title");
			}
		} catch (SQLException e) {
			successful = false;
			e.printStackTrace();
		}
		
		Poll resultsPoll = new Poll(title, pollID);

		Vector<PollItem> pollOptions = new Vector<PollItem>();
		
		try {
			String queryString = "SELECT item, votes " + 
									"FROM PollItems " + 
									"WHERE pollID=?";
			PreparedStatement ps = conn.prepareStatement(queryString);
			ps.setInt(1, pollID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pollOptions.add(new PollItem(rs.getString("item"), rs.getInt("votes")));
			}
		} catch (SQLException e) {
			successful = false;
			e.printStackTrace();
		}
		
		resultsPoll.setOptions(pollOptions);
		
		sendCommand.setGoods(resultsPoll);
		sendCommand.setSuccessful(successful);
		sendMessage(sendCommand);
	}

	private void pollVote(Command cmd) {
		PollVote thisVote = (PollVote) cmd.getGoods();
		
		boolean successful = true;
		
		try {
			String queryString = "SELECT VOTES FROM POLLITEMS " +
									"WHERE POLLID=? AND ITEM=?";
			PreparedStatement ps = conn.prepareStatement(queryString);
			ps.setInt(1, thisVote.getPollID());
			ps.setString(2, thisVote.getOption());
			ResultSet rs = ps.executeQuery();
			int votes = -1;
			if (rs.next()) {
				votes = rs.getInt("votes");
				String voteAddition = "UPDATE POLLITEMS " +
										"SET VOTES=? " +
										"WHERE POLLID=? AND ITEM=?";
				PreparedStatement ps2 = conn.prepareStatement(voteAddition);
				ps2.setInt(1, votes + 1);
				ps2.setInt(2, thisVote.getPollID());
				ps2.setString(3, thisVote.getOption());
				ps2.executeUpdate();
			}
		} catch (SQLException e) {
			successful = false;
			e.printStackTrace();
		}
		
		if (successful) {
			try {
				String insertionString = "INSERT INTO PollVoteHistory " + 
											"(pollID, username) " + 
											"VALUES(?,?)";
				PreparedStatement ps = conn.prepareStatement(insertionString);
				ps.setInt(1, thisVote.getPollID());
				ps.setString(2, cmd.getUsername());
				ps.executeUpdate();
			} catch (SQLException e) {
				successful = false;
				e.printStackTrace();
			}
		}
		
		sendResults(cmd.getUsername(), thisVote.getPollID(), successful);
	}
	
	private void addTodoListItem(Command cmd) {
		
		Command sendCommand = new Command();
		sendCommand.commandType = CommandType.TodoList;
		sendCommand.setProjectID(projectID);
		sendCommand.setSuccessful(true);
		
		try {
			String queryString = "INSERT INTO TODOITEMS " +
					"(PROJECTID, TITLE, COMPLETED) " +
					"VALUES(?,?, ?)";
			PreparedStatement ps = conn.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);
			TodoTask task = (TodoTask) cmd.getGoods();
			sendCommand.setGoods(task);
			ps.setInt(1, projectID);
			ps.setString(2, task.getTaskName());
			ps.setBoolean(3, false);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int ret = rs.getInt(1);
				task.setID(ret);
				sendCommand.setGoods(task);
			}
		} catch (SQLException e) {
			sendCommand.setSuccessful(false);
			e.printStackTrace();
		}
		if (sendCommand.isSuccessful()) {
			psServer.sendMessageToAllClients(sendCommand);
		} else {
			sendMessage(sendCommand);
		}
	}
	
	private void updateTodoListItem(Command cmd) {
		TodoTask todoTask = (TodoTask) cmd.getGoods();
		boolean todoCompleted = todoTask.isCompleted();
		Command sendCommand = new Command();
		sendCommand.commandType = CommandType.TodoList;
		sendCommand.setProjectID(projectID);
		sendCommand.setSuccessful(true);
		todoTask.setCompleted(!todoCompleted);
		try {
			String queryString = "UPDATE TODOITEMS " +
					 "SET COMPLETED=? " +
					 "WHERE TODOID=? ";
			PreparedStatement ps = conn.prepareStatement(queryString);
			ps.setBoolean(1, todoTask.isCompleted());
			ps.setInt(2, todoTask.getID());
			ps.executeUpdate();
		} catch (SQLException e) {
			sendCommand.setSuccessful(false);
			e.printStackTrace();
		}
		sendCommand.setGoods(todoTask);
		if (sendCommand.isSuccessful()) {
			psServer.sendMessageToAllClients(sendCommand);
		} else {
			sendMessage(sendCommand);
		}
		
	}
	
	private void addProject(Command cmd) {
		boolean foundProject = false;
		try {
			String checkProject = "SELECT * FROM PROJECTS " +
									"WHERE PROJECTID=?";
			PreparedStatement ps = conn.prepareStatement(checkProject);
			ps.setInt(1, cmd.getProjectID());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				projectID = rs.getInt("projectID");
				foundProject = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		if (!foundProject) {
			Command c = new Command();
			c.commandType = CommandType.AddProject;
			c.setSuccessful(false);
			sendMessage(c);
		} else {
			String queryString = "INSERT INTO PROJECTASSIGNMENTS " +
					"(USERNAME, PROJECTID) " +
					"VALUES(?,?)";
			try {
				PreparedStatement ps = conn.prepareStatement(queryString);
				ps.setString(1, cmd.getUsername());
				ps.setInt(2, cmd.getProjectID());
				ps.executeUpdate();
				String getEmailSQL = "SELECT EMAIL FROM LOGININFO " + 
						"WHERE USERNAME=?";
				PreparedStatement ps2 = conn.prepareStatement(getEmailSQL);
				ps2.setString(1, cmd.getUsername());
				ResultSet rs = ps2.executeQuery();
				if (rs.next()) {
					String userEmail = rs.getString("email");
					User newUser = new User(cmd.getUsername(), userEmail);
					Command sendCommand = new Command();
					sendCommand.commandType = CommandType.MembersList;
					sendCommand.setGoods(newUser);
					sendCommand.setProjectID(projectID);
					psServer.sendMessageToAllClients(sendCommand);
				}
				sendProject(cmd);
				
			} catch (SQLException e) {
				Command c = new Command();
				c.commandType = CommandType.AddProject;
				c.setSuccessful(false);
				sendMessage(c);
				e.printStackTrace();
			}
		}
	}
	
	private void createProject(Command cmd) {
		ProjectObject newProj =  (ProjectObject)cmd.getGoods();
		String title = newProj.getProjectName();
		String description = newProj.getProjectDescription();
		String userOwner = newProj.getOwnerUsername();
		String dueDate = newProj.getProjectDueDate();
		
		Command sendCommand = new Command();
		sendCommand.commandType = CommandType.ProjectObject;
		sendCommand.setSuccessful(true);
		boolean addedSuccessfully = true;
		int currProjectID = -1;
		try {
			String sqlString = "INSERT INTO PROJECTS " +
								"(TITLE, DESCRIPTION, DUEDATE) " +
								"VALUES(?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, title);
			ps.setString(2, description);
			ps.setString(3, dueDate);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				currProjectID = rs.getInt(1);
				projectID = currProjectID;
				sendCommand.setProjectID(projectID);
			} else {
				sendCommand.setSuccessful(false);
			}
		} catch (SQLException e) {
			sendCommand.setSuccessful(false);
			e.printStackTrace();
			addedSuccessfully = false;
		}
		
		if (addedSuccessfully && currProjectID != -1) {
			try {
				String sqlString = "INSERT INTO ProjectAssignments " +
									"(USERNAME, PROJECTID) " +
									"VALUES(?,?)";
				PreparedStatement ps = conn.prepareStatement(sqlString);
				ps.setString(1, userOwner);
				ps.setInt(2, currProjectID);
				ps.executeUpdate();
				newProj.setProjectID(currProjectID);
				String sqlString2 = "SELECT email FROM LoginInfo " +
									"WHERE username=?";
				PreparedStatement ps2 = conn.prepareStatement(sqlString2);
				ps2.setString(1, userOwner);
				ResultSet rs2 = ps2.executeQuery();
				if (rs2.next()) {
					newProj.addMember(new User(userOwner, rs2.getString(1)));
				}
			} catch (SQLException e){
				sendCommand.setSuccessful(false);
				e.printStackTrace();
			}
		}
		sendCommand.setGoods(newProj);
		sendMessage(sendCommand);
	}
	
	private void addPoll(Command cmd) {
		Command sendCommand = new Command();
		sendCommand.commandType = CommandType.Poll;
		sendCommand.setSuccessful(true);
		
		boolean firstInsert = true;
		Poll insertPoll = null;
		try {
			insertPoll = (Poll) cmd.getGoods();
			String queryString = "INSERT INTO POLLS " +
									"(PROJECTID, TITLE, USERNAME) " +
									"VALUES (?,?,?)";
			PreparedStatement ps = conn.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, cmd.getProjectID());
			ps.setString(2, insertPoll.getTitle());
			ps.setString(3, insertPoll.getOwner());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int newID = rs.getInt(1);
				insertPoll.setID(newID);
			}
		} catch (SQLException e) {
			firstInsert = false;
			sendCommand.setSuccessful(false);
			e.printStackTrace();
		}
		
		try {
			if (firstInsert) {
				if (insertPoll != null) {
					Vector<PollItem> pollItems = insertPoll.getOptions();
					String queryString = "INSERT INTO POLLITEMS " + 
										"(POLLID, ITEM, VOTES) " + 
										"VALUES(?, ?, ?)";
					PreparedStatement ps2 = conn.prepareStatement(queryString);
					for (PollItem pi : pollItems) {
						ps2.setInt(1, insertPoll.getID());
						ps2.setString(2, pi.getItem());
						ps2.setInt(3, 0);
						ps2.executeUpdate();
					}
				}
			}
		} catch (SQLException e) {
			sendCommand.setSuccessful(false);
		}
		
		sendCommand.setGoods(insertPoll);
		sendCommand.setProjectID(cmd.getProjectID());
		if (sendCommand.isSuccessful()) {
			psServer.sendMessageToAllClients(sendCommand);
		} else {
			sendMessage(sendCommand);
		}
		
	}
	
	private void addMember(Command cmd) {
		Command sendCommand = new Command();
		sendCommand.commandType = CommandType.MembersList;

		try {
			User newUser = (User) cmd.getGoods();
			sendCommand.setGoods(newUser);
			String statementString = "INSERT INTO PROJECTASSIGNMENTS" +
										"USERNAME, PROJECTID" +
										"VALUES(?,?)";
			PreparedStatement ps = conn.prepareStatement(statementString);
			ps.setString(1, newUser.getUsername());
			ps.setInt(2, cmd.getProjectID());
			ps.executeUpdate();
			sendCommand.setGoods(newUser);
		} catch (SQLException e) {
			sendCommand.setSuccessful(false);
			e.printStackTrace();
		}
		
		// send to all clients with the same project ID, sendCommand
		psServer.sendMessageToAllClients(sendCommand);
	}
	
	private void sendProject(Command cmd){
		projectID = cmd.getProjectID();
		ProjectObject p = null;
		
		Command c = new Command();
		c.commandType = CommandType.ProjectObject;
		c.setProjectID(projectID);
		
		boolean loggedIn = true;
		if (cmd.getUsername() == null) {
			loggedIn = false;
		}
		
		//get da project
		if (!connected) {
			c.setSuccessful(false);
		} else {
			try {
				//getting project information
				PreparedStatement ps = conn.prepareStatement
						("SELECT PROJECTID, TITLE, DESCRIPTION, DUEDATE FROM PROJECTS WHERE PROJECTID =?");
				ps.setInt(1, projectID);
				
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					String title = rs.getString("title");
					String description = rs.getString("description");
					String duedate = rs.getString("duedate");
					p = new ProjectObject(projectID, title, description, duedate);
				}
				
				//getting the calendar
				ps = conn.prepareStatement("SELECT * FROM CALENDAR WHERE PROJECTID =?");
				ps.setInt(1, projectID);
				rs = ps.executeQuery();
				Vector<Event> events = new Vector<Event>();
				while(rs.next()){
					String creator = rs.getString("username");
					String date = rs.getString("date");
					String title = rs.getString("title");
					String description = rs.getString("description");
					int row = rs.getInt("row");
					int col = rs.getInt("col");
					int eventID = rs.getInt(2);
					Event e = new Event(creator, projectID, date, title, description, row, col);
					//getting the rsvps
					PreparedStatement temp = conn.prepareStatement("SELECT * FROM EVENTRSVP WHERE EVENTID =?");
					temp.setInt(1, eventID);
					ResultSet rs2 = temp.executeQuery();
					Vector<String> yes = new Vector<String>();
					Vector<String> no = new Vector<String>();
					Vector<String> maybe = new Vector<String>();
					while(rs2.next()){
						int status = rs.getInt(2);
						String username = rs.getString("username");
						if(status == 0){
							no.add(username);
						}
						else if(status == 1){
							maybe.add(username);
						}
						else{
							yes.add(username);
						}
					}
					e.setNo(no);
					e.setMaybe(maybe);
					e.setYes(yes);
					events.add(e);
				}
				p.setCalendarEvents(events);
				
				//getting members
				String queryString = "SELECT PAsmt.username, login.email " +
										"FROM ProjectAssignments PAsmt " +
										"JOIN LoginInfo login " +
										"ON PAsmt.username = login.username " +
										"WHERE PAsmt.projectID=?";
				ps = conn.prepareStatement(queryString);
				ps.setInt(1, projectID);
				rs = ps.executeQuery();
				Vector<User> users = new Vector<User>();
				while(rs.next()){
					String username = rs.getString("username");
					String email = rs.getString("email");
					User u = new User(username, email);
					users.add(u);
				}
				p.setMembersList(users);
				
				//getting project files
				ps = conn.prepareStatement("SELECT * FROM PROJECTFILES WHERE PROJECTID=?");
				ps.setInt(1, projectID);
				rs = ps.executeQuery();
				Vector<ProjectFile> projectFiles = new Vector<ProjectFile>();
				while(rs.next()){
					String filename = rs.getString("filename");
					String owner = rs.getString("username");
					String description = rs.getString("description");
					ProjectFile pf = new ProjectFile(filename, description, owner);
					projectFiles.add(pf);
				}
				p.setProjectFiles(projectFiles);
				
				//getting polls
				ps = conn.prepareStatement("SELECT * FROM POLLS WHERE PROJECTID=?");
				ps.setInt(1, projectID);
				rs = ps.executeQuery();
				Vector<Poll> polls = new Vector<Poll>();
				while(rs.next()){
					int pollID = rs.getInt(2);
					String pollName = rs.getString("title");
					String username = rs.getString("username");
					Poll newPoll = new Poll(username, pollName, pollID);
					
					if (loggedIn) {
						PreparedStatement ps3 = conn.prepareStatement("SELECT * FROM PollVoteHistory WHERE " +
								"POLLID=? AND USERNAME=?");
						ps3.setInt(1, pollID);
						ps3.setString(2, cmd.getUsername());
						ResultSet rs3 = ps3.executeQuery();
						if (rs3.next()) {
							newPoll.setCanVote(false);
						}
					}
					
					PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM POLLITEMS WHERE POLLID=?");
					ps2.setInt(1, pollID);
					ResultSet rs2 = ps2.executeQuery();
					while(rs2.next()){
						String itemName = rs2.getString("item");
						int votes = rs2.getInt(3);
						PollItem pi = new PollItem(itemName, votes);
						newPoll.addItem(pi);
					}
					polls.add(newPoll);
				}
				p.setPolls(polls);
				
				//getting todolist
				ps = conn.prepareStatement("SELECT * FROM TODOITEMS WHERE PROJECTID=?",  Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, projectID);
				rs = ps.executeQuery();
				Vector<TodoTask> todoList = new Vector<TodoTask>();
				while(rs.next()){
					String taskName = rs.getString("title");
					boolean isCompleted = rs.getBoolean(4);
					TodoTask task = new TodoTask(taskName, isCompleted);
					task.setID(rs.getInt("todoID"));
					todoList.add(task);
				}
				p.setTodoList(todoList);
				c.setGoods(p);
				
				this.projectID = p.getProjectID();
				c.setSuccessful(true);
			} catch (SQLException e) {
				connected = false;
				//successful = false;
				c.setSuccessful(false);
				e.printStackTrace();
			}
			sendMessage(c);
		}
		
	}
	
	private void login(Command cmd) {
		Command sendCommand = new Command();
		sendCommand.commandType = CommandType.Login;
		User u = null;
		
		boolean successful = false;
		if (!connected) {
			successful = false;
		} else {
			try {
				PreparedStatement ps = conn.prepareStatement("SELECT USERNAME, PASSWORD FROM LOGININFO WHERE USERNAME=?");
				ps.setString(1, cmd.getUsername());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					String dbPassword = rs.getString("Password");
					if (dbPassword.equals(cmd.getPassword())) {
						successful = true;
						u = new User(cmd.getUsername(), cmd.getEmail());
						sendCommand.setGoods(u);
					}
				}
			} catch (SQLException e) {
				connected = false;
				successful = false;
				e.printStackTrace();
			}
		}
		sendCommand.setSuccessful(successful);
		sendMessage(sendCommand);
	}
	
	private void register(Command cmd) {
		Command sendCommand = new Command();
		sendCommand.commandType = CommandType.Register;
		User u = null;
		if (!connected) {
			sendCommand.setSuccessful(false);
		} else {
			try {
				PreparedStatement ps = conn.prepareStatement("SELECT USERNAME FROM LOGININFO WHERE USERNAME=?");
				ps.setString(1, cmd.getUsername());
				boolean found = false;
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					found = true;
				}
				if (!found) {
					PreparedStatement ps2 = conn.prepareStatement
							("INSERT INTO LOGININFO (USERNAME, PASSWORD, EMAIL) VALUES(?,?,?)");
					ps2.setString(1, cmd.getUsername());
					ps2.setString(2, cmd.getPassword());
					ps2.setString(3, cmd.getEmail());
					ps2.executeUpdate();
					sendCommand.setSuccessful(true);
					u = new User(cmd.getUsername(), cmd.getEmail());
					sendCommand.setGoods(u);
				} else {
					sendCommand.setSuccessful(false);
				}
			} catch (SQLException sqle) {
				sendCommand.setSuccessful(false);
				connected = false;
				sqle.printStackTrace();
			}
 		}
		sendMessage(sendCommand);
	}
	
	private void sendProjectList(Command cmd) {
		Command sendCommand = new Command();
		sendCommand.commandType = CommandType.ProjectList;
		if (!connected) {
			sendCommand.setSuccessful(false);
		} else {
			String username = cmd.getUsername();
			String queryString = "SELECT PAsmt.projectID, Proj.title " +
									"FROM ProjectAssignments PAsmt " +
									"JOIN Projects Proj " +
									"ON PAsmt.projectID=Proj.projectID " + 
									"WHERE Pasmt.username=?";
			try {
				PreparedStatement ps;
				ps = conn.prepareStatement(queryString);
				ps.setString(1, username);
				ResultSet rs = ps.executeQuery();
				Map<Integer, String> projectIDmap = new HashMap<Integer, String>();
				while(rs.next()) {
					int currentProjectID = rs.getInt("projectID");
					String currentTitle = rs.getString("title");
					projectIDmap.put(currentProjectID, currentTitle);
				}
				sendCommand.setGoods(projectIDmap);
				sendCommand.setSuccessful(true);
			} catch (SQLException e) {
				sendCommand.setSuccessful(false);
				e.printStackTrace();
			}
		}
		sendMessage(sendCommand);
	}
	
	private void calendarChange(Command cmd) {
		Event evt = (Event) cmd.getGoods();
		cmd.setSuccessful(true);
		try {
			String queryString = "INSERT INTO Calendar" +
									"(PROJECTID, DESCRIPTION, USERNAME, TITLE, DATE, ROW, COL)" +
									"VALUES(?,?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(queryString);
			ps.setInt(1, cmd.getProjectID());
			ps.setString(2, evt.getDescription());
			ps.setString(3, evt.getCreator());
			ps.setString(4, evt.getTitle());
			ps.setString(5, evt.getDate());
			ps.setInt(6, evt.getRow());
			ps.setInt(7, evt.getCol());
			ps.executeUpdate();
		} catch (SQLException e) {
			cmd.setSuccessful(false);
			e.printStackTrace();
		}
		
		if (cmd.isSuccessful()) {
			psServer.sendMessageToAllClients(cmd);
		} else {
			sendMessage(cmd);
		}
		
	}
	
}
