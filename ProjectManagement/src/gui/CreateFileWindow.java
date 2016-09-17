package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import client.ProjectFiles;
import objects.ProjectFile;

public class CreateFileWindow extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private ProjectFiles projectfiles;
	private CreateFileWindow cfw;
	
	private JPanel inputP, buttonP;
	private JLabel createdBy, fileName, details;
	private JTextField owner;
	private JTextArea description;
	private JButton create, selectFile;
	private File file;
	private Color baseColor = Color.decode("#59EC99");
	private Color lightBase = Color.decode("#83F4B4");
	private Color lightSelect = Color.decode("#BAFBD6");
	
	public CreateFileWindow(ProjectFiles pf) {
		projectfiles = pf;
		setLayout(new BorderLayout());
		initiateVariables();
		addActions();
		createGUI();
		setVisible(true);
	}
	
	private void initiateVariables(){
		setBackground(baseColor);
		createdBy = new JLabel("Owner: ");
		createdBy.setForeground(Color.white);
		fileName = new JLabel("Choose File: ");
		fileName.setForeground(Color.white);
		details = new JLabel("Description: ");
		details.setForeground(Color.white);
		selectFile = new JButton("Select File...");
		selectFile.setForeground(Color.white);
		selectFile.setBackground(lightBase);
		selectFile.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				selectFile.setBackground(lightSelect);
				selectFile.repaint();
			}
			public void mouseExited(MouseEvent e){
				selectFile.setBackground(lightBase);
				selectFile.repaint();
			}
		});
		selectFile.setPreferredSize(new Dimension(100, 30));
		owner = new JTextField();
		owner.setBackground(lightBase);
		owner.setBorder(BorderFactory.createEmptyBorder());
		
		owner.setText("  " + projectfiles.getProject().getUser().getUsername());
		owner.setForeground(Color.white);
		owner.setEditable(false);
		description = new JTextArea();
		description.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		description.setBackground(lightBase);
		description.setForeground(Color.white);
		
		create = new JButton("Create");
		create.setBackground(lightBase);
		create.setBorder(BorderFactory.createLineBorder(Color.white, 1));
		create.setForeground(Color.white);
		create.setPreferredSize(new Dimension(200, 50));
		create.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				create.setBackground(lightSelect);
				create.repaint();
			}
			public void mouseExited(MouseEvent e){
				create.setBackground(lightBase);
				create.repaint();
			}
		});
		
		inputP = new JPanel(new GridBagLayout());
		inputP.setBackground(baseColor);
		inputP.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		buttonP = new JPanel();
		buttonP.setBackground(baseColor);
	}
	
	private void createGUI(){
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(3,3,3,3);
		inputP.add(createdBy, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.ipadx = 120;
		inputP.add(owner, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.ipadx = 0;
		inputP.add(fileName, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.ipadx = 120;
		inputP.add(selectFile, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.ipadx = 0;
		inputP.add(details, gbc);
		
		buttonP.add(create);
		
		add(inputP, BorderLayout.NORTH);
		add(description, BorderLayout.CENTER);
		add(buttonP, BorderLayout.SOUTH);
	}
	
	private void addActions(){

		create.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(owner.getText().equals("")){
					JOptionPane.showMessageDialog(cfw, "Must input an owner."
							, "Create File Failed", JOptionPane.ERROR_MESSAGE);
				}else if (description.getText().equals("")){
					JOptionPane.showMessageDialog(cfw, "Must include a description."
							, "Create File Failed", JOptionPane.ERROR_MESSAGE);
				}//else if doesnt include a file ... display error message
				else{
					ProjectFile newFile = new ProjectFile(file.getName(), description.getText(), owner.getText() );
					try {
						newFile.importContents(file);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					projectfiles.sendGoods(newFile);
					projectfiles.setCreateFile(new CreateFileWindow(projectfiles));
				}
			}
			
		});
		
		selectFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFileChooser jfc = new JFileChooser();
				jfc.setFileFilter(new FileNameExtensionFilter("Text Files", "txt", "text"));
				jfc.setAcceptAllFileFilterUsed(false);
				int retVal = jfc.showOpenDialog(null);
				if(retVal == JFileChooser.APPROVE_OPTION){
					file = jfc.getSelectedFile();
				}
				if (file != null) {
					selectFile.setText(file.getName());
				}
			}
		});
	}
}
