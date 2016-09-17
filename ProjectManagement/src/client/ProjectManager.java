package client;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import aurelienribon.slidinglayout.SLAnimator;
import aurelienribon.tweenengine.Tween;
import gui.Login;
import objects.ProjectObject;
import objects.User;
import server.Command;
import server.Command.CommandType;
import testing.TestProject;
import testing.ThePanel;

public class ProjectManager{
	
	private Project currentProject;
	private ClientCommunicator cc;
	private Login login;
	private ProjectList projectlist;
	private User currentUser;
	private TestProject tp;
	private boolean isLoggedIn;

	private boolean isNewUser = false;
	private static final String fontPath = "./resource/Slabo_27px/Slabo27px-Regular.ttf";

	public ProjectManager(){
		
		Font f = null;
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath));
		} catch (FontFormatException | IOException e) {		}
		f = f.deriveFont(Font.BOLD, 14);
		setUIFont(f);
		UIManager.put("ScrollBar.width", new Integer(0)); 
		Tween.registerAccessor(ThePanel.class, new ThePanel.Accessor());
		SLAnimator.start();
		
		cc = new ClientCommunicator(6789, this);
		login = new Login(cc);

		tp = new TestProject(login, this);
		login.setTestProject(tp);
		tp.setLocationRelativeTo(null);
		//tp.setLogin(login);
		tp.setVisible(true);
		isLoggedIn = true;
		
	}
	
	public Login logout(){
		isLoggedIn = false;
		isNewUser = false;
		currentUser = null;
		projectlist = null;
		currentProject = null;
		cc = new ClientCommunicator(6789,this);
		login = new Login(cc);
		login.setTestProject(tp);
		return login;
	}
	
	public static void setUIFont (Font font){
	FontUIResource f = new FontUIResource(font);
    Enumeration<Object> keys = UIManager.getDefaults().keys();
    while (keys.hasMoreElements()) {
      Object key = keys.nextElement();
      Object value = UIManager.get (key);
      if (value != null && value instanceof javax.swing.plaf.FontUIResource)
        UIManager.put (key, f);
      }
    } 

	public void readCommand(Command c){
		switch(c.commandType){
		case Login:
			if(c.isSuccessful()){
				//removes login, displays project list
				currentUser = (User) c.getGoods();
				if (currentUser == null){
					System.out.println("No user");
				}
				isLoggedIn = true;
				getProjectList();
				
			}
			else{
				//display message
				showError("Login Failed", "Invalid Username or Password");
			}
			break;	
		case Register:
			if(c.isSuccessful()){
				//removes login, displays empty projectlist
				currentUser = (User) c.getGoods();
				isLoggedIn = true;
				isNewUser = true;
				getProjectList(); //user has no projects
			}
			else{
				//display message
				showError("Register failed", "Sign-Up error");
			}
			break;
		case ProjectList:
			@SuppressWarnings("unchecked")
			HashMap<Integer, String> projectList = (HashMap<Integer, String>) c.getGoods(); //might need to make a class
			projectlist = new ProjectList(projectList, this);
			tp.setProjectList(projectlist);
			if(isNewUser){
				tp.signUpSuccess();
			}else{
				tp.login();
			}
			break;
		
		case ProjectObject:
			if (c.isSuccessful()) {
				ProjectObject projOb = (ProjectObject) c.getGoods();
				if(currentUser == null){
					System.out.println("OFFLINE MODE!");
					isLoggedIn = false;
				}
				if(currentUser != null){
					projectlist.addProject((Integer)c.getProjectID(), projOb.getProjectName());
				}
				currentProject = new Project(this, projOb, cc, tp, isLoggedIn);
				//display the new project
			} else {
				showError("Server error", "Error in opening project");
			}
			
		default:
			if(currentProject != null){
				currentProject.readCommand(c);
			}
			break;
		}
	
	}
	
	private void showError(String windowTitle, String message) {
		JOptionPane.showMessageDialog(tp, message, windowTitle, JOptionPane.ERROR_MESSAGE);
	}
	
	public void sendCommand(Command c){
		try {
			if (currentProject != null) {
				c.setProjectID(currentProject.getID());
			}
			if(currentUser != null){
				c.setUsername(currentUser.getUsername());
			}
			cc.sendCommand(c);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getProjectList(){
		Command c = new Command();
		c.commandType = CommandType.ProjectList;
		c.setUsername(currentUser.getUsername());
		c.setGoods(currentUser);
		if (currentProject != null){
			currentProject = null;
		}
		try {
			cc.sendCommand(c);
		} catch (IOException e) {
			//display message server can't be reached
			showError("server error", "server cannot be reached");
		}
	}
	
	public User currentUser(){
		return currentUser;
	}
	
	public boolean isLoggedIn(){
		return isLoggedIn;
	}
	
	public static void main(String[] args){
		@SuppressWarnings("unused")
		ProjectManager projectManager = new ProjectManager();
	}
	
}
