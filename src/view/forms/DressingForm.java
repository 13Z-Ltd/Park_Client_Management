package view.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import model.Model;
import view.CustomWristbandLabel;
import view.drawpanels.DressingDrawPanel;

public class DressingForm extends MainJFrameForm implements Runnable {
	private static final long serialVersionUID = 1L;

	private Model model;
	
	private JPanel contentPane;
	private DressingDrawPanel receiverDrawPanel;
		
	private JLabel menuIconLabel;
	private JLabel signOutLabel;
	private JLabel rainyIconLabel;
	private JLabel rainyElapsedTimeLabel;
	private JTextField barCodeReceiverTextField;	
	private JPanel wristBandCheckPanel;
	private JLabel exceptionLabel;
	private JLabel wristbandClosingTimeLabel;
	private JButton barCodeReceiverButton;
	private JButton wristbandClosingButton;
	
	private CustomWristbandLabel customWristToDraw;
	private JLabel checkLabel;
	private JLabel checkTimeLabel;

	private JLabel startDateLabel;
	private JLabel stopDateLabel;
	private JLabel spentTimeLabel;
	private JLabel remainingTimeLabel;
	private JLabel badWeatherElapsedTimeLabel;
	private JLabel extraChargesLabel;
	private JLabel currentTimeLabel;
	private JPanel wristbandDataPanel;
	
	private JLabel activeNumberLabel;
	private JLabel dressingNumberLabel;
	private JLabel moreThen2HoursLabel;
	private JLabel moreThen3HoursLabel;
	private JLabel whiteTrackCounterLabel;
	private JLabel yellowTrackCounterLabel;
	private JLabel redTrackCounterLabel;
	
	private Color DARKBROWN = new Color(55, 45, 40);
	private Color ORANGE = new Color(251, 182, 0);
	private Color BEIGE = new Color(239, 231, 219);
	private Color GRAPHITE = new Color(69, 73, 78);
	private Color PASTELBLUE = new Color(0, 168, 243);
	private Color CHILLIRED = new Color(200, 24, 7);
	
	private Font myFont = new Font("Verdana", Font.PLAIN, 20);
	//private Font mySmallFont = new Font("Lucida Calligraphy", Font.BOLD, 13); 
	private Font mySmallFont = new Font("Segoe Script", Font.BOLD, 15);
	private Font mySmallNumberFont = new Font("Segoe Script", Font.BOLD, 16);
	private Font myScriptFont = new Font("Segoe Script", Font.BOLD, 18);
	
