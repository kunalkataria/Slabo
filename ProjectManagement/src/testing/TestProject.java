package testing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import aurelienribon.slidinglayout.SLAnimator;
import aurelienribon.slidinglayout.SLConfig;
import aurelienribon.slidinglayout.SLKeyframe;
import aurelienribon.slidinglayout.SLPanel;
import aurelienribon.slidinglayout.SLSide;
import client.ProjectList;
import client.ProjectManager;
import gui.AboutGUI;
import gui.CalendarGUI;
import gui.ChatGUI;
import gui.FilesGUI;
import gui.Login;
import gui.MembersListGUI;
import gui.PollsGUI;
import gui.ToDoGUI;

public class TestProject extends JFrame{
	private static final long serialVersionUID = 2036201929448744914L;
	private ProjectManager projectManager;
	private final SLPanel panel = new SLPanel();
	
	//Title Bar
	private ThePanel pTitle = new ThePanel("Title", "img/ic_info_white_24dp_2x.pngs");
	private ThePanel pHome = new ThePanel("Home", "data/img1.jpgs"); //logout
	private ThePanel pToggle = new ThePanel("Toggle", "data/img1.jpgs"); //toggle icons
	private ThePanel pBack = new ThePanel("Back", "data/img1.jpgs"); 
	
	//About
	private ThePanel p1Icon = new ThePanel("AboutIcon", "img/ic_info_white_24dp_2x.png");
	private ThePanel p1 = new ThePanel("About", "data/img1.jpg");

	//ToDo
	private ThePanel p2Icon = new ThePanel("ToDoIcon", "img/ic_info_white_24dp_2x.png");
	private ThePanel p2 = new ThePanel("ToDo", "data/img2.jpg");	
	private ThePanel p25 = new ThePanel("add ToDo task", "data/img2.jpg");
	
	//Chat
	//private ThePanel p3Icon = new ThePanel("ChatIcon", "img/ic_info_white_24dp_2x.png");
	private ThePanel p3 = new ThePanel("Chat", "data/img3.jpg");
	
	//Polls
	private ThePanel p4Icon = new ThePanel("PollsIcon", "img/ic_info_white_24dp_2x.png");
	private ThePanel p4 = new ThePanel("Polls", "data/img4.jpg");
	
	//Files
	private ThePanel p5Icon = new ThePanel("FilesIcon", "img/ic_info_white_24dp_2x.png");
	private ThePanel p5 = new ThePanel("Files", "data/img5.jpg");
	private ThePanel p55 = new ThePanel("Files Not Label", "data/img5.jpg"); 
	private ThePanel p51 = new ThePanel("Files Info", "data/img5.jpg");
	private ThePanel p52 = new ThePanel("Create Files", "data/img5.jpg");

	//Calendar
	private ThePanel p6Icon = new ThePanel("CalendarIcon", "img/ic_info_white_24dp_2x.png");
	private ThePanel p6 = new ThePanel("Calendar Grid", "data/old.jpg");
	
	//etc.
	private ThePanel p7 = new ThePanel("Members", "data/eddie.jpg");
	private ThePanel p8 = new ThePanel("Voting/Results", "data/jon.jpg");
	private ThePanel p9 = new ThePanel("Calendar", "data/monkey.jpg");
	private ThePanel p95 = new ThePanel("Create Calendar", "aimg/ic_info_white_48dp_1x.png");
	private ThePanel p10 = new ThePanel("Login", "data/spongebob.jpg");
	//private ThePanel p10 = new ThePanel(new Login(null));
	private ThePanel p11 = new ThePanel("Project List", "data/kunal.jpg");
	//signup
	private ThePanel p12 = new ThePanel("Sign Up", "data/kunal.jpg");
	
	private boolean iconOn = true;
	private boolean pollOpen = false;
	private boolean filesOpen = false;
	private boolean calendarOpen = false;
	private boolean toDoOpen = false;
	private boolean chatOpen = false;
	private boolean guest = false;
	
	private SLConfig mainIconCfg, mainCfg, loginCfg, pListCfg, aboutCfg, aboutChatCfg, todoCfg, todoChatCfg,
					chatCfg, chatIconCfg, filesCfg, filesChatCfg, calendarCfg, calendarChatCfg,
					pollCfg, pollChatCfg, signUpCfg;
	
	public TestProject(Login login, ProjectManager pm){
		//setSize(800, 600);
		projectManager = pm;
		p10 = new ThePanel(login, false);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();
		width = 5*width/8;
		height = 6*height/7;
		setSize(width,height);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Slabo");
		getContentPane().setBackground(Color.black);
		getContentPane().add(panel, BorderLayout.CENTER);
		
		p1.setAction(p1Action);
		p2.setAction(p2Action);
		p3.setAction(p3Action);
		p4.setAction(p4Action);
		p5.setAction(p5Action);
		p6.setAction(p6Action);
		
		p1Icon.setAction(p1Action);
		p2Icon.setAction(p2Action);
		//p3Icon.setAction(p3Action);
		p4Icon.setAction(p4Action);
		p5Icon.setAction(p5Action);
		p6Icon.setAction(p6Action);
		
		p10.setAction(loginAction);
		p11.setAction(projectAction);
		p55.setAction(p5BackAction);
		pToggle.setAction(pToggleIcon);
		pHome.setAction(pBackToLogin);
		pBack.setAction(pBackToProjectList);
		
		//project Icon Config
		mainIconCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(1f).col(1f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pToggle)
					.place(0, 3, pBack)
				.endGrid()
				.beginGrid(1,0 )
					.row(1f).col(1f).col(1f)
					.beginGrid(0, 0)
						.row(1f).row(3f).row(1f).col(1f)
						.place(0, 0, p1Icon)
						.place(1, 0, p2Icon)
						.place(2, 0, p3)
					.endGrid()
					.beginGrid(0,1)
						.row(2f).row(1f).row(4f).col(1f)
						.place(0,0,p4Icon)
						.place(1,0,p5Icon)
						.place(2,0,p6Icon)
					.endGrid()
				.endGrid();

		mainCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(1f).col(1f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pToggle)
					.place(0, 3, pBack)
				.endGrid()
				.beginGrid(1,0 )
					.row(1f).col(1f).col(1f)
					.beginGrid(0, 0)
						.row(1f).row(3f).row(1f).col(1f)
						.place(0, 0, p1)
						.place(1, 0, p2)
						.place(2, 0, p3)
					.endGrid()
					.beginGrid(0,1)
						.row(2f).row(1f).row(4f).col(1f)
						.place(0,0,p4)
						.place(1,0,p5)
						.place(2,0,p6)
					.endGrid()
				.endGrid();
	
		/*//project Config
		mainCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).col(1f).col(1f)
				.beginGrid(0, 0)
					.row(1f).row(3f).row(1f).col(1f)
					.place(0, 0, p1)
					.place(1, 0, p2)
					.place(2, 0, p3)
				.endGrid()
				.beginGrid(0,1)
					.row(2f).row(1f).row(4f).col(1f)
					.place(0,0,p4)
					.place(1,0,p5)
					.place(2,0,p6)
				.endGrid();*/
			
		//Login
		loginCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).col(1f)
				.place(0, 0, p10);

