package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import objects.Poll;
import objects.PollItem;

public class ResultsWindow extends JPanel{
	
	private static final long serialVersionUID = 1L;

	private Poll p;
	
	private JPanel results;
	private JLabel resultsL;
	private Color baseColor = Color.decode("#FF9B3E");
	
	public ResultsWindow(Poll given){
		p = given;
		setSize(340, 480);
		
		setLayout(new BorderLayout());
		setBackground(baseColor);
		
		resultsL = new JLabel("Results:");
		resultsL.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		resultsL.setForeground(Color.white);
		resultsL.setFont(resultsL.getFont().deriveFont(Font.PLAIN, 32));
		
		add(resultsL, BorderLayout.NORTH);
		
		results = new JPanel();
		results.setBackground(baseColor);
		results.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		int gridIncr = 0;
		
		Vector<PollItem> tempOptions = p.getOptions();
		for (int i = 0 ; i < tempOptions.size(); i++){
			PollItem curr = tempOptions.elementAt(i);
			
			gbc.gridx = 0;
			gbc.gridy = gridIncr;
			gbc.ipadx = 0;
			gbc.insets = new Insets(3,3,3,3);
			JLabel resultName = new JLabel(curr.getItem());
			resultName.setFont(resultName.getFont().deriveFont(Font.PLAIN, 24));
			resultName.setBackground(baseColor);
			resultName.setForeground(Color.white);
			results.add(resultName, gbc);
			gbc.gridx = 1;
			gbc.gridy = gridIncr;
			gbc.ipadx = 120;
			gridIncr++;
			Integer convert = (Integer) curr.getVotes();
			JLabel resultItem = new JLabel(convert.toString());
			resultItem.setFont(resultItem.getFont().deriveFont(Font.PLAIN, 24));
			resultItem.setBackground(baseColor);
			resultItem.setForeground(Color.white);
			results.add(resultItem, gbc);
		}
		
		add(results, BorderLayout.CENTER);
		setVisible(true);
	}

}
