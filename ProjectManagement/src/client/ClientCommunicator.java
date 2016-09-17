package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import server.Command;
import server.Command.CommandType;

public class ClientCommunicator extends Thread {
	
	private Socket s;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private ProjectManager pm;
	
	public ClientCommunicator(int port, ProjectManager pm) {
		this.pm = pm;
		try {
			s = new Socket("localhost", port);
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			this.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try{
			while(true) {
				Command c = (Command)ois.readObject();
				pm.readCommand(c);
			}
		} catch (IOException | ClassNotFoundException ioe) {
			JOptionPane.showMessageDialog(null, "Connection to server lost.");
		}
	}
	
	public void sendCommand(Command c) throws IOException {
		oos.writeObject(c);
		oos.flush();
	}
		
	public void sendUpdate(Object update, CommandType commandType){
		Command c = new Command();
		c.commandType = commandType;
		c.setGoods(update);
		
		try{
			sendCommand(c);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
}
