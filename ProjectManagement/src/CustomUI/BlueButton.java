package CustomUI;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;


public class BlueButton extends JButton {
	private static final long serialVersionUID = 3964529762960557244L;
	{
		
		setOpaque(true);
		setBorderPainted(false);
		Color bColor = Color.decode("#4FC3F7");
		Color bColorDark = Color.decode("#03A9F4");
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
	public BlueButton(String string){
		super(string);
		setOpaque(true);
		setBorderPainted(false);
		
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
}

