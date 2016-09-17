package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import CustomUI.BlueButton;
import CustomUI.BluePanel;
import CustomUI.IconButton;
import client.Calendar;
import objects.Event;

public class CalendarGUI extends BluePanel{

	private static final long serialVersionUID = 1L;
	
	private Vector<EventPanel> eventPanels;
	private JPanel calPanel;
	private Color baseColor = Color.decode("#45B3D7");
	private Color lightBase = Color.decode("#6BC6E4");
	private Color lightSelect = Color.decode("#9CDCF1");
	
	private Calendar cal;
	
	class EventPanel extends JPanel{
		private static final long serialVersionUID = 3122154567702893915L;
		
		private JLabel eventName, date, ownerString;
		private Event event;
		private JButton infoB;
		
		public EventPanel(Event e){
			super();
			
			setBackground(lightBase);
			addMouseListener(new MouseAdapter(){
				public void mouseEntered(MouseEvent e){
					setBackground(lightSelect);
					repaint();
				}
				public void mouseExited(MouseEvent e){
					setBackground(lightBase);
					repaint();
				}
			});
			setOpaque(true);
			setLayout(new GridLayout(1,4));
			setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			eventName = new JLabel("  "+e.getTitle());
			eventName.setFont(eventName.getFont().deriveFont(Font.PLAIN, 18));
			eventName.setForeground(Color.white);
			eventName.setHorizontalAlignment(SwingConstants.CENTER);
			date = new JLabel(e.getDate());
			date.setForeground(Color.white);
			date.setFont(eventName.getFont());
			date.setHorizontalAlignment(SwingConstants.CENTER);
			ownerString = new JLabel(e.getCreator());
			ownerString.setForeground(Color.white);
			ownerString.setFont(eventName.getFont());
			ownerString.setHorizontalAlignment(SwingConstants.CENTER);
			event = e;
			
			try {
				infoB = new IconButton("icons/filesinfo.png", "Info", null, null);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			add(eventName);
			add(date);
			add(ownerString);
			add(infoB);
			addPanelActions();
		}
		
		public void addPanelActions(){
			infoB.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
		           showInfo();
		         }
			});
		}
		public void actionPerformed(ActionEvent e){
			showInfo();
		}
				
		private void showInfo(){
			BluePanel allInfo = new BluePanel();
			BluePanel header = new BluePanel();
			BluePanel description = new BluePanel();
			allInfo.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			header.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			description.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			allInfo.setBackground(Color.decode("#45B3D7"));
			header.setBackground(Color.decode("#45B3D7"));
			description.setBackground(Color.decode("#45B3D7"));
			header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
			JLabel titleLabel = new JLabel(event.getTitle());
			titleLabel.setForeground(Color.white);
			header.add(titleLabel);
			JLabel dateLabel = new JLabel("Date: "+ event.getDate());
			dateLabel.setForeground(Color.WHITE);
			header.add(dateLabel);
			JLabel createdByLabel = new JLabel("Created by: " + event.getCreator());
			createdByLabel.setForeground(Color.WHITE);
			header.add(createdByLabel);
			JLabel descriptionLabel = new JLabel("Description: ");
			descriptionLabel.setForeground(Color.WHITE);
			header.add(descriptionLabel);
			
			JTextArea descriptInfo = new JTextArea();
			descriptInfo.setBorder(BorderFactory.createEmptyBorder());
			descriptInfo.setBackground(Color.decode("#45B3D7"));
			descriptInfo.setLineWrap(true);
			descriptInfo.setText(event.getDescription());
			descriptInfo.setEditable(false);
			descriptInfo.setForeground(Color.WHITE);
			description.setLayout(new BorderLayout());
			description.add(descriptInfo,BorderLayout.CENTER);//new JLabel(event.getDescription()));
			JScrollPane descriptScroll = new JScrollPane();
			descriptScroll.setBorder(BorderFactory.createEmptyBorder());
			descriptInfo.setCaretPosition(0);
			descriptScroll.setViewportView(description);
			descriptScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			descriptScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			allInfo.setLayout(new BorderLayout());
			allInfo.add(header, BorderLayout.NORTH);
			allInfo.add(descriptScroll, BorderLayout.CENTER);
			allInfo.setOpaque(true);
			Color custom = Color.decode("#4FC3F7");
			Border blueLine = BorderFactory.createLineBorder(custom);
			TitledBorder title;
			title = BorderFactory.createTitledBorder(blueLine, event.getTitle());
			allInfo.setBorder(title);
			
			
			cal.setCalendarWindow(allInfo);
		}
		
		public int getID(){
			return event.getEventID();
		}

		public void updateEvent(Event e) { //only for RSVPs pretty much
			event = e;
			//update the info
		}

	}
	
	public CalendarGUI(Calendar c){
		super();
		cal = c;
		instantiateComp();
		setSize(1000,500);
		setVisible(true);
		setOpaque(true);
	}
	
	private void instantiateComp(){
		//main events widget
		setBackground(baseColor);
		JLabel titleLabel = new JLabel("Events");
		titleLabel.setForeground(Color.white);
		titleLabel.setFont(titleLabel.getFont().deriveFont(Font.PLAIN, 32));
		titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		calPanel = new JPanel();
		calPanel.setBackground(baseColor);
		//equivalent of all
		setLayout(new BorderLayout());
		calPanel.setLayout(new BoxLayout(calPanel, BoxLayout.Y_AXIS));
		
		eventPanels = new Vector<EventPanel>();
		
		Box vBox = Box.createVerticalBox();
		Box hBox = Box.createHorizontalBox();
		hBox.add(titleLabel);
		vBox.add(hBox);
		vBox.add(calPanel);
		add(vBox, BorderLayout.NORTH);
		
	}
	
	public void refresh(Event e, boolean update){
		if(update){
			for(int i = 0; i < eventPanels.size(); i++){
				if(eventPanels.elementAt(i).getID() == e.getEventID()){
					eventPanels.elementAt(i).updateEvent(e);
				}
			}
		}
		else{
			EventPanel toAdd = new EventPanel(e);
			eventPanels.add(toAdd);  //chronological
			calPanel.add(toAdd);
		}
		
	}
	
}
