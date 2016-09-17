package objects;

import java.io.Serializable;

//import java.awt.Image;

public class User implements Serializable, Comparable<User>{

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String email;
	
	public User(String username, String email){
		this.username = username;
		this.email = email;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getEmail(){
		return email;
	}

	@Override
	public int compareTo(User o) {
		return this.username.compareTo(o.username);
	}

}
