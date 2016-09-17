package CustomUI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class BluePanel extends JPanel
{
	
	private Color backgroundColor;
    public BluePanel() 
    {
        super();
        this.setOpaque(true);
        backgroundColor = Color.decode("#FFFFFF");
        this.setBackground(backgroundColor);
    }
    
}