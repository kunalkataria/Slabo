package client;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gui.FilePreviewWindow;
import gui.FilesGUI;
import objects.ProjectFile;
import server.Command;
import server.Command.CommandType;
import testing.TestProject;

public class ProjectFiles implements Serializable {

	private static final long serialVersionUID = -8137623111767423798L;
	private Vector<ProjectFile> projectFiles;
	private FilesGUI fGUI;
	private Project project;
	private TestProject tp;
	
	public ProjectFiles(){
		projectFiles = new Vector<ProjectFile>();
		fGUI = new FilesGUI(this);
	}
	
	public ProjectFiles(Vector<ProjectFile> projectFiles, Project p, TestProject tp){
		this.projectFiles = projectFiles;
		this.tp = tp;
		project = p;
		fGUI = new FilesGUI(this);
		for(ProjectFile pf : projectFiles){
			fGUI.refresh(pf);
			fGUI.revalidate();
		}
	}
	
	public void readCommand(Command c){
		ProjectFile cur = (ProjectFile)c.getGoods();
		if(c.commandType == CommandType.Download){
			try {
				File create = new File(fGUI.getDownloadLocation() + "_" + cur.getFileName());
				if(!create.exists()){
					create.createNewFile();
					PrintWriter pw = new PrintWriter(create);
					pw.write(cur.getContents());
					pw.close();
					String[] options = {"Yes", "No"};
					int retVal = JOptionPane.showOptionDialog(fGUI, 
							"Download complete. Would you like to view the file?", "File Downloaded!", 
							JOptionPane.YES_NO_OPTION,
							JOptionPane.INFORMATION_MESSAGE,
							null, options, options[0]);
					if(retVal == 0){
						tp.setFileInfo(new FilePreviewWindow(cur));
					}
				}
				else{
					JOptionPane.showMessageDialog(fGUI, "Download could not be completed; Filename in download location already exists. Consider changing download directory.",
							 "Download Failed", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			projectFiles.add(cur);
			//refresh
			fGUI.refresh(cur);
			fGUI.revalidate();
		}
	}
	
	public void sendGoods(ProjectFile pf){
		Command c = new Command();
		c.commandType = CommandType.ProjectFile;
		c.setGoods(pf);
		project.sendCommand(c);	
	}
	
	public void requestDownload(ProjectFile request){
		Command c = new Command();
		c.commandType = CommandType.Download;
		c.setGoods(request);
		project.sendCommand(c);
	}
	
	public Project getProject(){
		return project;
	}
	
	public FilesGUI getGUI(){
		return fGUI;
	}
	
	public void setFileInfo(JPanel p){
		tp.setFileInfo(p);
	}
	
	public void setCreateFile(JPanel p){
		tp.setCreateFile(p);
	}
}
