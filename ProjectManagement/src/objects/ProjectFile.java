package objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Scanner;

public class ProjectFile implements Serializable{
	private static final long serialVersionUID = -595598126698471049L;
	private String contents; //text files only
	private String fileName;
	private String description;
	private String owner;
	
	public ProjectFile(String fileName, String description, String owner){
		this.fileName = fileName;
		this.description = description;
		this.owner = owner;
	}
	
	//class methods
	public void importContents(File file) throws FileNotFoundException{
		Scanner s = new Scanner(file);
		contents = null;
		if(s.hasNext()){
			contents = s.useDelimiter("\\Z").next();
		}
		s.close();
	}
	
	public boolean isOwner(String u){
		if(u.equals(owner)){
			return true;
		}
		return false;
	}
	
	//setters
	public void setFileName(String newName){
		fileName = newName;
	}
	public void setDescription(String newDesc){
		description = newDesc;
	}
	
	//getters
	public String getOwner(){
		return owner;
	}
	public String getDescription(){
		return description;
	}
	public String getFileName(){
		return fileName;
	}
	public String getContents(){
		return contents;
	}
	
}
