package view.drawpanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DressingDrawPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	Color DARKBROWN = new Color(55, 45, 40);
	Color ORANGE = new Color(251, 182, 0);
	Color GRASSGREEN = new Color(150, 169, 56);	
	Color BEIGE = new Color(239, 231, 219);
	Color DARKGRAY = new Color(60, 60, 60);	
	

	public DressingDrawPanel() {
		setBorder(null);
		setBackground(BEIGE);
		setLayout(null);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Icons
		ImageIcon birdIcon = new ImageIcon(getClass().getClassLoader().getResource("nice_bird_left.png"));
		JLabel niceBird = new JLabel(birdIcon);
		niceBird.setBounds(700, 98, 70, 57);
		niceBird.setVisible(true);
		add(niceBird);

		//top border
		g2d.setColor(GRASSGREEN);
		g2d.fillRect(0, 0, 1584, 50);
		
		// Clock box
		//top border
		g2d.setColor(DARKBROWN);
		g2d.fillRect(0, 50, 1584, 40);
		
		//Shadows
		g2d.setColor(DARKGRAY);
		g2d.fillRect(65, 155, 490, 40);
		
		g2d.fillRect(575, 155, 172, 40);
		
		g2d.fillRect(790, 685, 172, 40);
		
		//wristband Draw Panel
		g2d.setColor(DARKGRAY);
		g2d.fillRect(70, 240, 900, 120);
		
		//Data panel		
		g2d.setColor(DARKBROWN);
		g2d.fillRect(70, 410, 900, 240);
		
		//Statistic Panel Shadow
		g2d.setColor(DARKBROWN);
		g2d.fillRect(1060, 135, 490, 363);
		
		Path2D path = new Path2D.Double();
		path.moveTo(0, 821);
		path.lineTo(1584, 750);
		path.lineTo(1584, 841);
		path.lineTo(0, 841);
		path.closePath();
		g2d.setColor(GRASSGREEN);
		g2d.fill(path);

		path = new Path2D.Double();
		path.moveTo(0, 841);
		path.lineTo(1584, 780);
		path.lineTo(1584, 841);
		path.closePath();
		g2d.setColor(DARKBROWN);
		g2d.fill(path);
		g2d.fillRect(0, 841, 1584, 20);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1580, 720);
	}
}
