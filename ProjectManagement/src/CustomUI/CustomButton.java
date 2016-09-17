package CustomUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

//colors

//#f3460c the red on the logo
//#3FB772 for green on logo
//#FFC400 for yello on logo
//#00B2C9 for the blue on logo
//#F16528 for the font 
//montserrat for the font
public class CustomButton extends JButton{
{
		
		setOpaque(true);
		setBorderPainted(false);
		Color bColor = null; 
		Color bColorDark = null;
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

	public CustomButton(String string){
		super(string);
		setOpaque(true);
		setBorderPainted(false);
	}
	
	public void initializeBColor(String s){
		Color bColor = Color.decode(s);
		setBackground(bColor);
	}
	
	public void initializeBColorDark(String s){
		Color bColorDark = Color.decode(s);
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
}
