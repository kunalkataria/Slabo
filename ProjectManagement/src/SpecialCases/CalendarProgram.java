package SpecialCases;

import javax.swing.*;
//import javax.swing.event.*;
import javax.swing.table.*;

import gui.CalendarGUI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import client.Calendar;
import objects.Event;

//http://javahungry.blogspot.com/2013/06/calendar-implementation-gui-based.html got thes structure of the calendar
//from this website
public class CalendarProgram extends JPanel{
    static JLabel lblMonth, lblYear;
    static JButton btnPrev, btnNext;
    static JTable tblCalendar;
    static JComboBox cmbYear;
    static JFrame frmMain;
    static Container pane;
    static DefaultTableModel mtblCalendar; //Table model
    static JScrollPane stblCalendar; //The scrollpane
    static JPanel pnlCalendar;
    static int realYear, realMonth, realDay;
	public static int currentYear;
	public static int currentMonth;
    private Calendar c;
    private static Vector<Pairs> allPairs;
    private static Vector<objects.Event> allEvents;
    private static HashMap<String, String> alphaLookUp;
    private static HashMap<String, String> numLookUp;
    
    private Color baseColor = Color.decode("#45B3D7");
	private Color lightBase = Color.decode("#6BC6E4");
	private Color lightSelect = Color.decode("#9CDCF1");
    
    public CalendarProgram(Calendar c, Vector<Event> events){
    	super();
    	this.c = c;
    	setBackground(baseColor);
    	allPairs = new Vector<Pairs>();
    	allEvents = events;
    	alphaLookUp = new HashMap<String, String>();
    	numLookUp = new HashMap<String, String>();
    	createMaps();
    	setLayout(null);
    	setBorder(BorderFactory.createTitledBorder("Calendar"));
    	lblMonth = new JLabel ("January");
        lblYear = new JLabel ("Change year:");
        cmbYear = new JComboBox();
        btnPrev = new JButton ("&lt;&lt;");
        btnNext = new JButton ("&gt;&gt;");
        mtblCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
        tblCalendar = new JTable(mtblCalendar);
        stblCalendar = new JScrollPane(tblCalendar);
        //pnlCalendar = new JPanel(null);
        
        //action listeners
        btnPrev.addActionListener(new btnPrev_Action());
        btnNext.addActionListener(new btnNext_Action());
        cmbYear.addActionListener(new cmbYear_Action());
        
        //add controls to the panel
        add(lblMonth);
        add(lblYear);
        add(cmbYear);
        add(btnPrev);
        add(btnNext);
        add(stblCalendar);
        
        //set bounds for the panel will have to edit this later
        setBounds(0, 0, 320, 335);
        lblMonth.setBounds(160-lblMonth.getPreferredSize().width/2, 25, 100, 25);
        lblYear.setBounds(10, 305, 80, 20);
        cmbYear.setBounds(230, 305, 80, 20);
        btnPrev.setBounds(10, 25, 50, 25);
        btnNext.setBounds(260, 25, 50, 25);
        stblCalendar.setBounds(10, 50, 300, 250);
        getCalendar();
        
    }
    public void getCalendar(){
    	//Get real month/year
        GregorianCalendar cal = new GregorianCalendar(); //Create calendar
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
        realMonth = cal.get(GregorianCalendar.MONTH); //Get month
        realYear = cal.get(GregorianCalendar.YEAR); //Get year
        currentMonth = realMonth; //Match month and year
        currentYear = realYear;
        
        //Add headers
        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
        for (int i=0; i<7; i++){
            mtblCalendar.addColumn(headers[i]);
        }
        
        tblCalendar.getParent().setBackground(tblCalendar.getBackground()); //Set background
        
        //No resize/reorder
        tblCalendar.getTableHeader().setResizingAllowed(false);
        tblCalendar.getTableHeader().setReorderingAllowed(false);
        
        //Single cell selection
        tblCalendar.setColumnSelectionAllowed(true);
        tblCalendar.setRowSelectionAllowed(true);
        tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //Set row/column count
        tblCalendar.setRowHeight(38);
        mtblCalendar.setColumnCount(7);
        mtblCalendar.setRowCount(6);
        
        //Populate table
        for (int i=realYear-100; i<=realYear+100; i++){
            cmbYear.addItem(String.valueOf(i));
        }
        refreshCalendar (realMonth, realYear, false);
        //Add action listener for table
        tblCalendar.addMouseListener(new MouseAdapter(){
        	public void mouseClicked(java.awt.event.MouseEvent evt) {
        		if(!c.project().isLoggedIn()){
        			return;
        		}
                int row = tblCalendar.rowAtPoint(evt.getPoint());
                int col = tblCalendar.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {
                	Integer check = (Integer)tblCalendar.getModel().getValueAt(row,col);
                	if(check != null){
                		
                    	String getCalMonth = alphaLookUp.get(lblMonth.getText());
                    	String getCalDay = "";
                    	if(check <= 9){
                    		getCalDay = "0"+check;
                    	}
                    	else{
                    		getCalDay = check+"";
                    	}                    	
                    	String totalDate = getCalMonth + "/"+ getCalDay + "/" + currentYear;
                    	CalendarAdd eventDialog = new CalendarAdd(totalDate, CalendarProgram.this, c, row, col);
                    	c.setCalendarWindow(eventDialog);
                	}
                	
                }
            }
        });
      
    }
    
