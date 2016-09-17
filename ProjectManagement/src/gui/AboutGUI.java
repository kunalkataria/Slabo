package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import client.Project;
import objects.ProjectObject;

public class AboutGUI extends JPanel{

	private JPanel topPanel;
	
	private JLabel projectLabel;
	private JLabel idLabel;
	private JLabel dueLabel;
	private JTextArea description;
	private JScrollPane scroll;
	private JPanel background;
	private JPanel centerPanel;
	private Color baseColor = Color.decode("#AA7CDF");
	private Color lightBase = Color.decode("#C9A8F0");
	
	private ProjectObject po;
	
	public AboutGUI(ProjectObject po){
		super();
		this.po = po;
		instantiateComponents();
		setLayout(new GridLayout(1,1));
		
	}
	
	private void instantiateComponents(){
		setBackground(baseColor);
		topPanel = new JPanel();
		topPanel.setBackground(baseColor);
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		projectLabel = new JLabel(po.getProjectName());
		projectLabel.setFont(projectLabel.getFont().deriveFont(Font.BOLD, 40));
		projectLabel.setForeground(Color.white);
		idLabel = new JLabel("Project ID: " + po.getProjectID());
		idLabel.setFont(projectLabel.getFont().deriveFont(Font.PLAIN, 24));
		idLabel.setForeground(Color.white);
		dueLabel = new JLabel("Due at: " + po.getProjectDueDate());
		dueLabel.setFont(projectLabel.getFont().deriveFont(Font.PLAIN, 24));
		dueLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		dueLabel.setForeground(Color.white);
		topPanel.add(projectLabel);
		topPanel.add(idLabel);
		topPanel.add(dueLabel);
		
		description = new JTextArea();
		description.setBackground(lightBase);
		description.setForeground(Color.white);
		description.setEditable(false);
		description.setText(po.getProjectDescription());
		description.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		scroll = new JScrollPane();
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.setViewportBorder(BorderFactory.createEmptyBorder());
		scroll.setViewportView(description);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(scroll);
		
		
		background = new JPanel();
		background.setBackground(baseColor);
		background.setLayout(new BorderLayout());
		background.add(topPanel, BorderLayout.NORTH);
		background.add(centerPanel, BorderLayout.CENTER);
		add(background);
		
		setBorder(BorderFactory.createEmptyBorder(Project.BORDER, Project.BORDER, Project.BORDER, Project.BORDER));
	}
}