	public DressingForm(Model getModel) {
		this.model = getModel;
		
		setTitle("Öltöztetés");
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource("scanner.png")).getImage());		
		
		customWristToDraw = new CustomWristbandLabel();
		customWristToDraw.setVisible(false);
		
		checkLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("error_icon2.png")));
		checkLabel.setVisible(false);
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0, 0, 1600, 900);
		setLocationRelativeTo(null);		
				
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		receiverDrawPanel = new DressingDrawPanel();
		receiverDrawPanel.setBounds(0, 0, 1584, 861);
		contentPane.add(receiverDrawPanel);		

		JLabel lblUserNameLabel = new JLabel(model.getCurrentUser().getLastName() + " " + model.getCurrentUser().getFirstName());
		lblUserNameLabel.setBounds(1285, 5, 240, 40);
		lblUserNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUserNameLabel.setForeground(Color.WHITE);
		lblUserNameLabel.setFont(myFont);
		receiverDrawPanel.add(lblUserNameLabel);
		
		menuIconLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("menu_icon.png")));
		menuIconLabel.setBounds(1535, 5, 45, 40);
		receiverDrawPanel.add(menuIconLabel);
		
		rainyIconLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("rainy_active.png")));
		rainyIconLabel.setBounds(772, 50, 40, 40);
		rainyIconLabel.setToolTipText("Esõs idõre váltás");
		rainyIconLabel.setVisible(false);
		receiverDrawPanel.add(rainyIconLabel);
		
		rainyElapsedTimeLabel = new JLabel("");
		rainyElapsedTimeLabel.setBounds(822, 50, 200, 40);		
		rainyElapsedTimeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		rainyElapsedTimeLabel.setForeground(PASTELBLUE);
		rainyElapsedTimeLabel.setFont(myFont);		
		receiverDrawPanel.add(rainyElapsedTimeLabel);		
		
		signOutLabel = new JLabel("Kijelentkezés");
		signOutLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		signOutLabel.setBounds(1440, 54, 140, 34);
		signOutLabel.setFont(myFont);
		signOutLabel.setForeground(Color.BLACK);
		signOutLabel.setOpaque(true);
		signOutLabel.setBorder(new LineBorder(Color.DARK_GRAY, 1));
		signOutLabel.setVisible(false);
		receiverDrawPanel.add(signOutLabel);
		
		JLabel lblUserNameLabel_1 = new JLabel("Karszalagok aktiválása / lezárása");
		lblUserNameLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblUserNameLabel_1.setForeground(DARKBROWN);
		lblUserNameLabel_1.setFont(new Font("Verdana", Font.BOLD, 26));
		lblUserNameLabel_1.setBounds(60, 5, 684, 40);
		receiverDrawPanel.add(lblUserNameLabel_1);
		
		barCodeReceiverTextField = new JTextField("");
		barCodeReceiverTextField.setHorizontalAlignment(SwingConstants.CENTER);
		barCodeReceiverTextField.setBounds(60, 150, 490, 40);
		barCodeReceiverTextField.setFont(myFont);
		barCodeReceiverTextField.setBorder(new LineBorder(DARKBROWN , 1));
		//barCodeReceiverTextField.setEditable(false);
		receiverDrawPanel.add(barCodeReceiverTextField);
		
		barCodeReceiverButton = new JButton("Ellenõrzés");
		barCodeReceiverButton.setBounds(570, 150, 172, 40);
		barCodeReceiverButton.setBackground(ORANGE);
		barCodeReceiverButton.setBorder(new LineBorder(DARKBROWN, 2));
		barCodeReceiverButton.setForeground(Color.BLACK);
		barCodeReceiverButton.setFont(myFont);
		barCodeReceiverButton.setToolTipText("Karszalag számának ellenõrzése");
		//???
		barCodeReceiverButton.setFocusable(false);
		//barCodeReceiverButton.setEnabled(false);
		receiverDrawPanel.add(barCodeReceiverButton);
		
		wristBandCheckPanel = new JPanel();
		wristBandCheckPanel.setPreferredSize(new Dimension(975, 380));
		wristBandCheckPanel.setAutoscrolls(true);
		wristBandCheckPanel.setBounds(60, 230, 900, 120);
		wristBandCheckPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
		wristBandCheckPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30 , 30));
		receiverDrawPanel.add(wristBandCheckPanel);

		wristBandCheckPanel.add(customWristToDraw);
		wristBandCheckPanel.add(checkLabel);

		exceptionLabel = new JLabel("");
		exceptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		exceptionLabel.setBounds(60, 100, 610, 30);
		exceptionLabel.setForeground(Color.RED);
		exceptionLabel.setFont(myScriptFont);
		exceptionLabel.setVisible(false);
		receiverDrawPanel.add(exceptionLabel);
		
		wristbandClosingTimeLabel = new JLabel("");
		wristbandClosingTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		wristbandClosingTimeLabel.setBounds(917, 653, 40, 30);
		//wristbandClosingTimeLabel.setBackground(PASTELRED);
		//wristbandClosingTimeLabel.setOpaque(true);
		//wristbandClosingTimeLabel.setBorder(new LineBorder(Color.BLACK, 1));
		wristbandClosingTimeLabel.setForeground(CHILLIRED);
		wristbandClosingTimeLabel.setFont(myScriptFont);
		wristbandClosingTimeLabel.setVisible(false);		
		receiverDrawPanel.add(wristbandClosingTimeLabel);
				
		wristbandClosingButton = new JButton("Lezárás");
		wristbandClosingButton.setForeground(Color.WHITE);
		wristbandClosingButton.setFont(myFont);
		wristbandClosingButton.setBorder(new LineBorder(DARKBROWN, 2));
		wristbandClosingButton.setBackground(new Color(251, 182, 0));
		wristbandClosingButton.setBounds(785, 680, 172, 40);
		wristbandClosingButton.setEnabled(false);
		//wristbandClosingButton.setFocusable(false);
		wristbandClosingButton.setToolTipText("A lekéredzett szalag lezárása");
		receiverDrawPanel.add(wristbandClosingButton);
		
		currentTimeLabel = new JLabel("");
		currentTimeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		currentTimeLabel.setForeground(Color.WHITE);
		currentTimeLabel.setFont(myFont);
		currentTimeLabel.setBounds(10, 55, 180, 30);
		receiverDrawPanel.add(currentTimeLabel);
			
		wristbandDataPanel = new JPanel();
		wristbandDataPanel.setBounds(60, 400, 900, 240);
		wristbandDataPanel.setBackground(ORANGE);
		wristbandDataPanel.setLayout(null);
		wristbandDataPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
		receiverDrawPanel.add(wristbandDataPanel);		
		
		JLabel lblUserNameLabel_1_2 = new JLabel("Aktiválás");
		lblUserNameLabel_1_2.setBounds(60, 65, 180, 26);		
		lblUserNameLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserNameLabel_1_2.setForeground(Color.WHITE);
		lblUserNameLabel_1_2.setFont(myFont);
		wristbandDataPanel.add(lblUserNameLabel_1_2);
		
		JLabel lblUserNameLabel_1_3 = new JLabel("Lezárás");
		lblUserNameLabel_1_3.setBounds(270, 65, 180, 30);		
		lblUserNameLabel_1_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserNameLabel_1_3.setForeground(Color.WHITE);
		lblUserNameLabel_1_3.setFont(myFont);
		wristbandDataPanel.add(lblUserNameLabel_1_3);
		
		JLabel lblUserNameLabel_1_4 = new JLabel("Eltelt idõ");
		lblUserNameLabel_1_4.setBounds(480, 65, 180, 30);		
		lblUserNameLabel_1_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserNameLabel_1_4.setForeground(Color.WHITE);
		lblUserNameLabel_1_4.setFont(myFont);
		wristbandDataPanel.add(lblUserNameLabel_1_4);
		
		JLabel lblUserNameLabel_1_5 = new JLabel("Hátralévõ idõ");
		lblUserNameLabel_1_5.setBounds(690, 65, 180, 30);	
		lblUserNameLabel_1_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserNameLabel_1_5.setForeground(Color.WHITE);
		lblUserNameLabel_1_5.setFont(myFont);
		wristbandDataPanel.add(lblUserNameLabel_1_5);
		
		startDateLabel = new JLabel("");
		startDateLabel.setBounds(60, 125, 180, 30);		
		startDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		//startDateLabel.setForeground(Color.WHITE);
		startDateLabel.setForeground(Color.BLACK);
		startDateLabel.setFont(myFont);
		wristbandDataPanel.add(startDateLabel);
		
		stopDateLabel = new JLabel("");
		stopDateLabel.setBounds(270, 125, 180, 30);		
		stopDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		stopDateLabel.setForeground(Color.BLACK);
		//stopDateLabel.setForeground(Color.WHITE);
		stopDateLabel.setFont(myFont);
		wristbandDataPanel.add(stopDateLabel);
		
		spentTimeLabel = new JLabel("");
		spentTimeLabel.setBounds(480, 125, 180, 30);		
		spentTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		spentTimeLabel.setForeground(Color.BLACK);
		//spentTimeLabel.setForeground(Color.WHITE);
		spentTimeLabel.setFont(myFont);
		wristbandDataPanel.add(spentTimeLabel);
		
		remainingTimeLabel = new JLabel("");
		remainingTimeLabel.setBounds(690, 125, 180, 30);		
		remainingTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		remainingTimeLabel.setForeground(Color.BLACK);
		//remainingTimeLabel.setForeground(Color.WHITE);
		remainingTimeLabel.setFont(myFont);
		wristbandDataPanel.add(remainingTimeLabel);
		
		badWeatherElapsedTimeLabel = new JLabel("Pálya zárva volt: ");
		badWeatherElapsedTimeLabel.setBounds(250, 15, 600, 30);		
		badWeatherElapsedTimeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		badWeatherElapsedTimeLabel.setForeground(PASTELBLUE);
		badWeatherElapsedTimeLabel.setFont(myFont);
		badWeatherElapsedTimeLabel.setVisible(false);
		wristbandDataPanel.add(badWeatherElapsedTimeLabel);
		
		extraChargesLabel = new JLabel("");
		extraChargesLabel.setBounds(275, 190, 528, 30);		
		extraChargesLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		extraChargesLabel.setForeground(Color.RED);
		extraChargesLabel.setFont(new Font("Verdana", Font.BOLD, 21));
		wristbandDataPanel.add(extraChargesLabel);
		
		checkTimeLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("ok_icon2.png")));
		checkTimeLabel.setBounds(820, 175, 64, 60);
		wristbandDataPanel.add(checkTimeLabel);		
		
		JLabel lblUserNameLabel_1_1 = new JLabel("Karszalag adatai");
		lblUserNameLabel_1_1.setBounds(2, 2, 180, 30);
		wristbandDataPanel.add(lblUserNameLabel_1_1);
		lblUserNameLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserNameLabel_1_1.setForeground(Color.BLACK);
		//lblUserNameLabel_1_1.setForeground(DARKBROWN);
		lblUserNameLabel_1_1.setFont(myFont);
		checkTimeLabel.setVisible(false);
		
		//Statistics		
		JPanel statisticsPanel = new JPanel();
		statisticsPanel.setBounds(1070, 145, 470, 340);
		statisticsPanel.setBackground(BEIGE);
		statisticsPanel.setLayout(null);
		receiverDrawPanel.add(statisticsPanel);
		
		JLabel lblUserNameLabel_1_1_1 = new JLabel("Statisztikák");
		lblUserNameLabel_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblUserNameLabel_1_1_1.setForeground(GRAPHITE);
		lblUserNameLabel_1_1_1.setFont(mySmallNumberFont); 
		lblUserNameLabel_1_1_1.setBounds(15, 5, 129, 30);
		statisticsPanel.add(lblUserNameLabel_1_1_1);
		
		JLabel lblUserNameLabel_1_2_9 = new JLabel("Öltöztetés");		
		lblUserNameLabel_1_2_9.setHorizontalAlignment(SwingConstants.CENTER);		
		lblUserNameLabel_1_2_9.setForeground(GRAPHITE);
		lblUserNameLabel_1_2_9.setFont(mySmallFont);
		lblUserNameLabel_1_2_9.setBounds(33, 72, 80, 26);
		statisticsPanel.add(lblUserNameLabel_1_2_9);
				
		JLabel lblUserNameLabel_1_2_1 = new JLabel("Pályán");
		lblUserNameLabel_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserNameLabel_1_2_1.setForeground(GRAPHITE);
		lblUserNameLabel_1_2_1.setFont(mySmallFont);
		lblUserNameLabel_1_2_1.setBounds(133, 70, 80, 26);
		statisticsPanel.add(lblUserNameLabel_1_2_1);
		
		JLabel lblUserNameLabel_1_2_2 = new JLabel("> 2 óra");
		lblUserNameLabel_1_2_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserNameLabel_1_2_2.setForeground(GRAPHITE);
		lblUserNameLabel_1_2_2.setFont(mySmallFont);
		lblUserNameLabel_1_2_2.setBounds(248, 77, 80, 26);
		statisticsPanel.add(lblUserNameLabel_1_2_2);
		
		JLabel lblUserNameLabel_1_2_3 = new JLabel("> 3 óra");
		lblUserNameLabel_1_2_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserNameLabel_1_2_3.setForeground(GRAPHITE);
		lblUserNameLabel_1_2_3.setFont(mySmallFont);
		lblUserNameLabel_1_2_3.setBounds(348, 70, 80, 26);
		statisticsPanel.add(lblUserNameLabel_1_2_3);
		
		JLabel lblUserNameLabel_1_2_6 = new JLabel("Fehér");
		lblUserNameLabel_1_2_6.setForeground(GRAPHITE);
		lblUserNameLabel_1_2_6.setFont(mySmallFont);
		lblUserNameLabel_1_2_6.setBounds(61, 200, 100, 26);
		statisticsPanel.add(lblUserNameLabel_1_2_6);
		
		JLabel lblUserNameLabel_1_2_6_2 = new JLabel("pálya");
		lblUserNameLabel_1_2_6_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserNameLabel_1_2_6_2.setForeground(GRAPHITE);
		lblUserNameLabel_1_2_6_2.setFont(mySmallFont);
		lblUserNameLabel_1_2_6_2.setBounds(67, 216, 100, 26);
		statisticsPanel.add(lblUserNameLabel_1_2_6_2);
		
		JLabel lblUserNameLabel_1_2_5 = new JLabel("<html>Sárga <br> pálya</html>");
		lblUserNameLabel_1_2_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserNameLabel_1_2_5.setForeground(GRAPHITE);
		lblUserNameLabel_1_2_5.setFont(mySmallFont);
		lblUserNameLabel_1_2_5.setBounds(183, 195, 90, 42);
		statisticsPanel.add(lblUserNameLabel_1_2_5);
		
		JLabel lblUserNameLabel_1_2_4 = new JLabel("Piros");
		lblUserNameLabel_1_2_4.setForeground(GRAPHITE);
		lblUserNameLabel_1_2_4.setFont(mySmallFont);
		lblUserNameLabel_1_2_4.setBounds(315, 200, 100, 26);
		statisticsPanel.add(lblUserNameLabel_1_2_4);
		
		JLabel lblUserNameLabel_1_2_4_2 = new JLabel("pálya");
		lblUserNameLabel_1_2_4_2.setForeground(GRAPHITE);
		lblUserNameLabel_1_2_4_2.setFont(mySmallFont);
		lblUserNameLabel_1_2_4_2.setBounds(348, 215, 100, 26);
		statisticsPanel.add(lblUserNameLabel_1_2_4_2);
		
		dressingNumberLabel = new JLabel("0");
		dressingNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dressingNumberLabel.setForeground(GRAPHITE);
		dressingNumberLabel.setFont(mySmallNumberFont);
		dressingNumberLabel.setBounds(33, 112, 80, 26);
		statisticsPanel.add(dressingNumberLabel);
		
		activeNumberLabel = new JLabel("0");
		activeNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
		activeNumberLabel.setForeground(GRAPHITE);
		activeNumberLabel.setFont(mySmallNumberFont);
		activeNumberLabel.setBounds(133, 112, 80, 26);
		statisticsPanel.add(activeNumberLabel);
		
		moreThen2HoursLabel = new JLabel("0");
		moreThen2HoursLabel.setHorizontalAlignment(SwingConstants.CENTER);
		moreThen2HoursLabel.setForeground(GRAPHITE);
		moreThen2HoursLabel.setFont(mySmallNumberFont);
		moreThen2HoursLabel.setBounds(248, 118, 80, 26);
		statisticsPanel.add(moreThen2HoursLabel);
		
		moreThen3HoursLabel = new JLabel("0");
		moreThen3HoursLabel.setHorizontalAlignment(SwingConstants.CENTER);
		moreThen3HoursLabel.setForeground(GRAPHITE);
		moreThen3HoursLabel.setFont(mySmallNumberFont);
		moreThen3HoursLabel.setBounds(348, 114, 80, 26);
		statisticsPanel.add(moreThen3HoursLabel);
		
		whiteTrackCounterLabel = new JLabel("0");
		whiteTrackCounterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		whiteTrackCounterLabel.setForeground(GRAPHITE);
		whiteTrackCounterLabel.setFont(mySmallNumberFont);
		whiteTrackCounterLabel.setBounds(61, 250, 90, 26);
		statisticsPanel.add(whiteTrackCounterLabel);
		
		yellowTrackCounterLabel = new JLabel("0");
		yellowTrackCounterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		yellowTrackCounterLabel.setForeground(GRAPHITE);
		yellowTrackCounterLabel.setFont(mySmallNumberFont);
		yellowTrackCounterLabel.setBounds(183, 245, 90, 26);
		statisticsPanel.add(yellowTrackCounterLabel);
		
		redTrackCounterLabel = new JLabel("0");
		redTrackCounterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		redTrackCounterLabel.setForeground(GRAPHITE);
		redTrackCounterLabel.setFont(mySmallNumberFont);
		redTrackCounterLabel.setBounds(318, 250, 80, 26);
		statisticsPanel.add(redTrackCounterLabel);
		
		JLabel pinkStickyLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("stickies/sticky_pink.png")));
		pinkStickyLabel.setBounds(20, 53, 100, 100);
		pinkStickyLabel.setToolTipText("Szalag aktiválásra (öltöztetésre) várók száma");
		statisticsPanel.add(pinkStickyLabel);
		
		JLabel yellowStickyLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("stickies/sticky_grass.png")));
		yellowStickyLabel.setBounds(127, 53, 100, 100);
		yellowStickyLabel.setToolTipText("Jelenleg aktív szalagok száma");
		statisticsPanel.add(yellowStickyLabel);
		
		JLabel greenStickyLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("stickies/sticky_blue.png")));
		greenStickyLabel.setBounds(238, 55, 100, 100);
		greenStickyLabel.setToolTipText("2 óránál régebbi fehér szalagok száma");
		statisticsPanel.add(greenStickyLabel);

		JLabel red2StickyLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("stickies/sticky_yellow.png")));
		red2StickyLabel.setBounds(338, 52, 100, 100);
		red2StickyLabel.setToolTipText("3 óránál régebbi sárga és piros szalagok száma");
		statisticsPanel.add(red2StickyLabel);
		
		
		JLabel whiteStickyLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("stickies/sticky_white.png")));
		whiteStickyLabel.setBounds(50, 182, 100, 100);
		whiteStickyLabel.setToolTipText("Fehér pályán lévõ vendégek száma");
		statisticsPanel.add(whiteStickyLabel);
		
		JLabel redStickyLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("stickies/sticky_yellow.png")));
		redStickyLabel.setBounds(175, 175, 100, 100);
		redStickyLabel.setToolTipText("Sárga pályán lévõ vendégek száma");
		statisticsPanel.add(redStickyLabel);
		
		JLabel blueStickyLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("stickies/sticky_red.png")));
		blueStickyLabel.setBounds(305, 181, 100, 100);
		blueStickyLabel.setToolTipText("Piros pályán lévõ vendégek száma");
		statisticsPanel.add(blueStickyLabel);

		JLabel bunny = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("bunny2.png")));
		bunny.setBounds(2, 755, 80, 80);
		bunny.setVisible(true);
		receiverDrawPanel.add(bunny);
	}
	
	public void changeCheckTimeLabelIcon(boolean ok) {
		if(ok){
			checkTimeLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ok_icon2.png")));
		}else {
			checkTimeLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("error_icon.png")));
		}		
	}	

	@Override
	public void run() {
		
	}

	@Override
	public void hideExceptions() {
		
	}
		
	public JLabel getMenuIconLabel() {
		return menuIconLabel;
	}
	
	public JLabel getSignOutLabel() {
		return signOutLabel;
	}
	
	public JLabel getRainyIconLabel() {
		return rainyIconLabel;
	}


	public JLabel getRainyElapsedTimeLabel() {
		return rainyElapsedTimeLabel;
	}

	public DressingDrawPanel getReceiverDrowPanel() {
		return receiverDrawPanel;
	}

	public JButton getBarCodeReceiverButton() {
		return barCodeReceiverButton;
	}

	public JButton getWristbandClosingButton() {
		return wristbandClosingButton;
	}

	public JTextField getBarCodeReceiverTextField() {
		return barCodeReceiverTextField;
	}

	public JLabel getExceptionLabel() {
		return exceptionLabel;
	}
	
	public JLabel getWristbandClosingTimeLabel() {
		return wristbandClosingTimeLabel;
	}

	public JLabel getCheckLabel() {
		return checkLabel;
	}

	public JLabel getCheckTimeLabel() {
		return checkTimeLabel;
	}

	public CustomWristbandLabel getCustomWristToDraw() {
		return customWristToDraw;
	}

	public JLabel getStartDateLabel() {
		return startDateLabel;
	}

	public JLabel getStopDateLabel() {
		return stopDateLabel;
	}

	public JLabel getSpentTimeLabel() {
		return spentTimeLabel;
	}
	
	public JLabel getBadWeatherElapsedTimeLabel() {
		return badWeatherElapsedTimeLabel;
	}

	public JLabel getRemainingTimeLabel() {
		return remainingTimeLabel;
	}

	public JLabel getExtraChargesLabel() {
		return extraChargesLabel;
	}

	public JLabel getCurrentTimeLabel() {
		return currentTimeLabel;
	}

	public JLabel getActiveNumberLabel() {
		return activeNumberLabel;
	}
	
	public JLabel getDressingNumberLabel() {
		return dressingNumberLabel;
	}

	public JLabel getMoreThen2HoursLabel() {
		return moreThen2HoursLabel;
	}

	public JLabel getMoreThen3HoursLabel() {
		return moreThen3HoursLabel;
	}

	public JLabel getWhiteTrackCounterLabel() {
		return whiteTrackCounterLabel;
	}

	public JLabel getYellowTrackCounterLabel() {
		return yellowTrackCounterLabel;
	}

	public JLabel getRedTrackCounterLabel() {
		return redTrackCounterLabel;
	}
}
