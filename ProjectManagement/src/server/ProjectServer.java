package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;

public class ProjectServer extends Thread {
	private ServerSocket ss;
	private Vector<ServerThread> serverThreads;
	
	public ProjectServer(int socketNum) throws IOException{
		System.out.println("PROJECT SERVER STARTED");
		ss = new ServerSocket(socketNum);
		serverThreads = new Vector<ServerThread>();
	}
	
	public void run(){
		try{
			while(true){
				if(!ss.isClosed()){
					ServerThread s = new ServerThread(ss.accept(), this);
					serverThreads.add(s);
				}
			}
		} catch (IOException e){}
	}
	
	public void disconnect(){
		try {
			ss.close();
			closeAllSockets();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessageToAllClients(Command c) {
		for (ServerThread st : serverThreads) {
			st.sendMessage(c);
		}
	}
	
	private void closeAllSockets(){
		for(ServerThread s : serverThreads){
			s.disconnect();
		}
		serverThreads.clear();
	}
	
	public void removeServerThread(ServerThread st) {
		serverThreads.remove(st);
	}
	
	public static void main(String[] args){
		try {
			ProjectServer ps = new ProjectServer(6789);
			ps.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
