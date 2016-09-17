package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import CustomUI.IconButton;
import client.Polls;
import client.Project;
import objects.Poll;
import server.Command;
import server.Command.CommandType;

public class PollsGUI extends JPanel{

	private JLabel pollsTitle;
	private JScrollPane listScroll;
	private JList pollsList;
	private DefaultListModel<String> pollsListBTS;
	private JLabel addPoll;
	private IconButton addButton;
	private PollsGUI pp;
	private Vector<Poll> allPolls;
	private Polls poll;
	private Color baseColor = Color.decode("#FF9B3E");
	private Color lightBase = Color.decode("#FFAD61");
	private Color lightSelect = Color.decode("#FFC38C");
	
 	public PollsGUI(Polls p) {
 		instantiateVariables();
		createGUI();
		addActions();
		pp = this;
		poll = p;
		if(!poll.getProject().isLoggedIn()){
			setGuestMode();
		}
	}
 		
 	//creating from server info
	public PollsGUI(Polls p, Vector<Poll> newPolls) {
		instantiateVariables();
		createGUI();
		addActions();
		pp = this;
		poll = p;
		if(!poll.getProject().isLoggedIn()){
			setGuestMode();
		}
		for (int i = 0 ; i < newPolls.size(); i++){
			addPoll(newPolls.elementAt(i));
		}
	}

	private void instantiateVariables(){
		pollsTitle = new JLabel("Polls:");
		pollsTitle.setForeground(Color.white);
		pollsTitle.setFont(pollsTitle.getFont().deriveFont(Font.PLAIN, 32));
		pollsListBTS = new DefaultListModel<String>();
		pollsList = new JList(pollsListBTS);
		pollsList.setBackground(baseColor);
		pollsList.setFont(pollsList.getFont().deriveFont(Font.PLAIN, 18));
		pollsList.setForeground(Color.white);
		pollsList.setBorder(BorderFactory.createLineBorder(Color.white, 1));
		pollsList.setSelectionBackground(lightSelect);
		listScroll = new JScrollPane(pollsList);
		listScroll.setBorder(BorderFactory.createEmptyBorder());
		addPoll = new JLabel("Create New Poll   ");
		addPoll.setForeground(Color.white);
		addPoll.setFont(addPoll.getFont().deriveFont(Font.PLAIN, 24));
		try {
			addButton = new IconButton("icons/addbutton.png", "Add", null, null);
			addButton.doesHoverText(false);
		} catch (IOException e) {}
		allPolls = new Vector<Poll>();
	}
	
	private void createGUI(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Box hBoxa = Box.createHorizontalBox();
		hBoxa.add(pollsTitle);
		add(hBoxa);
		add(listScroll);
		Box hBox = Box.createHorizontalBox();
		hBox.add(addPoll);
		hBox.add(addButton);
		add(hBox);
		setBackground(baseColor);
		setBorder(BorderFactory.createEmptyBorder(Project.BORDER, Project.BORDER, Project.BORDER, Project.BORDER));
		repaint();
	}
	
	private void addActions(){
		addButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				NewPollWindow testWindow = new NewPollWindow(pp,poll);
				//poll.setPollWindow(new NewPollWindow(pp, poll));
				poll.setPollWindow(testWindow);
			}
		});
		
		pollsList.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent evt){
				JList list = (JList)evt.getSource();
				if (evt.getClickCount() == 1){
					int index = list.locationToIndex(evt.getPoint());
					if (index < allPolls.size() && index >= 0){
						if(allPolls.elementAt(index).canVote() && poll.getProject().isLoggedIn()){
							poll.setPollWindow(new PollsWindow(allPolls.elementAt(index), poll));
						}
						else{
							Command cmd = new Command();
							cmd.commandType = CommandType.PollResults;
							cmd.setGoods((Integer)allPolls.elementAt(index).getID());
							poll.sendCommand(cmd);
						}
					}
				}else if (evt.getClickCount() == 2){
					int index = list.locationToIndex(evt.getPoint());
					if (index < allPolls.size() && index >= 0){
						if(allPolls.elementAt(index).canVote() && poll.getProject().isLoggedIn()){
							poll.setPollWindow(new PollsWindow(allPolls.elementAt(index), poll));
						}
						else{
							Command cmd = new Command();
							cmd.commandType = CommandType.PollResults;
							cmd.setGoods((Integer)allPolls.elementAt(index).getID());
							poll.sendCommand(cmd);
						}
					}
				}
			}
		});
	}
	
	public void addPoll(Poll p){
		allPolls.addElement(p);
		String title = p.getTitle();
		pollsListBTS.addElement(title);
	}
	
	public void sendPoll(Poll p){
		poll.sendGoods(p);
	}
	
	public void refresh(Poll p){
		addPoll(p);
		revalidate();
		repaint();
	}
	
	private void setGuestMode(){
		addPoll.setEnabled(false);
	}
	
}
