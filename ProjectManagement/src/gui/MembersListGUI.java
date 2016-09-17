package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.MembersList;
import objects.User;

public class MembersListGUI extends JPanel{

	private static final long serialVersionUID = -4693561164493152463L;
	
	private JPanel all;
	private Color baseColor = Color.decode("#AA7CDF");
	private Color lightBase = Color.decode("#C9A8F0");
	private Color lightHover = Color.decode("#EADAFB");

	class MemberPanel extends JPanel{
	
		private static final long serialVersionUID = -4631807518837475788L;
		private JPanel left, right;
		MemberPanel(User u){
			super();
			setLayout(new BorderLayout());
			setBackground(lightBase);
			left = new JPanel();
			left.setBackground(lightBase);
			ImageIcon imageIcon = new ImageIcon("./img/profileicon.png"); // load the image to a imageIcon
			Image image = imageIcon.getImage(); // transform it 
			Image newimg = image.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			imageIcon = new ImageIcon(newimg);
			JLabel pic = new JLabel();
			pic.setBackground(lightBase);
			pic.setIcon(imageIcon);
			left.add(pic);
			
			right = new JPanel();
			right.setBackground(lightBase);
			right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
			JLabel username = new JLabel(u.getUsername());
			username.setForeground(Color.white);
			JLabel email = new JLabel(u.getEmail());
			email.setForeground(Color.white);
			right.add(username);
			right.add(email);
			add(left, BorderLayout.WEST);
			add(right, BorderLayout.CENTER);
			setBorder(BorderFactory.createLineBorder(baseColor, 3));
			
			addMouseListener(new MouseAdapter(){
				@Override
				public void mouseEntered(MouseEvent e) {
					setBackground(lightHover);
					left.setBackground(lightHover);
					right.setBackground(lightHover);
					repaint();
				}

				@Override
				public void mouseExited(MouseEvent e) {
					setBackground(lightBase);
					left.setBackground(lightBase);
					right.setBackground(lightBase);
					repaint();
				}
			});
		}
	}
	
	public MembersListGUI(MembersList ml){//MembersList ml){
		super();
		instantiateComponents();
	}

	private void instantiateComponents() {
		setBackground(baseColor);
		all = new JPanel();
		all.setBackground(baseColor);
		setLayout(new BorderLayout());
		all.setLayout(new BoxLayout(all, BoxLayout.Y_AXIS));
		add(all, BorderLayout.NORTH);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BorderLayout());
		titlePanel.setBackground(baseColor);
		titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JLabel title = new JLabel("Project Members");
		title.setForeground(Color.white);
		title.setFont(title.getFont().deriveFont(Font.BOLD, 32));
		titlePanel.add(title, BorderLayout.CENTER);
		all.add(titlePanel);
	}
	
	public void refresh(User u){
		MemberPanel mp = new MemberPanel(u);
	
		all.add(mp);
		all.repaint();
		all.revalidate();
	}
}
