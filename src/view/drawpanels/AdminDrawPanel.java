package view.drawpanels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AdminDrawPanel extends JPanel {
	
	private boolean paintWristbandElements;
	private boolean paintUserElements;
	private boolean paintStatisticsElements;
	
	Color DARKBROWN = new Color(55, 45, 40);
	Color ORANGE = new Color(251, 182, 0);
	Color GRASSGREEN = new Color(160, 180, 65); 
	Color SHADOW = new Color(37, 37, 37);
	Color MACAROON = new Color(249, 224, 119);
	Color PASTELRED = new Color(200, 78, 56);
	Color PASTELWHITE = new Color(241, 241, 241);
	Color PASTELBLUE = new Color(0, 168, 243);
	
	private boolean drawPasswordField = false;
	
	private Point[] whiteCoordinates = {new Point(255, 200), new Point(255, 250), new Point(255, 300),
										new Point(1050, 190), new Point(1050, 240), new Point(1050, 290), //new Point(1200, 250), new Point(1350, 250),
										new Point(1050, 390), new Point(1050, 460)}; //new Point(1030, 450), new Point(1030, 540)};
	private Point[] yellowCoordinates = {new Point(405, 200), new Point(405, 250), new Point(405, 300),
										new Point(1200, 190), new Point(1200, 240), new Point(1200, 290), //new Point(1200, 300), new Point(1350, 300),
										new Point(1200, 390), new Point(1200, 460)};
	private Point[] redCoordinates = {new Point(555, 200), new Point(555, 250), new Point(555, 300),
										new Point(1350, 190), new Point(1350, 240), new Point(1350, 290), // new Point(1050, 350), new Point(1200, 350), new Point(1350, 350),
										new Point(1350, 390), new Point(1350, 460)};

	public AdminDrawPanel() {
		paintWristbandElements = paintUserElements = false;
		paintStatisticsElements = true;
		
		setBorder(null);
		setOpaque(false);
		setLayout(null);	
	}
	
	public void changePaintingElements(int i) {
		paintWristbandElements = paintUserElements = paintStatisticsElements = false;
		if(i == 1) paintWristbandElements = true;
		if(i == 2) paintUserElements = true;
		if(i == 3) paintStatisticsElements = true;
	}
	
	public void showPasswordFields() {
		drawPasswordField = !drawPasswordField;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//top border
		g2d.setColor(DARKBROWN);
		g2d.fillRect(0, 0, 1584, 90);
		
		Path2D path = new Path2D.Double();
		path.moveTo(0, 841);
		path.lineTo(1584, 800);
		path.lineTo(1584, 841);
		path.closePath();
		g2d.setColor(DARKBROWN);
		g2d.fill(path);
		g2d.fillRect(0, 841, 1584, 20);
		
		//paint necessary elements
		if(paintWristbandElements) {
			g2d.setColor(SHADOW);
			
			g2d.fillRect(110, 210, 500, 40);

			g2d.fillRect(710, 210, 150, 40);

			g2d.fillRect(960, 210, 150, 40);
			
			g2d.fillRect(1210, 210, 200, 40);
			
			g2d.fillRect(110, 350, 851, 190);
			
			g2d.fillRect(110, 580, 851, 190);
		}
		
		if(paintUserElements) {
			g2d.setColor(SHADOW);
			g2d.fillRect(110, 190, 400, 400);
			
			g2d.fillRect(655, 215, 180, 40);
			
			g2d.fillRect(905, 215, 210, 40);
			
			g2d.fillRect(1185, 215, 210, 40);
			
			g2d.fillRect(905, 365, 210, 40);			
			
			if(drawPasswordField) {
				g2d.fillRect(905, 515, 210, 40);
				
				g2d.fillRect(1185, 515, 210, 40);
			}
			
			g2d.fillRect(1185, 665, 210, 40);
		}
		
		if(paintStatisticsElements) {
			g2d.setColor(SHADOW);
			
			g2d.fillRect(82, 502, 635, 280);
			
			//panels
			g2d.setStroke(new BasicStroke(2.0f));
			g2d.drawRect(70, 140, 660, 290);
			//g2d.drawRect(70, 140, 660, 260);
			
			g2d.drawRect(960, 140, 530, 210);
			g2d.drawRect(800, 365, 690, 200);
			g2d.drawRect(800, 585, 690, 140);
			
			g2d.setColor(PASTELWHITE);			
			for (Point point : whiteCoordinates) {
				g2d.fillRoundRect(point.x, point.y, 110, 40, 20, 20);
			}
			
			g2d.setColor(MACAROON);			
			for (Point point : yellowCoordinates) {
				g2d.fillRoundRect(point.x, point.y, 110, 40, 20, 20);
			}
			
			g2d.setColor(PASTELRED);			
			for (Point point : redCoordinates) {
				g2d.fillRoundRect(point.x, point.y, 110, 40, 20, 20);
			}
			
			g2d.setColor(ORANGE);
			for (int i = 0; i < 3; i++) {				
				g2d.fillRoundRect(305 + (i * 150), 350, 60, 25, 20, 20);
				
				g2d.fillRoundRect(1100 + (i * 150), 520, 60, 25, 20, 20);
			}
			
			//bad weather
			g2d.setColor(PASTELBLUE);
			for (int i = 0; i < 3; i++) {	
				g2d.fillRoundRect(850 + (i*235), 650, 110, 40, 20, 20);
			}
		}
	}

	
}