		//Project List
		pListCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(11f).col(1f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
				.endGrid()
				.beginGrid(1, 0)
					.gap(10, 10)
					.row(1f).col(1f)
					.place(0, 0, p11)
				.endGrid();
	
		//About Config
		aboutCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(2f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pBack)
				.endGrid()
				.beginGrid(1, 0)
					.row(1f).row(1f).col(1f)
					.place(0, 0, p1)
					.beginGrid(1, 0)
						.row(1f).col(1f).col(1f)
						.place(0, 0, p3)
						.place(0, 1, p7)
					.endGrid()
				.endGrid();
		
		//About Chat Config
		aboutChatCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(2f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pBack)
				.endGrid()
				.beginGrid(1, 0)
					.row(1f).col(1f).col(1f)
					.place(0, 0, p3)
					.beginGrid(0, 1)
						.row(1f).row(1f).col(1f)
						.place(0, 0, p1)
						.place(1, 0, p7)
					.endGrid()
				.endGrid();

		//To-Do Config
		todoCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(2f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pBack)
				.endGrid()
				.beginGrid(1, 0)
					.row(4f).row(1f).col(1f)
					.place(0, 0, p2)
					.beginGrid(1, 0)
						.row(1f).col(1f).col(1f)
						.place(0, 0, p3)
						.place(0, 1, p25)
					.endGrid()
				.endGrid();

		//To-Do Chat Config
		todoChatCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(2f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pBack)
				.endGrid()
				.beginGrid(1, 0)
					.row(1f).col(1f).col(1f)
					.place(0, 0, p3)
					.beginGrid(0, 1)
						.row(4f).row(1f).col(1f)
						.place(0, 0, p2)
						.place(1, 0, p25)
					.endGrid()
				.endGrid();
		
		//Chat Config
		chatCfg = new SLConfig(panel)
				.gap(10, 10)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(1f).col(1f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pToggle)
					.place(0, 3, pBack)
				.endGrid()
				.beginGrid(1, 0)
					.row(1f).col(1f).col(1f)
					.beginGrid(0, 0)
						.row(1f).row(4f).col(1f)
						.place(0, 0, p1)
						.place(1, 0, p3)
					.endGrid()
					.beginGrid(0,1)
						.row(2f).row(1f).row(4f).col(1f)
						.place(0,0,p4)
						.place(1,0,p5)
						.place(2,0,p6)
					.endGrid()
				.endGrid();
		
		//Chat Icon Config
		chatIconCfg = new SLConfig(panel)
				.gap(10, 10)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(1f).col(1f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pToggle)
					.place(0, 3, pBack)
				.endGrid()
				.beginGrid(1, 0)
					.row(1f).col(1f).col(1f)
					.beginGrid(0, 0)
						.row(1f).row(4f).col(1f)
						.place(0, 0, p1Icon)
						.place(1, 0, p3)
					.endGrid()
					.beginGrid(0,1)
						.row(2f).row(1f).row(4f).col(1f)
						.place(0,0,p4Icon)
						.place(1,0,p5Icon)
						.place(2,0,p6Icon)
					.endGrid()
				.endGrid();
		
		
		//Polls Config
		pollCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(2f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pBack)
				.endGrid()
				.beginGrid(1, 0)
					.row(1f).col(1f).col(1f)
					.beginGrid(0, 0)
						.row(4f).row(1f).col(1f)
						.place(0, 0, p4)
						.place(1, 0, p3)
					.endGrid()
					.place(0, 1, p8)
				.endGrid();
		
		//Polls Chat Config
		pollChatCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(2f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pBack)
				.endGrid()
				.beginGrid(1, 0)
					.row(1f).col(1f).col(1f)
					.place(0, 0, p3)
					.beginGrid(0, 1)
						.row(1f).row(1f).col(1f)
						.place(0, 0, p4)
						.place(1, 0, p8)
					.endGrid()
				.endGrid();
		
		//Files Config 
		filesCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(2f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pBack)
				.endGrid()
				.beginGrid(1, 0)				
					.row(1f).col(1f).col(1f)
					.beginGrid(0, 0)
						.row(4f).row(1f).col(1f)
						.place(0, 0, p55)
						.place(1, 0, p3)
					.endGrid()
						.beginGrid(0,1)
							.row(1f).row(1f).col(1f)
							.place(0, 0, p51)
							.place(1, 0, p52)
						.endGrid()
				.endGrid();
		
		//Files Config 
		filesChatCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(2f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pBack)
				.endGrid()
				.beginGrid(1, 0)				
					.row(1f).col(1f).col(1f)
					.beginGrid(0, 0)
						.row(1f).row(3f).col(1f)
						.place(0, 0, p55)
						.place(1, 0, p3)
					.endGrid()
						.beginGrid(0,1)
							.row(1f).row(1f).col(1f)
							.place(0, 0, p51)
							.place(1, 0, p52)
						.endGrid()
				.endGrid();
								
		//Calendar Config
		calendarCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(2f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pBack)
				.endGrid()
				.beginGrid(1, 0)
					.row(1f).col(1f).col(1f)
					.beginGrid(0, 0)
						.row(4f).row(1f).col(1f)
						.place(0, 0, p9)
						.place(1, 0, p3)
					.endGrid()
					.beginGrid(0,1)
						.row(1f).row(1f).col(1f)
						.place(0, 0, p6)
						.place(1, 0, p95)		
					.endGrid()
				.endGrid();
		
		//Calendar Chat Config
		calendarChatCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(2f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pBack)
				.endGrid()
				.beginGrid(1, 0)
					.row(1f).col(1f).col(1f)
					.beginGrid(0, 0)
						.row(1f).row(3f).col(1f)
						.place(0, 0, p9)
						.place(1, 0, p3)
					.endGrid()
					.beginGrid(0,1)
						.row(1f).row(1f).col(1f)
						.place(0, 0, p6)
						.place(1, 0, p95)		
					.endGrid()
				.endGrid();
	
		//signUp Config
		signUpCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).col(1f)
				.place(0, 0, p12);
		

