package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import CustomUI.IconButton;
import client.ProjectFiles;
import objects.ProjectFile;

public class FilesGUI extends JPanel {

	private ProjectFiles projectFiles;
	
	private JButton selectLocation;
	private JScrollPane filesScrollPane;
	private JPanel mainPanel;
	private FilesGUI fg = this;
	private JLabel currentLocation;
	private String saveLocation;
	private Color baseColor = Color.decode("#59EC99");
	private Color lightBase = Color.decode("#83F4B4");
	private Color lightSelect = Color.decode("#BAFBD6");
	
	class FilePanel extends JPanel{
		
		ProjectFile pf;
		JButton download, info;
		
		FilePanel(ProjectFile pf){
			super();
			this.pf = pf;
			setLayout(new BorderLayout());
			setBackground(lightBase);
			addMouseListener(new MouseAdapter(){

				@Override
				public void mouseEntered(MouseEvent arg0) {
					setBackground(lightSelect);
					repaint();
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					setBackground(lightBase);
					repaint();
				}
				
			});
			Box hBox = Box.createHorizontalBox();
			JLabel fileLabel = new JLabel("  " + pf.getFileName());
			fileLabel.setForeground(Color.white);
			fileLabel.setFont(fileLabel.getFont().deriveFont(Font.PLAIN, 24));
			add(fileLabel, BorderLayout.WEST);
			try {
				download = new IconButton("icons/filesave.png", "Download!", null, null);
				download.setPreferredSize(new Dimension(90,30));
				info = new IconButton("icons/filesinfo.png", "Info", null, null);
				info.setPreferredSize(new Dimension(90,30));
			} catch (IOException e) {}
			hBox.add(info);
			hBox.add(new JLabel("        "));
			hBox.add(download);
			add(hBox, BorderLayout.EAST);
			add(new JLabel(), BorderLayout.CENTER);
			addActions();
			setBorder(BorderFactory.createLineBorder(Color.white, 1));
			
			if(!projectFiles.getProject().isLoggedIn()){
				setGuestMode();
			}
		}
		
		private void addActions(){
			info.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent elClicko){
					FileWindow temp = new FileWindow(pf, projectFiles);
					projectFiles.setFileInfo(temp);
				}
			});
			download.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent elClicko){
					projectFiles.requestDownload(pf);
				}
			});
		}
		
		private void setGuestMode(){
			download.setEnabled(false);
		}
	}
	
	public FilesGUI(ProjectFiles pf){
		super();
		setLayout(new BorderLayout());
		projectFiles = pf;
		instantiateComp();
		addAction();
		setVisible(true);
		setSize(500,500);
		if(!projectFiles.getProject().isLoggedIn()){
			setGuestMode();
		}
	}
	
	private void instantiateComp(){
		setLayout(new BorderLayout());
		setBackground(baseColor);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JLabel filesTitle = new JLabel("Shared Files");
		filesTitle.setFont(filesTitle.getFont().deriveFont(Font.PLAIN, 32));
		filesTitle.setForeground(Color.white);
		filesTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(filesTitle, BorderLayout.NORTH);
		JPanel mainContainer = new JPanel();
		mainContainer.setBackground(baseColor);
		mainContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainContainer.setLayout(new BorderLayout());
		mainContainer.add(mainPanel, BorderLayout.NORTH);
		filesScrollPane = new JScrollPane();
		filesScrollPane.setViewportView(mainContainer);
		filesScrollPane.setBorder(BorderFactory.createEmptyBorder());
		filesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		filesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//mainPanel.setPreferredSize(new Dimension(filesScrollPane.getWidth(), filesScrollPane.getHeight()));
		add(filesScrollPane, BorderLayout.CENTER);
		
		saveLocation = "serverFiles/";
		Box vBox = Box.createVerticalBox();
		Box hBox = Box.createHorizontalBox();
		selectLocation = new JButton("Download Location: ");
		selectLocation.setForeground(Color.white);
		selectLocation.setBackground(lightBase);
		selectLocation.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				selectLocation.setBackground(lightSelect);
				repaint();
			}
			public void mouseExited(MouseEvent e){
				selectLocation.setBackground(lightBase);
				repaint();
			}
		});
		currentLocation = new JLabel(saveLocation);
		currentLocation.setForeground(Color.white);
		hBox.add(selectLocation);
		hBox.add(new JLabel("    "));
		hBox.add(currentLocation);
		vBox.add(hBox);
		vBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		add(vBox, BorderLayout.SOUTH);
		
	}
	
	private void addAction(){
		
		selectLocation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int retVal = jfc.showOpenDialog(fg);
				if(retVal == JFileChooser.APPROVE_OPTION){
					File file = jfc.getSelectedFile();
					saveLocation = file.getPath();
					currentLocation.setText(saveLocation);
				}
			}
		});
	}
	
	public void refresh(ProjectFile pf){
		FilePanel fp = new FilePanel(pf);
		fp.setSize(getWidth(), 100);
		mainPanel.add(fp);
	}
	
	public String getDownloadLocation(){
		return saveLocation;
	}
	
	private void setGuestMode(){
		selectLocation.setEnabled(false);
	}
	
}
