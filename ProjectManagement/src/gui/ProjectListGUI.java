package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class ProjectListGUI extends JPanel{
	
	private JPanel topButtons; //pane that goes in the north of background
	private JButton addProjectB; //add project button by ID
	
	private JPanel centerContainer; //pane in center of background
	
	private JPanel bottomButtons; //pane that goes in the south of background
	private JButton createProjectB; //create project button 
	
	class ProjectPanel extends JPanel{
		private JPanel left, right;
		private JButton openB;
		public ProjectPanel(int projectID, String projectName){
			super();
			String titleName = projectID+"";
			TitledBorder title;
			title = BorderFactory.createTitledBorder(titleName);
			setBorder(title);
			JLabel proName = new JLabel("Project Name: " + projectName);
			left = new JPanel();
			left.add(proName);
			right = new JPanel();
			openB = new JButton("Open");
			right.add(openB);
			
			setLayout(new BorderLayout());
			add(left, BorderLayout.CENTER);
			add(right, BorderLayout.EAST);
		}
	}
	public ProjectListGUI(){
		super();
		instantiateComponents();
	}
	private void instantiateComponents() {
		setLayout(new BorderLayout());
		
		topButtons = new JPanel();
		addProjectB = new JButton("Add Project by ID");
		Dimension buttonD = addProjectB.getPreferredSize();
		addProjectB.setBorder(BorderFactory.createLineBorder(Color.black));
		addProjectB.setMinimumSize(buttonD);
		addProjectB.setPreferredSize(buttonD);
		addProjectB.setBackground(Color.GRAY);
		addProjectB.setForeground(Color.WHITE);;
		topButtons.add(addProjectB);
		
		JPanel allCenter = new JPanel();
		centerContainer = new JPanel();
		centerContainer.setLayout(new BoxLayout(centerContainer, BoxLayout.Y_AXIS));
		Border blackline = BorderFactory.createLineBorder(Color.black);
		centerContainer.setBorder(blackline);
		for(int i = 0; i < 20; i++){
			ProjectPanel test = new ProjectPanel(i, "Name" + i);
			centerContainer.add(test);
			centerContainer.revalidate();
		}
		bottomButtons = new JPanel();
		createProjectB = new JButton("Create New Project");
		buttonD = createProjectB.getPreferredSize();
		createProjectB.setBorder(BorderFactory.createLineBorder(Color.black));
		createProjectB.setMinimumSize(buttonD);
		createProjectB.setPreferredSize(buttonD);
		createProjectB.setBackground(Color.GRAY);
		createProjectB.setForeground(Color.WHITE);;
		bottomButtons.add(createProjectB);
		
		allCenter.setLayout(new BorderLayout());
		allCenter.add(centerContainer, BorderLayout.NORTH);
		add(topButtons, BorderLayout.NORTH);
		
		JScrollPane s = new JScrollPane();
		s.setViewportView(allCenter);
		//s.setViewportView(all);
		s.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		s.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(s, BorderLayout.CENTER);
		add(bottomButtons, BorderLayout.SOUTH);
		
		
	}
	
}
