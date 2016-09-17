package gui;
import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AboutEdit extends JDialog {
	private JPanel topPanel;
	private JPanel bottomPanel;
	
	private JLabel projectLabel;
	private JLabel dueLabel;
	private JTextArea description;
	private JButton changeButton, cancelButton;
	private JScrollPane scroll;
	private JPanel background;
	private JPanel centerPanel;
	private JTextField projectName, dueDate;
	private JLabel descriptionLabel;
	public AboutEdit(){
		instantiateComponents();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setSize(300,300);
	}

	private void instantiateComponents(){
		topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		JPanel projectPanel = new JPanel();
		JPanel idPanel = new JPanel();
		JPanel duePanel = new JPanel();
		projectPanel.setLayout(new BorderLayout());
		idPanel.setLayout(new BorderLayout());
		duePanel.setLayout(new BorderLayout());
		projectLabel = new JLabel("Project Name:");
		dueLabel = new JLabel("Due at:");
		projectName = new JTextField(20);
		dueDate = new JTextField(20);
		projectPanel.add(projectLabel, BorderLayout.WEST);
		projectPanel.add(projectName, BorderLayout.CENTER);
		duePanel.add(dueLabel, BorderLayout.WEST);
		duePanel.add(dueDate, BorderLayout.CENTER);
		topPanel.add(projectPanel);
		topPanel.add(duePanel);
		descriptionLabel = new JLabel("Description");
		JPanel descriptPane = new JPanel();
		descriptPane.setLayout(new BorderLayout());
		descriptPane.add(descriptionLabel, BorderLayout.WEST);
		topPanel.add(descriptPane);
		
		description = new JTextArea();
		scroll = new JScrollPane();
		scroll.setViewportView(description);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		cancelButton = new JButton("Cancel");
		changeButton = new JButton("Change");
		centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		//centerPanel.add(descriptPane);
		centerPanel.add(scroll);
		
		
		bottomPanel = new JPanel();
		bottomPanel.add(changeButton);
		bottomPanel.add(cancelButton);
		
		background = new JPanel();
		background.setLayout(new BorderLayout());
		background.add(topPanel, BorderLayout.NORTH);
		background.add(centerPanel, BorderLayout.CENTER);
		background.add(bottomPanel, BorderLayout.SOUTH);
		add(background);
	}
	
	public static void main(String[] args){
		AboutEdit a = new AboutEdit();
	}
}
