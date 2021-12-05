package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import model.PreparedWristband;
import model.entities.ActiveWristband;

@SuppressWarnings("serial")
public class CustomWristbandLabel extends JLabel {
	private ImageIcon barCodeImage = new ImageIcon(getClass().getClassLoader().getResource("bar_code.png"));

	private PreparedWristband wristband;

	public CustomWristbandLabel() {
		wristband = new PreparedWristband("WHITE");
		wristband.setBarCodeOn(true);
		// setIcon( new ImageIcon(new ImageIcon("png/spread_white_tyvek.png").getImage().getScaledInstance(880, 78, Image.SCALE_SMOOTH)));
		setIcon(new ImageIcon(getClass().getClassLoader().getResource("spread_white_tyvek.png")));
	}

	public CustomWristbandLabel(PreparedWristband wrist) {
		this.wristband = wrist;
		if (wristband.getColor() == "WHITE") {
			setIcon(new ImageIcon(getClass().getClassLoader().getResource("spread_white_tyvek.png")));
		}
		if (wristband.getColor() == "YELLOW") {
			setIcon(new ImageIcon(getClass().getClassLoader().getResource("spread_yellow_tyvek.png")));
		}
		if (wristband.getColor() == "RED") {
			setIcon(new ImageIcon(getClass().getClassLoader().getResource("spread_red_tyvek2.png")));
		}
	}

	public void setCustomWristbandLabel(ActiveWristband activeWristband) {
		wristband.setBarCodeOn(true);
		wristband.setBarCode(activeWristband.getBarCode());
		wristband.setColor(activeWristband.getColor());
		if (wristband.getColor().equals("WHITE")) {
			setIcon(new ImageIcon(getClass().getClassLoader().getResource("spread_white_tyvek.png")));
		}
		if (wristband.getColor().equals("YELLOW")) {
			setIcon(new ImageIcon(getClass().getClassLoader().getResource("spread_yellow_tyvek.png")));
		}
		if (wristband.getColor().equals("RED")) {
			setIcon(new ImageIcon(getClass().getClassLoader().getResource("spread_red_tyvek2.png")));
		}
	}

	public void setEmptyCustomWritbandLabel() {
		wristband.setBarCodeOn(false);
		wristband.setColor("WHITE");
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (wristband.isBarCodeOn()) {
			barCodeImage.paintIcon(this, g2d, 80, 10);

			g2d.setColor(Color.BLACK);
			g2d.setFont(new Font("Segoe UI Black", Font.PLAIN, 26));
			g2d.drawString(Long.toString(wristband.getBarCode()), 300, 37);
		}
	}
}
