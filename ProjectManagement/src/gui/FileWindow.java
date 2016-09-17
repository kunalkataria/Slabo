package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.ProjectFiles;
import objects.ProjectFile;

public class FileWindow extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private ProjectFile projectfile;
	private ProjectFiles project;
	
	private JPanel detailsP, buttonP;
	private JLabel createdBy, details;
	private JButton download;
	private Color baseColor = Color.decode("#59EC99");
	private Color lightBase = Color.decode("#83F4B4");
	private Color lightSelect = Color.decode("#BAFBD6");
	
	public FileWindow(ProjectFile pf, ProjectFiles p) {
		projectfile = pf;
		project = p;
		setLayout(new BorderLayout());
		initiateVariables();
		addActions();
		createGUI();
		setVisible(true);
	}
	
	private void initiateVariables(){
		setBackground(baseColor);
		createdBy = new JLabel("Created By: " + projectfile.getOwner() );
		createdBy.setFont(createdBy.getFont().deriveFont(Font.PLAIN, 24));
		createdBy.setForeground(Color.white);
		details = new JLabel ("Description: \r\n" + projectfile.getDescription());
		details.setFont(createdBy.getFont().deriveFont(Font.PLAIN, 24));
		details.setForeground(Color.white);
		
		download = new JButton("Download");
		download.setBackground(lightBase);
		download.setBorder(BorderFactory.createLineBorder(Color.white, 1));
		download.setForeground(Color.white);
		download.setPreferredSize(new Dimension(100,50));
		download.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				download.setBackground(lightSelect);
				repaint();
			}
			public void mouseExited(MouseEvent e){
				download.setBackground(lightBase);
				repaint();
			}
		});
		
		detailsP = new JPanel();
		detailsP.setLayout(new BoxLayout(detailsP, BoxLayout.Y_AXIS));
		detailsP.setBackground(baseColor);
		buttonP = new JPanel();
		buttonP.setBackground(baseColor);
	}
	
	private void createGUI(){
		detailsP.add(createdBy);
		detailsP.add(details);
		detailsP.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		buttonP.add(download);
		buttonP.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		add(detailsP, BorderLayout.CENTER);
		add(buttonP, BorderLayout.SOUTH);
	}
	
	private void addActions(){
			download.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//request from ProjectFiles
				project.requestDownload(projectfile);
			}
			
		});
	}
	
}