		initializeIcons();
		panel.setTweenManager(SLAnimator.createTweenManager());
		panel.initialize(loginCfg);
		//panel.initialize(mainIconCfg);
		iconOn = true;
		
	}
	
	//fixs
	private void initializeIcons(){
		pTitle.disableAction();
		
		ImageIcon img = new ImageIcon("icons/smallHome.png");
		JLabel tempL = new JLabel(img, JLabel.CENTER);
		pHome.add(tempL, BorderLayout.CENTER);
		pHome.setBackground(Color.DARK_GRAY);
		
		img = new ImageIcon("icons/toggle.png");
		tempL = new JLabel(img, JLabel.CENTER);
		pToggle.add(tempL, BorderLayout.CENTER);
		pToggle.setBackground(Color.DARK_GRAY);
	
		img = new ImageIcon("icons/back.png");
		tempL = new JLabel(img, JLabel.CENTER);
		pBack.add(tempL, BorderLayout.CENTER);
		pBack.setBackground(Color.DARK_GRAY);
		
		img = new ImageIcon("icons/slabosmall.png");
		tempL = new JLabel(img, JLabel.CENTER);
		pTitle.add(tempL, BorderLayout.CENTER);
		pTitle.setBackground(Color.BLACK);
		
		img = new ImageIcon("icons/about.png");
		tempL = new JLabel(img, JLabel.CENTER);
		p1Icon.add(tempL, BorderLayout.CENTER);
		p1Icon.setBackground(Color.decode("#AA7CDF"));

		img = new ImageIcon("icons/todo.png");
		tempL = new JLabel(img, JLabel.CENTER);
		p2Icon.add(tempL, BorderLayout.CENTER);
		p2Icon.setBackground(Color.decode("#FB516F"));
		
		img = new ImageIcon("icons/todo.png");
		tempL = new JLabel(img, JLabel.CENTER);
		p25.add(tempL, BorderLayout.CENTER);
		p25.setBackground(Color.decode("#FB516F"));
		p25.disableAction();
		
		//polls
		
		img = new ImageIcon("icons/polls.png");
		tempL = new JLabel(img, JLabel.CENTER);
		p4Icon.add(tempL, BorderLayout.CENTER);
		p4Icon.setBackground(Color.decode("#FF9B3E"));
		
		img = new ImageIcon("icons/polls.png");
		tempL = new JLabel(img, JLabel.CENTER);
		p8.add(tempL, BorderLayout.CENTER);	
		p8.setBackground(Color.decode("#FF9B3E"));
		p8.disableAction();

		//files
		p5.setBackground(Color.decode("#59EC99"));
		
		img = new ImageIcon("icons/smallFiles.png");
		tempL = new JLabel(img, JLabel.CENTER);
		p5Icon.add(tempL, BorderLayout.CENTER);
		p5Icon.setBackground(Color.decode("#59EC99"));
		
		img = new ImageIcon("icons/about.png");
		tempL = new JLabel(img, JLabel.CENTER);
		p51.add(tempL, BorderLayout.CENTER);
		p51.setBackground(Color.decode("#59EC99"));
		p51.disableAction();
		
		img = new ImageIcon("icons/smallFiles.png");
		tempL = new JLabel(img, JLabel.CENTER);
		p52.add(tempL, BorderLayout.CENTER);
		p52.setBackground(Color.decode("#59EC99"));
		p52.disableAction();
		
		//calendar
		img = new ImageIcon("icons/calendar.png");
		tempL = new JLabel(img, JLabel.CENTER);
		p6Icon.add(tempL, BorderLayout.CENTER);
		p6Icon.setBackground(Color.decode("#45B3D7"));
		
		img = new ImageIcon("icons/calendar.png");
		tempL = new JLabel(img, JLabel.CENTER);
		p95.add(tempL, BorderLayout.CENTER);
		p95.setBackground(Color.decode("#45B3D7"));
		p95.disableAction();
		
		repaint();
	}
	
	private void ToggleActions(){
		if (iconOn){
			p1.disableAction();
			p2.disableAction();
			//p3.disableAction();
			p4.disableAction();
			p5.disableAction();
			p6.disableAction();
			p1Icon.enableAction();
			p2Icon.enableAction();
			p3.enableAction();
			//p3Icon.enableAction();
			p4Icon.enableAction();
			p5Icon.enableAction();
			p6Icon.enableAction();
		}
		else{
			p1Icon.disableAction();
			p2Icon.disableAction();
			//p3Icon.disableAction();
			p4Icon.disableAction();
			p5Icon.disableAction();
			p6Icon.disableAction();
			p1.enableAction();
			p2.enableAction();
			p3.enableAction();
			p4.enableAction();
			p5.enableAction();
			p6.enableAction();
		}
		
		
	}
	
	private void disableActions() {
		p1.disableAction();
		p2.disableAction();
		p3.disableAction();
		p4.disableAction();
		p5.disableAction();
		p6.disableAction();
		p7.disableAction();
		p8.disableAction();
		p9.disableAction();
	}

	private void enableActions() {
		p1.enableAction();
		p2.enableAction();
		p3.enableAction();
		p4.enableAction();
		p5.enableAction();
		p6.enableAction();
		p7.enableAction();
		p8.enableAction();
		p9.enableAction();
	}
	
	private final Runnable signUpAction = new Runnable() {@Override public void run() {
		disableActions();

		panel.createTransition()
			.push(new SLKeyframe(signUpCfg, 0.6f)
				.setEndSide(SLSide.LEFT, p10)
				.setStartSide(SLSide.RIGHT, p12)
				.setDelay(0.6f, p12)
				/*.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					p10.disableAction();
					p11.enableAction();
				}})*/)
			.play();
	}};

	private final Runnable signUpBackAction = new Runnable() {@Override public void run() {
		disableActions();

		panel.createTransition()
			.push(new SLKeyframe(loginCfg, 0.6f)
				.setEndSide(SLSide.LEFT, p12)
				.setStartSide(SLSide.RIGHT, p10)
				.setDelay(0.6f, p10)
				/*.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					p10.disableAction();
					p11.enableAction();
				}})*/)
			.play();
	}};
	
	private final Runnable signUpSuccessAction = new Runnable() {@Override public void run() {
		disableActions();

		panel.createTransition()
			.push(new SLKeyframe(pListCfg, 0.6f)
				.setEndSide(SLSide.LEFT, p12)
				.setStartSide(SLSide.RIGHT, p11)
				.setStartSide(SLSide.TOP, pTitle, pHome)
				.setDelay(0.6f, p11, pTitle, pHome)
				/*.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					p10.disableAction();
					p11.enableAction();
				}})*/)
			.play();
	}};
	
	private final Runnable loginAction = new Runnable() {@Override public void run() {
		disableActions();

		panel.createTransition()
			.push(new SLKeyframe(pListCfg, 0.6f)
				.setEndSide(SLSide.LEFT, p10)
				.setStartSide(SLSide.RIGHT, p11)
				.setStartSide(SLSide.TOP, pTitle, pHome)
				.setDelay(0.6f, p11, pTitle, pHome)
				/*.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					p10.disableAction();
					p11.enableAction();
				}})*/)
			.play();
	}};
	
	private final Runnable guestAction = new Runnable() {@Override public void run() {
		disableActions();

		panel.createTransition()
			.push(new SLKeyframe(mainIconCfg, 0.6f)
				.setEndSide(SLSide.LEFT, p10)
				.setStartSide(SLSide.TOP, pTitle, pHome, pToggle, pBack, p1Icon, p2Icon ,p3, p4Icon, p5Icon, p6Icon)
				.setDelay(0.6f, pTitle, pHome, pToggle, pBack, p1Icon, p2Icon ,p3, p4Icon, p5Icon, p6Icon)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					//p10.disableAction();
					//p11.enableAction();
					enableActions();
				}}))
			.play();
	}};
	
	private final Runnable projectAction = new Runnable() {@Override public void run() {
		disableActions();
		iconOn = true;
		
		panel.createTransition()
			.push(new SLKeyframe(mainIconCfg, 0.6f)
				.setEndSide(SLSide.BOTTOM, p11)
				.setStartSide(SLSide.TOP, pToggle, pBack, p1Icon, p2Icon ,p3, p4Icon, p5Icon, p6Icon)
				.setDelay(0.6f, pToggle, pBack, p1Icon, p2Icon, p3, p4Icon, p5Icon, p6Icon)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					p11.disableAction();
					ToggleActions();
				}}))
			.play();
	}};
	
	//About Action
	private final Runnable p1Action = new Runnable() {@Override public void run() {
		disableActions();
		if (chatOpen){
			if(iconOn){
				panel.createTransition()
					.push(new SLKeyframe(aboutChatCfg, 0.6f)
						.setEndSide(SLSide.TOP, pToggle)
						.setEndSide(SLSide.BOTTOM, p1Icon, p4Icon, p5Icon, p6Icon)
						.setStartSide(SLSide.BOTTOM, p1, p7)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {
							pBack.setAction(p1BackAction);
							p3.setAction(p1ChatBackAction);
							p3.enableAction();
						}}))
					.play();
			}
			else{

				panel.createTransition()
					.push(new SLKeyframe(aboutCfg, 0.6f)
						.setEndSide(SLSide.TOP, pToggle)
						.setEndSide(SLSide.BOTTOM, p4, p5, p6)
						.setStartSide(SLSide.BOTTOM, p7)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {
							pBack.setAction(p1BackAction);
							p3.setAction(p1ChatBackAction);
							p3.enableAction();
						}}))
					.play();
			}
		}
		else{
			if(iconOn){
				panel.createTransition()
					.push(new SLKeyframe(aboutCfg, 0.6f)
						.setEndSide(SLSide.TOP, pToggle)
						.setEndSide(SLSide.BOTTOM, p1Icon, p2Icon, p4Icon, p5Icon, p6Icon)
						.setStartSide(SLSide.BOTTOM, p1, p7)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {
							pBack.setAction(p1BackAction);
							p3.setAction(p1ChatAction);
							p3.enableAction();
						}}))
					.play();
			}
			else{

				panel.createTransition()
					.push(new SLKeyframe(aboutCfg, 0.6f)
						.setEndSide(SLSide.TOP, pToggle)
						.setEndSide(SLSide.BOTTOM, p2, p4, p5, p6)
						.setStartSide(SLSide.BOTTOM, p7)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {
							pBack.setAction(p1BackAction);
							p3.setAction(p1ChatAction);
							p3.enableAction();
						}}))
					.play();
			}
		}
		
		}};

	private final Runnable p1BackAction = new Runnable() {@Override public void run() {
		disableActions();

		if(chatOpen){
			if (iconOn){
				panel.createTransition()
				.push(new SLKeyframe(chatIconCfg, 0.6f)
					.setStartSide(SLSide.TOP, pToggle)
					.setEndSide(SLSide.BOTTOM, p1, p7)
					.setStartSide(SLSide.BOTTOM, p1Icon, p4Icon, p5Icon, p6Icon)
					.setDelay(0.6f, p1Icon, p4Icon, p5Icon, p6Icon)
					.setCallback(new SLKeyframe.Callback() {@Override public void done() {
						p3.setAction(p3BackAction);
						if (guest){
							pBack.setAction(pBackToLogin);
						}else{
							pBack.setAction(pBackToProjectList);
						}
						enableActions();
					}}))
				.play();
			}
			else{
				panel.createTransition()
				.push(new SLKeyframe(chatCfg, 0.6f)
					.setStartSide(SLSide.TOP, pToggle)
					.setEndSide(SLSide.BOTTOM, p7)
					.setStartSide(SLSide.BOTTOM, p4, p5, p6)
					.setDelay(0.6f, p4,p5,p6)
					.setCallback(new SLKeyframe.Callback() {@Override public void done() {
						p3.setAction(p3BackAction);
						if (guest){
							pBack.setAction(pBackToLogin);
						}else{
							pBack.setAction(pBackToProjectList);
						}
						enableActions();
					}}))
				.play();
			}
		}
		else{
			if (iconOn){
				panel.createTransition()
				.push(new SLKeyframe(mainIconCfg, 0.6f)
					.setStartSide(SLSide.TOP, pToggle)
					.setEndSide(SLSide.BOTTOM, p1, p7)
					.setStartSide(SLSide.BOTTOM, p1Icon, p2Icon, p4Icon, p5Icon, p6Icon)
					.setDelay(0.6f, p1Icon, p2Icon, p4Icon, p5Icon, p6Icon)
					.setCallback(new SLKeyframe.Callback() {@Override public void done() {
						p3.setAction(p3Action);
						if (guest){
							pBack.setAction(pBackToLogin);
						}else{
							pBack.setAction(pBackToProjectList);
						}
						enableActions();
					}}))
				.play();
			}
			else{
				panel.createTransition()
				.push(new SLKeyframe(mainCfg, 0.6f)
					.setStartSide(SLSide.TOP, pToggle)
					.setEndSide(SLSide.BOTTOM, p7)
					.setStartSide(SLSide.BOTTOM, p2, p4, p5, p6)
					.setDelay(0.6f, p2, p4,p5,p6)
					.setCallback(new SLKeyframe.Callback() {@Override public void done() {
						p3.setAction(p3Action);
						if (guest){
							pBack.setAction(pBackToLogin);
						}else{
							pBack.setAction(pBackToProjectList);
						}
						enableActions();
					}}))
				.play();
			}
		}
	}};
	
	private final Runnable p1ChatAction = new Runnable() {@Override public void run() {
		disableActions();
		chatOpen = true;
		
		panel.createTransition()
			.push(new SLKeyframe(aboutChatCfg, 0.6f)
				.setDelay(0.6f, p3)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					p3.setAction(p1ChatBackAction);
					p3.enableAction();
				}}))
			.play();
	}};
	
	private final Runnable p1ChatBackAction = new Runnable() {@Override public void run() {
		disableActions();
		chatOpen = false;
		
		panel.createTransition()
			.push(new SLKeyframe(aboutCfg, 0.6f)
				.setDelay(0.6f, p1)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					p3.setAction(p1ChatAction);
					p3.enableAction();
				}}))
			.play();
	}};

	//ToDo Action
	private final Runnable p2Action = new Runnable() {@Override public void run() {
		disableActions();
		toDoOpen = true;
		
		if (iconOn){
			panel.createTransition()
				.push(new SLKeyframe(todoCfg, 0.6f)
					.setEndSide(SLSide.LEFT, p1Icon, p2Icon)
					.setEndSide(SLSide.RIGHT, p4Icon, p5Icon, p6Icon)
					.setStartSide(SLSide.RIGHT, p2, p25)
					.setDelay(0.6f, p2, p25)
					.setCallback(new SLKeyframe.Callback() {@Override public void done() {
						pBack.setAction(p2BackAction);
						p3.setAction(p2ChatAction);
						p3.enableAction();
					}}))
				.play();
		}
		else{
			panel.createTransition()
				.push(new SLKeyframe(todoCfg, 0.6f)
					.setEndSide(SLSide.LEFT, p1)
					.setEndSide(SLSide.RIGHT, p4, p5, p6)
					.setStartSide(SLSide.RIGHT, p25)
					.setDelay(0.6f, p2, p25)
					.setCallback(new SLKeyframe.Callback() {@Override public void done() {
						pBack.setAction(p2BackAction);
						p3.setAction(p2ChatAction);
						p3.enableAction();
					}}))
				.play();
		}
	}};

	private final Runnable p2BackAction = new Runnable() {@Override public void run() {
		toDoOpen = false;
		disableActions();

		if(chatOpen){
			if(iconOn){
				panel.createTransition()
				.push(new SLKeyframe(chatIconCfg, 0.6f)
					.setEndSide(SLSide.RIGHT, p2, p25)
					.setStartSide(SLSide.LEFT, p1Icon)
					.setStartSide(SLSide.RIGHT, p4Icon, p5Icon, p6Icon)
					.setDelay(0.6f, p1Icon, p4Icon, p5Icon, p6Icon)
					.setCallback(new SLKeyframe.Callback() {@Override public void done() {
						p3.setAction(p3BackAction);
						if (guest){
							pBack.setAction(pBackToLogin);
						}else{
							pBack.setAction(pBackToProjectList);
						}
						enableActions();
					}}))
				.play();
			}
			else{
				panel.createTransition()
				.push(new SLKeyframe(chatCfg, 0.6f)
					.setEndSide(SLSide.RIGHT, p25, p2)
					.setStartSide(SLSide.LEFT, p1)
					.setStartSide(SLSide.RIGHT, p4, p5, p6)
					.setDelay(0.6f, p1, p4, p5, p6)
					.setCallback(new SLKeyframe.Callback() {@Override public void done() {
						p3.setAction(p3BackAction);
						if (guest){
							pBack.setAction(pBackToLogin);
						}else{
							pBack.setAction(pBackToProjectList);
						}
						enableActions();
					}}))
				.play();
			}
		}
		else{
			if(iconOn){
				panel.createTransition()
				.push(new SLKeyframe(mainIconCfg, 0.6f)
					.setEndSide(SLSide.RIGHT, p2, p25)
					.setStartSide(SLSide.LEFT, p1Icon, p2Icon)
					.setStartSide(SLSide.RIGHT, p4Icon, p5Icon, p6Icon)
					.setDelay(0.6f, p1Icon, p2Icon, p4Icon, p5Icon, p6Icon)
					.setCallback(new SLKeyframe.Callback() {@Override public void done() {
						p3.setAction(p3Action);
						if (guest){
							pBack.setAction(pBackToLogin);
						}else{
							pBack.setAction(pBackToProjectList);
						}
						enableActions();
					}}))
				.play();
			}
			else{
				panel.createTransition()
				.push(new SLKeyframe(mainCfg, 0.6f)
					.setEndSide(SLSide.RIGHT, p25)
					.setStartSide(SLSide.LEFT, p1)
					.setStartSide(SLSide.RIGHT, p4, p5, p6)
					.setDelay(0.6f, p1, p4, p5, p6)
					.setCallback(new SLKeyframe.Callback() {@Override public void done() {
						p3.setAction(p3Action);
						if (guest){
							pBack.setAction(pBackToLogin);
						}else{
							pBack.setAction(pBackToProjectList);
						}
						enableActions();
					}}))
				.play();
			}
		}
	}};

	private final Runnable p2ChatAction = new Runnable() {@Override public void run() {
		disableActions();
		chatOpen = true;
		
		panel.createTransition()
			.push(new SLKeyframe(todoChatCfg, 0.6f)
				.setDelay(0.6f, p3)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					p3.setAction(p2ChatBackAction);
					p3.enableAction();
				}}))
			.play();
	}};

	private final Runnable p2ChatBackAction = new Runnable() {@Override public void run() {
		disableActions();
		chatOpen = false;

		panel.createTransition()
			.push(new SLKeyframe(todoCfg, 0.6f)
				.setDelay(0.6f, p2)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					p3.setAction(p2ChatAction);
					p3.enableAction();
				}}))
			.play();
	}};

	private final Runnable p3Action = new Runnable() {@Override public void run() {
		//disableActions();
		chatOpen = true;
		
		if(iconOn){
		panel.createTransition()
			.push(new SLKeyframe(chatIconCfg, 0.8f)
				.setEndSide(SLSide.LEFT, p2Icon)
				.setDelay(0.6f, p3)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					p3.setAction(p3BackAction);
					p3.enableAction();
				}}))
			.play();
		}
		else{
			panel.createTransition()
			.push(new SLKeyframe(chatCfg, 0.8f)
				.setEndSide(SLSide.LEFT, p2)
				.setDelay(0.6f, p3)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					p3.setAction(p3BackAction);
					p3.enableAction();
				}}))
			.play();
		}
	}};

	private final Runnable p3BackAction = new Runnable() {@Override public void run() {
		//disableActions();
		chatOpen = false;
		
		if(iconOn){
		panel.createTransition()
			.push(new SLKeyframe(mainIconCfg, 0.8f)
				.setStartSide(SLSide.LEFT, p2Icon)
				.setDelay(0.6f, p2Icon)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					p3.setAction(p3Action);
					enableActions();
				}}))
			.play();
		}
		else{
			panel.createTransition()
			.push(new SLKeyframe(mainCfg, 0.8f)
				.setStartSide(SLSide.LEFT, p2)
				.setDelay(0.6f, p2)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					p3.setAction(p3Action);
					enableActions();
				}}))
			.play();
		}
	}};

	private final Runnable p4Action = new Runnable() {@Override public void run() {
		pollOpen = true;
		disableActions();
		
		if(chatOpen){
			if(iconOn){
				panel.createTransition()
					.push(new SLKeyframe(pollChatCfg, 0.6f)
						.setEndSide(SLSide.BOTTOM, p1Icon, p4Icon, p5Icon, p6Icon)
						.setStartSide(SLSide.RIGHT, p4, p8)
						.setDelay(0.6f, p4, p8)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {
							p3.setAction(p4ChatBackAction);
							pBack.setAction(p4BackAction);
							p3.enableAction();
						}}))
					.play();
				}
				else{
					panel.createTransition()
					.push(new SLKeyframe(pollChatCfg, 0.6f)
						.setEndSide(SLSide.BOTTOM, p1, p5, p6)
						.setStartSide(SLSide.RIGHT, p8)
						.setDelay(0.6f, p8)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {
							p3.setAction(p4ChatBackAction);
							pBack.setAction(p4BackAction);
							p3.enableAction();
						}}))
					.play();
				}
		}
		else{
			if(iconOn){
				panel.createTransition()
					.push(new SLKeyframe(pollCfg, 0.6f)
						.setEndSide(SLSide.BOTTOM, p1Icon, p2Icon, p4Icon, p5Icon, p6Icon)
						.setStartSide(SLSide.RIGHT, p4, p8)
						.setDelay(0.6f, p4, p8)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {
							p3.setAction(p4ChatAction);
							pBack.setAction(p4BackAction);
							p3.enableAction();
						}}))
					.play();
				}
				else{
					panel.createTransition()
					.push(new SLKeyframe(pollCfg, 0.6f)
						.setEndSide(SLSide.BOTTOM, p1, p2, p5, p6)
						.setStartSide(SLSide.RIGHT, p8)
						.setDelay(0.6f, p8)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {
							p3.setAction(p4ChatAction);
							pBack.setAction(p4BackAction);
							p3.enableAction();
						}}))
					.play();
				}
		}
	}};

	private final Runnable p4BackAction = new Runnable() {@Override public void run() {
		pollOpen = false;
		disableActions();

		if(chatOpen){
			if(iconOn){
				panel.createTransition()
					.push(new SLKeyframe(chatIconCfg, 0.6f)
						.setEndSide(SLSide.RIGHT, p4, p8)
						.setStartSide(SLSide.BOTTOM, p1Icon, p4Icon, p5Icon, p6Icon)
						.setDelay(0.6f, p1Icon, p4Icon, p5Icon, p6Icon)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {
							p3.setAction(p3BackAction);
							if (guest){
								pBack.setAction(pBackToLogin);
							}else{
								pBack.setAction(pBackToProjectList);
							}
							enableActions();
						}}))
					.play();
				}
				else{
					panel.createTransition()
						.push(new SLKeyframe(chatCfg, 0.6f)
							.setEndSide(SLSide.RIGHT, p8)
							.setStartSide(SLSide.BOTTOM, p1, p5, p6)
							.setDelay(0.6f, p1, p5, p6)
							.setCallback(new SLKeyframe.Callback() {@Override public void done() {
								p3.setAction(p3BackAction);
								if (guest){
									pBack.setAction(pBackToLogin);
								}else{
									pBack.setAction(pBackToProjectList);
								}
								enableActions();
							}}))
						.play();
				}
		}
		else{
			if(iconOn){
				panel.createTransition()
					.push(new SLKeyframe(mainIconCfg, 0.6f)
						.setEndSide(SLSide.RIGHT, p4, p8)
						.setStartSide(SLSide.BOTTOM, p1Icon, p2Icon, p4Icon, p5Icon, p6Icon)
						.setDelay(0.6f, p1Icon, p2Icon, p4Icon, p5Icon, p6Icon)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {
							p3.setAction(p3Action);
							if (guest){
								pBack.setAction(pBackToLogin);
							}else{
								pBack.setAction(pBackToProjectList);
							}
							enableActions();
						}}))
					.play();
				}
				else{
					panel.createTransition()
						.push(new SLKeyframe(mainCfg, 0.6f)
							.setEndSide(SLSide.RIGHT, p8)
							.setStartSide(SLSide.BOTTOM, p1, p2, p5, p6)
							.setDelay(0.6f, p1, p2, p5, p6)
							.setCallback(new SLKeyframe.Callback() {@Override public void done() {
								p3.setAction(p3Action);
								if (guest){
									pBack.setAction(pBackToLogin);
								}else{
									pBack.setAction(pBackToProjectList);
								}
								enableActions();
							}}))
						.play();
				}
		}
	}};
	
	private final Runnable p4ChatAction = new Runnable() {@Override public void run() {
		disableActions();
		chatOpen = true;
		
		panel.createTransition()
			.push(new SLKeyframe(pollChatCfg, 0.6f)
				.setDelay(0.4f, p4)
				.setDelay(0.8f, p3)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					p3.setAction(p4ChatBackAction);
					p3.enableAction();
				}}))
			.play();
	}};
	
	private final Runnable p4ChatBackAction = new Runnable() {@Override public void run() {
		disableActions();
		chatOpen = false;

		panel.createTransition()
			.push(new SLKeyframe(pollCfg, 0.6f)
				.setDelay(0.6f, p4)
				.setDelay(1.2f, p8)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					p3.setAction(p4ChatAction);
					p3.enableAction();
				}}))
			.play();
	}};

	//Files Action
	private final Runnable p5Action = new Runnable() {@Override public void run() {
		disableActions();
		filesOpen = true;

		if(chatOpen){
			if(iconOn){
				panel.createTransition()
					.push(new SLKeyframe(filesChatCfg, 0.6f)
						.setEndSide(SLSide.LEFT, p1Icon)
						.setEndSide(SLSide.RIGHT, p4Icon, p5Icon, p6Icon)
						.setStartSide(SLSide.TOP, p55, p51, p52)
						.setDelay(0.6f,p55, p51, p52)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {
							//p5.setAction(p5BackAction);
							//p5.enableAction();
							p3.setAction(p5ChatBackAction);
							pBack.setAction(p5BackAction);
							p3.enableAction();
						}}))
					.play();
				}
				else{
					panel.createTransition()
					.push(new SLKeyframe(filesChatCfg, 0.6f)
						.setEndSide(SLSide.LEFT, p1)
						.setEndSide(SLSide.RIGHT, p4, p5, p6)
						.setStartSide(SLSide.TOP, p55, p51, p52)
						.setDelay(0.6f,p55, p51, p52)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {
							//p5.setAction(p5BackAction);
							//p5.enableAction();
							p3.setAction(p5ChatBackAction);
							pBack.setAction(p5BackAction);
							p3.enableAction();
						}}))
					.play();
					
				}
		}
		else{
			if(iconOn){
				panel.createTransition()
					.push(new SLKeyframe(filesCfg, 0.6f)
						.setEndSide(SLSide.LEFT, p1Icon, p2Icon)
						.setEndSide(SLSide.RIGHT, p4Icon, p5Icon, p6Icon)
						.setStartSide(SLSide.TOP, p55, p51, p52)
						.setDelay(0.6f,p55, p51, p52)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {
							//p5.setAction(p5BackAction);
							//p5.enableAction();
							p3.setAction(p5ChatAction);
							pBack.setAction(p5BackAction);
							p3.enableAction();
						}}))
					.play();
				}
				else{
					panel.createTransition()
					.push(new SLKeyframe(filesCfg, 0.6f)
						.setEndSide(SLSide.LEFT, p1, p2)
						.setEndSide(SLSide.RIGHT, p4, p5, p6)
						.setStartSide(SLSide.TOP, p55, p51, p52)
						.setDelay(0.6f,p55, p51, p52)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {
							//p5.setAction(p5BackAction);
							//p5.enableAction();
							p3.setAction(p5ChatAction);
							pBack.setAction(p5BackAction);
							p3.enableAction();
						}}))
					.play();
					
				}
		}
	}};

	//Files Back Action
	private final Runnable p5BackAction = new Runnable() {@Override public void run() {
		disableActions();
		filesOpen = false;
		
		if(chatOpen){
			if(iconOn){
				panel.createTransition()
					.push(new SLKeyframe(chatIconCfg, 0.6f)
						.setEndSide(SLSide.TOP, p55, p51, p52)
						.setStartSide(SLSide.LEFT, p1Icon)
						.setStartSide(SLSide.RIGHT, p4Icon, p5Icon, p6Icon)
						.setDelay(0.6f, p1Icon, p4Icon, p5Icon, p6Icon)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {
							//p5.setAction(p5Action);
							p3.setAction(p3BackAction);
							if (guest){
								pBack.setAction(pBackToLogin);
							}else{
								pBack.setAction(pBackToProjectList);
							}
							enableActions();
						}}))
					.play();
				}
				else{
					panel.createTransition()
					.push(new SLKeyframe(chatCfg, 0.6f)
						.setEndSide(SLSide.TOP, p55, p51, p52)
						.setStartSide(SLSide.LEFT, p1)
						.setStartSide(SLSide.RIGHT, p4, p5, p6)
						.setDelay(0.6f, p1, p4, p5, p6)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {
							//p5.setAction(p5Action);
							p3.setAction(p3BackAction);
							if (guest){
								pBack.setAction(pBackToLogin);
							}else{
								pBack.setAction(pBackToProjectList);
							}
							enableActions();
						}}))
					.play();
					
				}
		}
		else{
			if(iconOn){
				panel.createTransition()
					.push(new SLKeyframe(mainIconCfg, 0.6f)
						.setEndSide(SLSide.TOP, p55, p51, p52)
						.setStartSide(SLSide.LEFT, p1Icon, p2Icon)
						.setStartSide(SLSide.RIGHT, p4Icon, p5Icon, p6Icon)
						.setDelay(0.6f, p1Icon, p2Icon, p4Icon, p5Icon, p6Icon)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {
							//p5.setAction(p5Action);
							p3.setAction(p3Action);
							if (guest){
								pBack.setAction(pBackToLogin);
							}else{
								pBack.setAction(pBackToProjectList);
							}
							enableActions();
						}}))
					.play();
				}
				else{
					panel.createTransition()
					.push(new SLKeyframe(mainCfg, 0.6f)
						.setEndSide(SLSide.TOP, p55, p51, p52)
						.setStartSide(SLSide.LEFT, p1, p2)
						.setStartSide(SLSide.RIGHT, p4, p5, p6)
						.setDelay(0.6f, p1, p2, p4, p5, p6)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {
							//p5.setAction(p5Action);
							p3.setAction(p3Action);
							if (guest){
								pBack.setAction(pBackToLogin);
							}else{
								pBack.setAction(pBackToProjectList);
							}
							enableActions();
						}}))
					.play();
					
				}
		}
	}};

	private final Runnable p5ChatAction = new Runnable() {@Override public void run() {
		disableActions();
		chatOpen = true;
		
		panel.createTransition()
			.push(new SLKeyframe(filesChatCfg, 0.6f)
				.setDelay(0.6f, p3)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					p3.setAction(p5ChatBackAction);
					p3.enableAction();
				}}))
			.play();
	}};
	
	private final Runnable p5ChatBackAction = new Runnable() {@Override public void run() {
		disableActions();
		chatOpen = false;
		
		panel.createTransition()
			.push(new SLKeyframe(filesCfg, 0.6f)
				.setDelay(0.6f,p55)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					p3.setAction(p5ChatAction);
					p3.enableAction();
				}}))
			.play();
	}};
	
	
	//Calendar Action
	private final Runnable p6Action = new Runnable() {@Override public void run() {
		disableActions();
		calendarOpen = true;
		
		if(chatOpen){
			if(iconOn){
				panel.createTransition()
					.push(new SLKeyframe(calendarChatCfg, 0.6f)
						.setEndSide(SLSide.TOP, p1Icon, p4Icon, p5Icon, p6Icon)
						.setStartSide(SLSide.RIGHT, p6, p9, p95)
						.setDelay(0.6f, p6, p9, p95)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {
							p3.setAction(p6ChatBackAction);
							pBack.setAction(p6BackAction);
							p3.enableAction();
						}}))
					.play();
				}
				else{

					panel.createTransition()
						.push(new SLKeyframe(calendarChatCfg, 0.6f)
							.setEndSide(SLSide.TOP, p1, p4, p5)
							.setStartSide(SLSide.RIGHT, p9, p95)
							.setDelay(0.6f, p9, p95)
							.setCallback(new SLKeyframe.Callback() {@Override public void done() {
								p3.setAction(p6ChatBackAction);
								pBack.setAction(p6BackAction);
								p3.enableAction();
							}}))
						.play();
				}
		}
		else{
			if(iconOn){
				panel.createTransition()
					.push(new SLKeyframe(calendarCfg, 0.6f)
						.setEndSide(SLSide.TOP, p1Icon, p2Icon, p4Icon, p5Icon, p6Icon)
						.setStartSide(SLSide.RIGHT, p6, p9, p95)
						.setDelay(0.6f, p6, p9, p95)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {
							p3.setAction(p6ChatAction);
							pBack.setAction(p6BackAction);
							p3.enableAction();
						}}))
					.play();
				}
				else{

					panel.createTransition()
						.push(new SLKeyframe(calendarCfg, 0.6f)
							.setEndSide(SLSide.TOP, p1, p2, p4, p5)
							.setStartSide(SLSide.RIGHT, p9, p95)
							.setDelay(0.6f, p9, p95)
							.setCallback(new SLKeyframe.Callback() {@Override public void done() {
								p3.setAction(p6ChatAction);
								pBack.setAction(p6BackAction);
								p3.enableAction();
							}}))
						.play();
				}
		}
	}};

	
	private final Runnable p6BackAction = new Runnable() {@Override public void run() {
		disableActions();
		calendarOpen = false;

		if(chatOpen){
			if(iconOn){
				panel.createTransition()
					.push(new SLKeyframe(chatIconCfg, 0.6f)
						.setEndSide(SLSide.RIGHT, p6, p9, p95)
						.setStartSide(SLSide.TOP, p1Icon, p4Icon, p5Icon, p6Icon)
						.setDelay(0.6f, p1Icon, p4Icon, p5Icon, p6Icon)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {
							if (guest){
								pBack.setAction(pBackToLogin);
							}else{
								pBack.setAction(pBackToProjectList);
							}
							p3.setAction(p3BackAction);
							enableActions();
						}}))
					.play();
				}
				else{
					panel.createTransition()
					.push(new SLKeyframe(chatCfg, 0.6f)
						.setEndSide(SLSide.RIGHT, p9, p95)
						.setStartSide(SLSide.TOP, p1, p4, p5)
						.setDelay(0.6f, p1, p4, p5)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {

							if (guest){
								pBack.setAction(pBackToLogin);
							}else{
								pBack.setAction(pBackToProjectList);
							}
							p3.setAction(p3BackAction);
							enableActions();
						}}))
					.play();
				}
		}
		else{
			if(iconOn){
				panel.createTransition()
					.push(new SLKeyframe(mainIconCfg, 0.6f)
						.setEndSide(SLSide.RIGHT, p6, p9, p95)
						.setStartSide(SLSide.TOP, p1Icon, p2Icon, p4Icon, p5Icon, p6Icon)
						.setDelay(0.6f, p1Icon, p2Icon, p4Icon, p5Icon, p6Icon)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {

							if (guest){
								pBack.setAction(pBackToLogin);
							}else{
								pBack.setAction(pBackToProjectList);
							}
							p3.setAction(p3Action);
							enableActions();
						}}))
					.play();
				}
				else{
					panel.createTransition()
					.push(new SLKeyframe(mainCfg, 0.6f)
						.setEndSide(SLSide.RIGHT, p9, p95)
						.setStartSide(SLSide.TOP, p1, p2, p4, p5)
						.setDelay(0.6f, p1, p2, p4, p5)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {

							if (guest){
								pBack.setAction(pBackToLogin);
							}else{
								pBack.setAction(pBackToProjectList);
							}
							p3.setAction(p3Action);
							enableActions();
						}}))
					.play();
				}
		}
	}};
	
	private final Runnable p6ChatAction = new Runnable() {@Override public void run() {
		disableActions();
		calendarOpen = true;
		chatOpen = true;
		
		panel.createTransition()
			.push(new SLKeyframe(calendarChatCfg, 0.6f)
				.setDelay(0.6f, p3)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					p3.setAction(p6ChatBackAction);
					p3.enableAction();
				}}))
			.play();
	}};
	
	private final Runnable p6ChatBackAction = new Runnable() {@Override public void run() {
		disableActions();
		calendarOpen = true;
		chatOpen = false;
		
		panel.createTransition()
			.push(new SLKeyframe(calendarCfg, 0.6f)
				.setDelay(0.6f, p9)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					p3.setAction(p6ChatAction);
					p3.enableAction();
				}}))
			.play();
	}};

	
	//Toggle Icons
	private final Runnable pToggleIcon = new Runnable() {@Override public void run() {
		iconOn = false;
		ToggleActions();
		
		if(chatOpen){
		panel.createTransition()
			.push(new SLKeyframe(chatCfg, 0.6f)
				.setEndSide(SLSide.LEFT, p1Icon)
				.setEndSide(SLSide.RIGHT, p4Icon, p5Icon, p6Icon)
				.setStartSide(SLSide.LEFT, p1)
				.setStartSide(SLSide.RIGHT, p4, p5, p6)
				.setDelay(0.6f, p1, p4, p5, p6)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					pToggle.setAction(pToggleBack);
					enableActions();
				}}))
			.play();
		}
		else{
			panel.createTransition()
			.push(new SLKeyframe(mainCfg, 0.6f)
					.setEndSide(SLSide.LEFT, p1Icon, p2Icon)
					.setEndSide(SLSide.RIGHT, p4Icon, p5Icon, p6Icon)
					.setStartSide(SLSide.LEFT, p1, p2)
					.setStartSide(SLSide.RIGHT, p4, p5, p6)
					.setDelay(0.6f, p1, p2, p4, p5, p6)
					.setCallback(new SLKeyframe.Callback() {@Override public void done() {
						pToggle.setAction(pToggleBack);
						enableActions();
					}}))
				.play();
		}
	}};

	
	private final Runnable pToggleBack = new Runnable() {@Override public void run() {
		iconOn = true;
		ToggleActions();
		
		if(chatOpen){
		panel.createTransition()
			.push(new SLKeyframe(chatIconCfg, 0.6f)
				.setEndSide(SLSide.LEFT, p1)
				.setEndSide(SLSide.RIGHT, p4, p5, p6)
				.setStartSide(SLSide.LEFT, p1Icon)
				.setStartSide(SLSide.RIGHT, p4Icon, p5Icon, p6Icon)
				.setDelay(0.6f, p1Icon, p4Icon, p5Icon, p6Icon)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					pToggle.setAction(pToggleIcon);
					enableActions();
				}}))
			.play();
		}
		else{
			panel.createTransition()
			.push(new SLKeyframe(mainIconCfg, 0.6f)
				.setEndSide(SLSide.LEFT, p1, p2)
				.setEndSide(SLSide.RIGHT, p4, p5, p6)
				.setStartSide(SLSide.LEFT, p1Icon, p2Icon)
				.setStartSide(SLSide.RIGHT, p4Icon, p5Icon, p6Icon)
				.setDelay(0.6f, p1Icon, p2Icon, p4Icon, p5Icon, p6Icon)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					pToggle.setAction(pToggleIcon);
					enableActions();
				}}))
			.play();		
		}
	}};	
	
	private final Runnable pBackToProjectList = new Runnable() {@Override public void run() {

		if (iconOn){
			if(chatOpen){
				panel.createTransition()
					.push(new SLKeyframe(pListCfg, 0.6f)
						.setEndSide(SLSide.RIGHT,pToggle, pBack, p1Icon, p3, p4Icon, p5Icon, p6Icon)
						.setStartSide(SLSide.LEFT, p11)
						.setDelay(0.6f, p11)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {
							disableActions();
						}}))
					.play();
				}
				else{
					panel.createTransition()
					.push(new SLKeyframe(pListCfg, 0.6f)
							.setEndSide(SLSide.RIGHT, pToggle, pBack, p1Icon, p2Icon, p3, p4Icon, p5Icon, p6Icon)
							.setStartSide(SLSide.LEFT, p11)
							.setDelay(0.6f, p11)
							.setCallback(new SLKeyframe.Callback() {@Override public void done() {
								disableActions();
							}}))
						.play();
				}
		}
		else{
			if(chatOpen){
				panel.createTransition()
					.push(new SLKeyframe(pListCfg, 0.6f)
						.setEndSide(SLSide.RIGHT,pToggle, pBack, p1, p3, p4, p5, p6)
						.setStartSide(SLSide.LEFT, p11)
						.setDelay(0.6f, p11)
						.setCallback(new SLKeyframe.Callback() {@Override public void done() {
							disableActions();
							ToggleActions();
						}}))
					.play();
				}
				else{
					panel.createTransition()
					.push(new SLKeyframe(pListCfg, 0.6f)
							.setEndSide(SLSide.RIGHT,pToggle, pBack, p1, p2, p3, p4, p5, p6)
							.setStartSide(SLSide.LEFT, p11)
							.setDelay(0.6f, p11)
							.setCallback(new SLKeyframe.Callback() {@Override public void done() {
								disableActions();
								ToggleActions();
							}}))
						.play();
				}
		}
		
	}};	
	
	private final Runnable pBackToLogin = new Runnable() {@Override public void run() {
		setLogin(projectManager.logout());
		
		panel.createTransition()
		.push(new SLKeyframe(loginCfg, 0.6f)
			.setStartSide(SLSide.LEFT, p10)
			.setDelay(0.6f, p10)
			.setCallback(new SLKeyframe.Callback() {@Override public void done() {
				disableActions();
			}}))
		.play();
		
		
	}};	
	
	
	public void setLogin(Login login){
		p10 = new ThePanel(login, false);
		revalidate();
		p10.revalidate();
		p10.setAction(loginAction);
		refresh();
	}
	
	public void setProjectList(ProjectList pList){
		p11 = new ThePanel(pList, false);
		revalidate();

		refresh();
		//p11.setAction(projectAction);
	
	}
	
	public void setAbout(AboutGUI about){
		p1 = new ThePanel(about, true);
		revalidate();

		p1.setAction(p1Action);
	}
	
	//ToDo Functions
	public void setToDo(ToDoGUI todo){
		p2 = new ThePanel(todo, true);
		revalidate();

		p2.setAction(p2Action);
	}
	
	public void setToDoWindow(JPanel p){
		p.setBorder(BorderFactory.createLineBorder(Color.black));
		p25.removeAll();
		p25.add(p, BorderLayout.CENTER);
		p25.revalidate();
		/*if (!toDoOpen){
			p2.doAction();
		}*/
		
	}
	
	public void setChat(ChatGUI chat){
		p3 = new ThePanel(chat, true);
		revalidate();
		p3.setAction(p3Action);
	}
	
	public void setPolls(PollsGUI poll){
		p4 = new ThePanel(poll, true);
		revalidate();

		p4.setAction(p4Action);
	}
	
	//Files Functions
	public void setFile(FilesGUI file){
		p55 = new ThePanel(file, true);
		revalidate();
		
		p55.setAction(p5Action);
	}
	
	public void setFileInfo(JPanel p){
		p.setBorder(BorderFactory.createLineBorder(Color.black));
		p51.removeAll();
		p51.add(p, BorderLayout.CENTER);
		p51.revalidate();
		if (!filesOpen){
			p55.doAction();
		}
	}
	
	public void setCreateFile(JPanel p){
		p.setBorder(BorderFactory.createLineBorder(Color.black));
		p52.removeAll();
		p52.add(p, BorderLayout.CENTER);
		p52.revalidate();
		/*if (!filesOpen){
			p55.doAction();
		}*/
	}
	
	//Calendar Functions
	public void setCalendar(CalendarGUI calendar){
		p9 = new ThePanel(calendar, true);
		
		revalidate();
		//p6.setAction(p6Action);
	}
	
	public void setCalendarGrid(JPanel p){
		p.setBorder(BorderFactory.createLineBorder(Color.black));
		p6.removeAll();
		p6.add(p, BorderLayout.CENTER);
		p6.revalidate();
	}
	
	public void setCalendarWindow(JPanel p){
		p.setBorder(BorderFactory.createLineBorder(Color.black));
		p95.removeAll();
		p95.add(p, BorderLayout.CENTER);
		p95.revalidate();
		if (!calendarOpen){
			p6.doAction();
		}
	}
	
	//Members List Functions
	public void setMembers(MembersListGUI memList){
		p7 = new ThePanel(memList, true);
		revalidate();
	}
	
	public void setPollsWindow(JPanel p){
		p.setBorder(BorderFactory.createLineBorder(Color.black));
		p8.removeAll();
		p8.add(p,BorderLayout.CENTER);
		p8.revalidate();
		if (!pollOpen){
			p4.doAction();
		}
	}
	
	public void setPollsWindow2(JPanel p){
		p.setBorder(BorderFactory.createLineBorder(Color.black));
		p8.removeAll();
		p8.add(p,BorderLayout.CENTER);
		p8.revalidate();
	}
	
	public void login(){
		guest = false;
		p10.setAction(loginAction);
		p10.doAction();
		refresh();
	}
	
	public void guestLogin(){
		guest = true;
		refresh();
		
		ImageIcon img = new ImageIcon("icons/todo.png");
		JLabel tempL = new JLabel(img, JLabel.CENTER);
		p25.removeAll();
		p25.add(tempL, BorderLayout.CENTER);
		p25.setBackground(Color.decode("#FB516F"));
		p25.disableAction();
		p25.revalidate();
		
		img = new ImageIcon("icons/polls.png");
		tempL = new JLabel(img, JLabel.CENTER);
		p8.removeAll();
		p8.add(tempL, BorderLayout.CENTER);	
		p8.setBackground(Color.decode("#FF9B3E"));
		p8.disableAction();
		p8.revalidate();
		
		img = new ImageIcon("icons/about.png");
		tempL = new JLabel(img, JLabel.CENTER);
		p51.removeAll();
		p51.add(tempL, BorderLayout.CENTER);
		p51.setBackground(Color.decode("#59EC99"));
		p51.disableAction();
		p51.revalidate();
		
		img = new ImageIcon("icons/smallFiles.png");
		tempL = new JLabel(img, JLabel.CENTER);
		p52.removeAll();
		p52.add(tempL, BorderLayout.CENTER);
		p52.setBackground(Color.decode("#59EC99"));
		p52.disableAction();
		p52.revalidate();
		
		img = new ImageIcon("icons/calendar.png");
		tempL = new JLabel(img, JLabel.CENTER);
		p95.removeAll();
		p95.add(tempL, BorderLayout.CENTER);
		p95.setBackground(Color.decode("#45B3D7"));
		p95.disableAction();
		p95.revalidate();
		
		p10.setAction(guestAction);
		p10.doAction();
	}
	
	public void openProject(){
		refresh();
		p11.doAction();
		chatOpen = false;
	}
	
	public void signUp(JPanel p){
		p.setBorder(BorderFactory.createLineBorder(Color.black));
		p12.removeAll();
		p12.add(p, BorderLayout.CENTER);
		p12.revalidate();
		p10.setAction(signUpAction);
		p10.doAction();
	}
	
	public void cancelSignUp(){
		p12.setAction(signUpBackAction);
		p12.doAction();
		p10.setAction(loginAction);
	}
	
	public void signUpSuccess(){
		p12.setAction(signUpSuccessAction);
		p12.doAction();
	}
	
	private void refresh(){
		
		p1.setAction(p1Action);
		p2.setAction(p2Action);
		p3.setAction(p3Action);
		p4.setAction(p4Action);
		p5.setAction(p5Action);
		p6.setAction(p6Action);
		
		p1Icon.setAction(p1Action);
		p2Icon.setAction(p2Action);
		//p3Icon.setAction(p3Action);
		p4Icon.setAction(p4Action);
		p5Icon.setAction(p5Action);
		p6Icon.setAction(p6Action);
		
		p10.setAction(loginAction);
		p11.setAction(projectAction);
		p55.setAction(p5BackAction);
		pToggle.setAction(pToggleIcon);
		
		//project Icon Config
		mainIconCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(1f).col(1f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pToggle)
					.place(0, 3, pBack)
				.endGrid()
				.beginGrid(1,0 )
					.row(1f).col(1f).col(1f)
					.beginGrid(0, 0)
						.row(1f).row(3f).row(1f).col(1f)
						.place(0, 0, p1Icon)
						.place(1, 0, p2Icon)
						.place(2, 0, p3)
					.endGrid()
					.beginGrid(0,1)
						.row(2f).row(1f).row(4f).col(1f)
						.place(0,0,p4Icon)
						.place(1,0,p5Icon)
						.place(2,0,p6Icon)
					.endGrid()
				.endGrid();

		mainCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(1f).col(1f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pToggle)
					.place(0, 3, pBack)
				.endGrid()
				.beginGrid(1,0 )
					.row(1f).col(1f).col(1f)
					.beginGrid(0, 0)
						.row(1f).row(3f).row(1f).col(1f)
						.place(0, 0, p1)
						.place(1, 0, p2)
						.place(2, 0, p3)
					.endGrid()
					.beginGrid(0,1)
						.row(2f).row(1f).row(4f).col(1f)
						.place(0,0,p4)
						.place(1,0,p5)
						.place(2,0,p6)
					.endGrid()
				.endGrid();
	
		/*//project Config
		mainCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).col(1f).col(1f)
				.beginGrid(0, 0)
					.row(1f).row(3f).row(1f).col(1f)
					.place(0, 0, p1)
					.place(1, 0, p2)
					.place(2, 0, p3)
				.endGrid()
				.beginGrid(0,1)
					.row(2f).row(1f).row(4f).col(1f)
					.place(0,0,p4)
					.place(1,0,p5)
					.place(2,0,p6)
				.endGrid();*/
			
		//Login
		loginCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).col(1f)
				.place(0, 0, p10);

		//Project List
		pListCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(11f).col(1f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
				.endGrid()
				.beginGrid(1, 0)
					.gap(10, 10)
					.row(1f).col(1f)
					.place(0, 0, p11)
				.endGrid();
	
		//About Config
		aboutCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(2f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pBack)
				.endGrid()
				.beginGrid(1, 0)
					.row(1f).row(1f).col(1f)
					.place(0, 0, p1)
					.beginGrid(1, 0)
						.row(1f).col(1f).col(1f)
						.place(0, 0, p3)
						.place(0, 1, p7)
					.endGrid()
				.endGrid();
		
		//About Chat Config
		aboutChatCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(2f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pBack)
				.endGrid()
				.beginGrid(1, 0)
					.row(1f).col(1f).col(1f)
					.place(0, 0, p3)
					.beginGrid(0, 1)
						.row(1f).row(1f).col(1f)
						.place(0, 0, p1)
						.place(1, 0, p7)
					.endGrid()
				.endGrid();

		//To-Do Config
		todoCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(2f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pBack)
				.endGrid()
				.beginGrid(1, 0)
					.row(4f).row(1f).col(1f)
					.place(0, 0, p2)
					.beginGrid(1, 0)
						.row(1f).col(1f).col(1f)
						.place(0, 0, p3)
						.place(0, 1, p25)
					.endGrid()
				.endGrid();

		//To-Do Chat Config
		todoChatCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(2f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pBack)
				.endGrid()
				.beginGrid(1, 0)
					.row(1f).col(1f).col(1f)
					.place(0, 0, p3)
					.beginGrid(0, 1)
						.row(4f).row(1f).col(1f)
						.place(0, 0, p2)
						.place(1, 0, p25)
					.endGrid()
				.endGrid();
		
		//Chat Config
		chatCfg = new SLConfig(panel)
				.gap(10, 10)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(1f).col(1f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pToggle)
					.place(0, 3, pBack)
				.endGrid()
				.beginGrid(1, 0)
					.row(1f).col(1f).col(1f)
					.beginGrid(0, 0)
						.row(1f).row(4f).col(1f)
						.place(0, 0, p1)
						.place(1, 0, p3)
					.endGrid()
					.beginGrid(0,1)
						.row(2f).row(1f).row(4f).col(1f)
						.place(0,0,p4)
						.place(1,0,p5)
						.place(2,0,p6)
					.endGrid()
				.endGrid();
		
		//Chat Icon Config
		chatIconCfg = new SLConfig(panel)
				.gap(10, 10)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(1f).col(1f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pToggle)
					.place(0, 3, pBack)
				.endGrid()
				.beginGrid(1, 0)
					.row(1f).col(1f).col(1f)
					.beginGrid(0, 0)
						.row(1f).row(4f).col(1f)
						.place(0, 0, p1Icon)
						.place(1, 0, p3)
					.endGrid()
					.beginGrid(0,1)
						.row(2f).row(1f).row(4f).col(1f)
						.place(0,0,p4Icon)
						.place(1,0,p5Icon)
						.place(2,0,p6Icon)
					.endGrid()
				.endGrid();
		
		
		//Polls Config
		pollCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(2f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pBack)
				.endGrid()
				.beginGrid(1, 0)
					.row(1f).col(1f).col(1f)
					.beginGrid(0, 0)
						.row(4f).row(1f).col(1f)
						.place(0, 0, p4)
						.place(1, 0, p3)
					.endGrid()
					.place(0, 1, p8)
				.endGrid();
		
		//Polls Chat Config
		pollChatCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(2f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pBack)
				.endGrid()
				.beginGrid(1, 0)
					.row(1f).col(1f).col(1f)
					.place(0, 0, p3)
					.beginGrid(0, 1)
						.row(1f).row(1f).col(1f)
						.place(0, 0, p4)
						.place(1, 0, p8)
					.endGrid()
				.endGrid();
		
		//Files Config 
		filesCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(2f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pBack)
				.endGrid()
				.beginGrid(1, 0)				
					.row(1f).col(1f).col(1f)
					.beginGrid(0, 0)
						.row(4f).row(1f).col(1f)
						.place(0, 0, p55)
						.place(1, 0, p3)
					.endGrid()
						.beginGrid(0,1)
							.row(1f).row(1f).col(1f)
							.place(0, 0, p51)
							.place(1, 0, p52)
						.endGrid()
				.endGrid();
		
		//Files Config 
		filesChatCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(2f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pBack)
				.endGrid()
				.beginGrid(1, 0)				
					.row(1f).col(1f).col(1f)
					.beginGrid(0, 0)
						.row(1f).row(3f).col(1f)
						.place(0, 0, p55)
						.place(1, 0, p3)
					.endGrid()
						.beginGrid(0,1)
							.row(1f).row(1f).col(1f)
							.place(0, 0, p51)
							.place(1, 0, p52)
						.endGrid()
				.endGrid();
								
		//Calendar Config
		calendarCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(2f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pBack)
				.endGrid()
				.beginGrid(1, 0)
					.row(1f).col(1f).col(1f)
					.beginGrid(0, 0)
						.row(4f).row(1f).col(1f)
						.place(0, 0, p9)
						.place(1, 0, p3)
					.endGrid()
					.beginGrid(0,1)
						.row(5f).row(3f).col(1f)
						.place(0, 0, p6)
						.place(1, 0, p95)		
					.endGrid()
				.endGrid();
		
		//Calendar Chat Config
		calendarChatCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(10f).col(1f)
				.beginGrid(0, 0)
					.row(1f).col(9f).col(1f).col(2f)
					.place(0, 0, pTitle)
					.place(0, 1, pHome)
					.place(0, 2, pBack)
				.endGrid()
				.beginGrid(1, 0)
					.row(1f).col(1f).col(1f)
					.beginGrid(0, 0)
						.row(1f).row(3f).col(1f)
						.place(0, 0, p9)
						.place(1, 0, p3)
					.endGrid()
					.beginGrid(0,1)
						.row(1f).row(1f).col(1f)
						.place(0, 0, p6)
						.place(1, 0, p95)		
					.endGrid()
				.endGrid();
		/*
		//About Config
		aboutCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).row(1f).col(1f)
				.place(0, 0, p1)
				.place(1, 0, p7);
		
		
		//To-Do Config
		todoCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(4f).row(1f).col(1f)
				.place(0, 0, p2)
				.place(1, 0, p25);
		
		//Chat Config
		chatCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).col(1f).col(1f)
				.beginGrid(0, 0)
					.row(1f).row(4f).col(1f)
					.place(0, 0, p1)
					.place(1, 0, p3)
				.endGrid()
				.beginGrid(0,1)
					.row(2f).row(1f).row(4f).col(1f)
					.place(0,0,p4)
					.place(1,0,p5)
					.place(2,0,p6)
				.endGrid();
		
		//Polls Config
		pollCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).col(1f).col(1f)
				.place(0, 0, p4)
				.place(0, 1, p8);
		
		//Files Config
		filesCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).col(1f).col(1f)
				.place(0, 0, p55)
					.beginGrid(0,1)
					.row(1f).row(1f).col(1f)
					.place(0, 0, p51)
					.place(1, 0, p52);
		
		//Calendar Config
		calendarCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).col(1f).col(1f)
				.place(0, 0, p9)
					.beginGrid(0,1)
					.row(1f).row(1f).col(1f)
					.place(0, 0, p6)
					.place(1, 0, p95);
		*/
		//signUp Config
		signUpCfg = new SLConfig(panel)
				.gap(10, 10)
				.row(1f).col(1f)
				.place(0, 0, p12);
		
		iconOn = true;
		panel.setTweenManager(SLAnimator.createTweenManager());
	}	
}
