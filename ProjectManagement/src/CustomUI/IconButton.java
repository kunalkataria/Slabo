package CustomUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class IconButton extends JButton {
	
	private static final long serialVersionUID = -882982249833966236L;
	private IconButton THIS = this;
	private Color hoverColor;
	private Color backgroundColor;
	private String hoverText;
	private ImageIcon buttonIcon;
	private boolean hover;
	
	public IconButton(String iconPath, String buttonText, Color inhoverColor, Color backgroundColor) throws IOException{
		super();
		setIconImage(iconPath);
		setHoverColor(inhoverColor);
		this.backgroundColor = backgroundColor;
		setBackground(backgroundColor);
		setHoverText(buttonText);
		hover = true;
		setBorder(BorderFactory.createEmptyBorder());
		setContentAreaFilled(false);
		setOpaque(true);
		setPreferredSize(new Dimension(buttonIcon.getIconWidth(), buttonIcon.getIconHeight()));
	
		addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(THIS.hoverColor);
				if(hover){
					THIS.setIcon(null);
					THIS.setText(hoverText);
				}
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if(hover){
					THIS.setText("");
					THIS.setIcon(buttonIcon);
				}
				setBackground(THIS.backgroundColor);
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				setBackground(Color.darkGray);
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				setBackground(THIS.hoverColor);
				repaint();
			}
			
		});
	}
	
	public void setIconImage(String iconPath){
		BufferedImage ImageIcon;
		try {
			ImageIcon = ImageIO.read(new File(iconPath));
			buttonIcon = new ImageIcon(ImageIcon);
			this.setIcon(new ImageIcon(ImageIcon));
			repaint();
			revalidate();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setHoverText(String hoverText){
		this.hoverText = hoverText;
	}
	
	public void setHoverColor(Color color){
		this.hoverColor = color;
	}
	
	public void setBackgroundColor(Color color){
		backgroundColor = color;
		setBackground(backgroundColor);
	}
	
	public void doesHoverText(boolean tf){
		hover = tf;
	}

	/*public static void main(String[] args){
	JFrame test = new JFrame("Test");
	JPanel main = new JPanel();
	
	IconButton ib1 = null;
	try {
		ib1 = new IconButton("C:\\Users\\Acer\\Downloads\\ic_format_list_bulleted_black_48dp\\ic_format_list_bulleted_black_48dp\\web\\balls.png", "Polls", Color.pink);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		main.add(ib1);
		test.add(main, BorderLayout.CENTER);
		test.setSize(400, 400);
		test.setVisible(true);
	}*/

}