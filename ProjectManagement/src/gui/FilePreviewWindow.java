package gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import objects.ProjectFile;

public class FilePreviewWindow extends JPanel{
	
	ProjectFile pf;
	private Color baseColor = Color.decode("#59EC99");
	private Color lightBase = Color.decode("#83F4B4");
	
	public FilePreviewWindow(ProjectFile pf){
		setLayout(new BorderLayout());
		
		this.pf = pf;
		setBackground(baseColor);
		
		Box topBox = Box.createVerticalBox();
		JLabel nameLabel = new JLabel(pf.getFileName());
		nameLabel.setForeground(Color.white);
		JLabel ownerLabel = new JLabel("Owner: " + pf.getOwner());
		ownerLabel.setForeground(Color.white);
		topBox.add(nameLabel);
		topBox.add(ownerLabel);
		topBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(topBox, BorderLayout.NORTH);
		
		Box contentsBox = Box.createVerticalBox();
		contentsBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JLabel contentsLabel = new JLabel("File Contents:");
		contentsLabel.setForeground(Color.white);
		contentsBox.add(contentsLabel);
		
		JTextArea contents = new JTextArea(pf.getContents());
		contents.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		contents.setForeground(Color.white);
		contents.setBackground(lightBase);
		JScrollPane cJSP = new JScrollPane(contents);
		cJSP.setBorder(BorderFactory.createEmptyBorder());
		cJSP.setViewportBorder(BorderFactory.createEmptyBorder());
		contents.setLineWrap(true);
		contentsBox.add(cJSP);
		contents.setEditable(false);
		
		Box descBox = Box.createVerticalBox();
		descBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JLabel descLabel = new JLabel("Description: ");
		descLabel.setForeground(Color.white);
		descBox.add(descLabel);
		
		JTextArea desc = new JTextArea(pf.getDescription());
		desc.setForeground(Color.white);
		desc.setBackground(lightBase);
		desc.setBorder(BorderFactory.createEmptyBorder());
		JScrollPane dJSP = new JScrollPane(desc);
		dJSP.setBorder(BorderFactory.createEmptyBorder());
		desc.setLineWrap(true);
		descBox.add(dJSP);
		desc.setEditable(false);
		
		add(contentsBox, BorderLayout.CENTER);
		add(descBox, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
}
