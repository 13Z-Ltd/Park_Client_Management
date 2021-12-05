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

@SuppressWarnings("serial")
public class ActivationDrawPanel extends JPanel {
	Color DARKBROWN = new Color(55, 45, 40);
	Color ORANGE = new Color(251, 182, 0);
	Color GRASSGREEN = new Color(150, 169, 56);
	Color BEIGE = new Color(239,231,219);
	
	/**
	 * Create the panel
	 */
	public ActivationDrawPanel() {
		setPreferredSize(new Dimension(1580,720));
		setBorder(null);
		setBackground(BEIGE);
		setLayout(null);		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//Icons		
		ImageIcon birdIcon = new ImageIcon(getClass().getClassLoader().getResource("nice_bird.png"));
		JLabel niceBird = new JLabel(birdIcon);
		niceBird.setBounds(600, 19, 70, 57);
		niceBird.setVisible(true);
		add(niceBird);

		//borders
		g2d.setColor(DARKBROWN);
		g2d.fillRect(0, 50, 480, 445);
		
		g2d.setColor(ORANGE);
		g2d.fillRect(-20, 30, 480, 425);

		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 40, 450, 405);
		
		//WristPanel
		g2d.setColor(ORANGE);
		g2d.fillRect(570, 110, 1000, 440);
		g2d.setColor(DARKBROWN);
		g2d.fillRect(550, 90, 1000, 405);
		
		//TotalField
		g2d.setColor(ORANGE);
		g2d.fillRect(90, 511, 440, 127);
		g2d.setColor(DARKBROWN);
		g2d.fillRect(90, 637, 440, 50);
		
		Path2D path = new Path2D.Double();
		path.moveTo(0, 724);
		path.lineTo(1584, 684);
		path.lineTo(1584, 734);
		path.lineTo(0, 734);
		path.closePath();
		g2d.setColor(GRASSGREEN);
		g2d.fill(path);
		
		path = new Path2D.Double();
		path.moveTo(0, 734);
		path.lineTo(1584, 709);
		path.lineTo(1584, 734);
		path.closePath();		
		g2d.setColor(ORANGE);
		g2d.fill(path);		
		g2d.fillRect(0, 734, 1584, 20);		
	}
	
	@Override
    public Dimension getPreferredSize() {
        return new Dimension(1580,720);
    }
}
