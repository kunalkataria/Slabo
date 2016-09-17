package CustomUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class WhiteButton extends JButton {
	private Color inColor;
	private Color outColorDark;
	private static final long serialVersionUID = 3964529762960557244L;
	{
		
		setOpaque(true);
		setBorderPainted(false);
		Color bColor = Color.decode("#F2F9FC");
		Color bColorDark = Color.decode("#EEEEEE");
		setBackground(bColor);
		addMouseListener(new MouseAdapter() {
				@Override
		public void mouseEntered(MouseEvent arg0) {
					setBackground(bColorDark);
					revalidate();
					repaint();
			}
				@Override
		public void mouseExited(MouseEvent arg0) {
				setBackground(bColor);
				revalidate();
				repaint();
			}
		});
	}
	public WhiteButton(String string){
		super(string);
		setOpaque(true);
		setBorderPainted(false);
		
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
}