    public static void refreshCalendar(int month, int year, boolean b){
        //Variables
        String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int nod, som; //Number Of Days, Start Of Month
        
        //Allow/disallow buttons
        btnPrev.setEnabled(true);
        btnNext.setEnabled(true);
        if (month == 0 && year <= realYear-10){btnPrev.setEnabled(false);} //Too early
        if (month == 11 && year >= realYear+100){btnNext.setEnabled(false);} //Too late
        lblMonth.setText(months[month]); //Refresh the month label (at the top)
        lblMonth.setBounds(160-lblMonth.getPreferredSize().width/2, 25, 180, 25); //Re-align label with calendar
        cmbYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
        
        //Clear table
        for (int i=0; i<6; i++){
            for (int j=0; j<7; j++){
                mtblCalendar.setValueAt(null, i, j);
            }
        }
        
        //Get first day of month and number of days
        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        som = cal.get(GregorianCalendar.DAY_OF_WEEK);
        
        //Draw calendar
        for (int i=1; i<=nod; i++){
            int row = new Integer((i+som-2)/7);
            int column  =  (i+som-2)%7;
            mtblCalendar.setValueAt(i, row, column);
           
        }
        
        //Apply renderers
        if(b){
        	tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRendererRepaint());
        	}
        else{
        	tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
        }
       
        
 
    }
    
    static class tblCalendarRendererRepaint extends DefaultTableCellRenderer{
    	 public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
    		 super.getTableCellRendererComponent(table, value, selected, focused, row, column);
    			
    		 if (column == 0 || column == 6){ //Week-end
                 setBackground(new Color(255, 220, 220));
             }
             else{ //Week
                 setBackground(new Color(255, 255, 255));
             }
    		 if(value != null){
    			 for(int i = 0; i < allEvents.size(); i++){
    				//curr is the current event
    				objects.Event curr = allEvents.elementAt(i);
    				String eventMonth = curr.getMonth();
    				String eventYear = curr.getYear();
    				int changeYear = Integer.parseInt(eventYear);
    				String currCalMonth = alphaLookUp.get(lblMonth.getText());
    				if(eventMonth.equals(currCalMonth) && changeYear == currentYear && column == curr.getCol() && row == curr.getRow())
    				{
    					setBackground(new Color(220,220, 255));
    				}
    			 }
    		 }
    	table.getSelectionModel().clearSelection();
    	return this;
     }
   }
    		
    static class tblCalendarRenderer extends DefaultTableCellRenderer{
        public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            if (column == 0 || column == 6){ //Week-end
                setBackground(new Color(255, 220, 220));
            }
            else{ //Week
                setBackground(new Color(255, 255, 255));
            }
            setBorder(null);
            setForeground(Color.black);
            return this;
        }
    }
    
    static class btnPrev_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (currentMonth == 0){ //Back one year
                currentMonth = 11;
                currentYear -= 1;
            }
            else{ //Back one month
                currentMonth -= 1;
            }
            
            refreshCalendar(currentMonth, currentYear, true);
        }
    }
    static class btnNext_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (currentMonth == 11){ //Foward one year
                currentMonth = 0;
                currentYear += 1;
            }
            else{ //Foward one month
                currentMonth += 1;
            }
            
            refreshCalendar(currentMonth, currentYear, true);
        }
    }
    static class cmbYear_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (cmbYear.getSelectedItem() != null){
                String b = cmbYear.getSelectedItem().toString();
                currentYear = Integer.parseInt(b);
                refreshCalendar(currentMonth, currentYear, true);
            }
        }
    }
    static void addEvent(objects.Event e){
    	allEvents.add(e);
    }
    
    static void createMaps(){
    	  String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    	  String[] numMonths = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    	  for(int i = 0; i < months.length; i++){
    		  alphaLookUp.put(months[i], numMonths[i]);
    		  numLookUp.put(numMonths[i], months[i]);
    	  }
    }
    
}

