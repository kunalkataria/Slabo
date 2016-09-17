package gui;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class InfoTaskDialog extends JDialog{

	private JPanel background;
	private JLabel detailsLabel;
	private JPanel detailsPane;
	private JTextArea description;
	private JScrollPane jsp;
	public InfoTaskDialog(){
		setTitle("Task name");
		initializeVariables();
		setSize(300,300);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void initializeVariables(){
		background = new JPanel();
		background.setLayout(new BorderLayout());
		
		detailsLabel = new JLabel("Details");
		background.add(detailsLabel,BorderLayout.NORTH);
		detailsPane = new JPanel();
		detailsPane.setLayout(new GridLayout(1,1));
		description = new JTextArea();
		jsp = new JScrollPane();
		jsp.setViewportView(description);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		background.add(jsp, BorderLayout.CENTER);
		
		add(background);
	}
	
}
