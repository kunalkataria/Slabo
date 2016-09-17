package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import CustomUI.IconButton;
import client.Polls;
import objects.Poll;
import objects.PollItem;
import objects.PollVote;
import server.Command;
import server.Command.CommandType;

public class PollsWindow extends JPanel{

	Polls poll;
	PollsWindow pw = this;
	Poll p;
	
	JPanel overall, voting, results;
	
	JLabel titleL, resultsL;
	Vector <JRadioButton> allOptions;
	ButtonGroup optionBG;
	IconButton vote;
	private Color baseColor = Color.decode("#FF9B3E");
	private Color lightBase = Color.decode("#FFAD61");
	private Color lightSelect = Color.decode("#FFC38C");
	int totalVotes;
	
	public PollsWindow(Poll given, Polls p2){
		p = given;
		poll = p2;
		setSize(340, 480);
		initiateVariables();
		createGUI();
		addActions();
		setVisible(true);
	}
	
	private void initiateVariables(){
		setBackground(baseColor);
		
		overall = new JPanel();
		
		voting = new JPanel();
		
		titleL = new JLabel(p.getTitle());
		titleL.setFont(titleL.getFont().deriveFont(Font.PLAIN, 32));
		
		totalVotes = 0;
		optionBG = new ButtonGroup();
		allOptions = new Vector<JRadioButton>();
		Vector<PollItem> Options = p.getOptions();
		for (int i = 0 ; i < Options.size(); i++){
			JRadioButton tempOption = new JRadioButton(Options.elementAt(i).getItem());
			tempOption.setForeground(Color.white);
			tempOption.setFont(tempOption.getFont().deriveFont(Font.PLAIN, 24));
			tempOption.setActionCommand(Options.elementAt(i).getItem());
			totalVotes += Options.elementAt(i).getVotes();
			allOptions.add(tempOption);
			optionBG.add(tempOption);
		}

		try {
			vote = new IconButton("icons/addbutton.png", "Vote!", null, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		overall.setBackground(baseColor);
		voting.setBackground(baseColor);
	}
	
	private void createGUI(){
		voting.setLayout(new GridLayout(0,1));
		titleL.setForeground(Color.white);
		voting.add(titleL);
		for (int i = 0; i < allOptions.size(); i++){
			allOptions.elementAt(i).setBackground(baseColor);
			voting.add(allOptions.elementAt(i));
		}
		voting.add(vote);
				
		overall.add(voting, "first");
		add(overall);
	}
	private void addActions(){
		vote.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Command cmd = new Command();
				cmd.commandType = CommandType.PollVote;
				PollVote pv = new PollVote(p.getID(), optionBG.getSelection().getActionCommand());
				cmd.setGoods(pv);
				cmd.setUsername(poll.getUser().getUsername());
				poll.sendCommand(cmd);
			}
			
		});
	}
	
}