package CustomUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class CoolScrollBarUI extends BasicScrollBarUI{

	private Image sThumbImage;
	private Image sButtonUpImage;
	private Image sButtonDownImage;
	
	{
	    try {
			sThumbImage = ImageIO.read(new File("img/scrollbar/scrollthumb.png"));
			sButtonUpImage = ImageIO.read(new File("img/scrollbar/uparrow.png"));
			sButtonDownImage = ImageIO.read(new File("img/scrollbar/downarrow.png"));
		} catch (IOException e) {e.printStackTrace();}
	}
	
	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
		Image sized = sThumbImage.getScaledInstance(r.width, r.height, Image.SCALE_FAST);
		g.drawImage(sized, r.x, r.y, r.width, r.height, null);
	}
	
	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
		g.setColor(new Color(0,0,0,0));
		g.drawRect(0, 0, 0, 0);
	}
	
	@Override
    protected JButton createDecreaseButton(int orientation) {
		JButton b = new JButton();
		b.setEnabled(false);
		b.setVisible(false);
		return b;
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
    	JButton b = new JButton();
		b.setEnabled(false);
		b.setVisible(false);
		return b;
    }
	
}
