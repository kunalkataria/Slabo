package CustomUI;

import java.awt.Color;

import javax.swing.JPanel;

public class CustomLoginPanel extends JPanel {

	private Color panelColor;
		
	public CustomLoginPanel(){
		super();
		this.setOpaque(true);
		panelColor = Color.BLACK;
		this.setBackground(panelColor);
	}
	
	public void setBackground(String s){
		panelColor = Color.decode(s);
		this.setBackground(panelColor);
	}
}
