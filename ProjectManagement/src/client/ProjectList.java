package client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import gui.AddProjectPopUp;
import gui.CreateProjectPopUp;
import server.Command;
import server.Command.CommandType;

public class ProjectList extends JPanel{

	private static final long serialVersionUID = -5920571359315322303L;

	//private JPanel background; //pane for the entire layout
	//hello 123
	
	private JPanel topButtons; //pane that goes in the north of background
	private JButton addProjectB; //add project button by ID
	
	private JPanel centerContainer; //pane in center of background
	private JPanel centerHolder;
	private JScrollPane scroll;
	
	private JPanel bottomButtons; //pane that goes in the south of background
	private JButton createProjectB; //create project button
	
	private Set<Integer> projectIDset;
	private HashMap<Integer, String> map;
	
	private ProjectManager pm;

	private Color slaboBGColor = Color.decode("#5970FF");
	private Color currentSlaboColor = Color.decode("#3594E3");//Color.decode("#8293FE");  
	private Color endingColor = Color.decode("#ADD8FC");//Color.decode("#E9F5FF");
	
	class ProjectPanel extends JPanel{
		
		private static final long serialVersionUID = 1711302893557850856L;
		
		private int projectID;
		private String projectName;
		private JPanel left, right;
		private openButton openB;
		public ProjectPanel(int projectID, String projectName, Color color){
			super();
			this.projectID = projectID;
			this.projectName = projectName;
			String titleName = projectID+"";
			TitledBorder title;
			title = BorderFactory.createTitledBorder(titleName);
			title.setTitleColor(Color.WHITE);
			setBorder(title);
			JLabel proName = new JLabel("         Project Name: " + projectName);
			proName.setFont(proName.getFont().deriveFont(Font.BOLD, 24));
			proName.setForeground(Color.white);
			left = new JPanel();
			left.setBackground(color);
			left.setLayout(new BorderLayout());
			left.add(proName, BorderLayout.CENTER);
			right = new JPanel();
			right.setBackground(color);
			//openB = new JButton("Open");
			openB = new openButton(color);
			right.add(openB);
			openB.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Command sendCommand = new Command();
					sendCommand.commandType = CommandType.ProjectObject;
					sendCommand.setProjectID(projectID);
					pm.sendCommand(sendCommand);
				}
			});
			
			setLayout(new BorderLayout());
			add(left, BorderLayout.CENTER);
			add(right, BorderLayout.EAST);
		}
	}
	
	class openButton extends JButton{
		
		BufferedImage icon1, icon2;
		ImageIcon buttonIcon;
		
		public openButton(Color c){
			super();
			try {
				icon1 = ImageIO.read(new File("icons/sit.png"));
				icon2 = ImageIO.read(new File("icons/kick.png"));
				setIconImage(icon1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			setBackground(c);
			setBorder(BorderFactory.createEmptyBorder());
			setContentAreaFilled(false);
			setOpaque(true);
			setPreferredSize(new Dimension(buttonIcon.getIconWidth(), buttonIcon.getIconHeight()));
			
			addMouseListener(new MouseAdapter(){
				@Override
				public void mouseEntered(MouseEvent e) {
					setIconImage(icon2);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					setIconImage(icon1);
				}
				
			});
			
		}
		
		private void setIconImage(BufferedImage imageIcon){
			buttonIcon = new ImageIcon(imageIcon);
			this.setIcon(buttonIcon);
			repaint();
			revalidate();
			
		}
	}
	
	public ProjectList(HashMap<Integer, String> pMap, ProjectManager pm){
		super();
		this.pm = pm;
		this.map = pMap;
		projectIDset = new HashSet<Integer>();
		for (Map.Entry<Integer, String> projectEntry : pMap.entrySet()) {
			projectIDset.add(projectEntry.getKey());
		}
		
		instantiateComponents();
		addActions();
	}
	private void instantiateComponents() {
		setLayout(new BorderLayout());
		setBackground(Color.BLACK);
		
		topButtons = new JPanel();
		topButtons.setBackground(Color.BLACK);
		
		addProjectB = new JButton("Add Project by ID");
		Dimension buttonD = addProjectB.getPreferredSize();
		addProjectB.setBorder(BorderFactory.createLineBorder(Color.black));
		addProjectB.setMinimumSize(buttonD);
		addProjectB.setPreferredSize(buttonD);
		addProjectB.setBackground(endingColor);
		addProjectB.setForeground(Color.WHITE);;
		topButtons.add(addProjectB);
		centerHolder = new JPanel();
		centerHolder.setLayout(new BorderLayout());
		centerContainer = new JPanel();
		centerContainer.setBackground(Color.BLACK);
		centerContainer.setLayout(new BoxLayout(centerContainer, BoxLayout.Y_AXIS));
		Border blackline = BorderFactory.createLineBorder(Color.black);
		centerContainer.setBorder(blackline);
		Iterator<Entry<Integer, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, String> pair = (Map.Entry<Integer, String>)it.next();
			changeColor();
			ProjectPanel nextProject = new ProjectPanel((int) pair.getKey(), (String) pair.getValue(), currentSlaboColor);
			nextProject.setBackground(currentSlaboColor);
			centerContainer.add(nextProject);
		}
		
		centerContainer.revalidate();
		centerContainer.repaint();
		centerHolder.add(centerContainer, BorderLayout.NORTH);
		centerHolder.setBackground(Color.BLACK);
		scroll = new JScrollPane();
		scroll.setViewportView(centerHolder);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBackground(Color.BLACK);
		scroll.setBorder(BorderFactory.createLineBorder(Color.black));
		
		bottomButtons = new JPanel();
		bottomButtons.setBackground(Color.BLACK);
		createProjectB = new JButton("Create New Project");
		buttonD = createProjectB.getPreferredSize();
		createProjectB.setBorder(BorderFactory.createLineBorder(Color.black));
		createProjectB.setMinimumSize(buttonD);
		createProjectB.setPreferredSize(buttonD);
		createProjectB.setBackground(endingColor);
		createProjectB.setForeground(Color.WHITE);;
		bottomButtons.add(createProjectB);
		
		add(topButtons, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		add(bottomButtons, BorderLayout.SOUTH);
		
	}
	
	public void addActions(){
		createProjectB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				CreateProjectPopUp pgui = new CreateProjectPopUp(pm);
				pgui.setVisible(true);
			}
		});
		addProjectB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				AddProjectPopUp pgui = new AddProjectPopUp(pm, projectIDset);
				pgui.setVisible(true);
			}
		});
		
		
	}
	
	public void addProject(Integer PID, String pName){
		if(!projectIDset.contains(PID)){
			projectIDset.add(PID);
			map.put(PID, pName);
			//ProjectPanel toAdd = new ProjectPanel(PID, pName);
			//centerContainer.add(toAdd);
			revalidate();
		}
	}
	
	private void changeColor(){
		double p = 0.8;
		int R1 = currentSlaboColor.getRed();
		int R2 = endingColor.getRed();
		int R = (int)Math.round(R1 * p + R2 * (1-p));

		int B1 = currentSlaboColor.getBlue();
		int B2 = endingColor.getBlue();
		int B = (int)Math.round(B1 * p + B2 * (1-p));
		
		int G1 = currentSlaboColor.getGreen();
		int G2 = endingColor.getGreen();
		int G = (int)Math.round(G1 * p + G2 * (1-p));
		
		currentSlaboColor = new Color(R, G, B);
	}
	
}
