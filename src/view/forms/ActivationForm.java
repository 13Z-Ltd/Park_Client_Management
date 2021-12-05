package view.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import model.Model;
import model.PreparedWristband;
import view.CustomWristbandLabel;
import view.drawpanels.ActivationDrawPanel;

public class ActivationForm extends MainJFrameForm implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private Model model;
	private JPanel contentPane;
	private ActivationDrawPanel mainActivationPanel;
	private JPanel wristListPanel;
	private JScrollPane scrollFrame;	
	
	private JButton btnAddBarCodeButton;
	private JButton btnAcceptButton;
	
	private JLabel whiteCounterLabel;
	private JLabel yellowCounterLabel;
	private JLabel redCounterLabel;
	private JLabel whiteCalculationLabel;
	private JLabel yellowCalculationLabel;
	private JLabel redCalculationLabel;
	
	private JLabel whiteWristImageLabel;
	private JLabel yellowWristImageLabel;
	private JLabel redWristImageLabel;	
	private JLabel lblSumTotalLabel;
	
	private JLabel currentTimeLabel;
	private JLabel menuIconLabel;
	private JLabel signOutLabel;	
	private JLabel taxBaseLabel;
	private JLabel taxLabel;
	private JLabel discountAmountLabel;
	private JLabel discountSubtitleLabel;
	private JTextField barCodeAdderTextField;
	
	private JLabel whiteRegisteredNumberLabel;
	private JLabel yellowRegisteredNumberLabel;
	private JLabel redRegisteredNumberLabel;
	
	private JLabel availableChildSingingNumbersLabel;
	private JLabel availableSeniorSingingNumbersLabel;
	private JLabel activeWhiteNumbersLabel;
	private JLabel activeYellowNumbersLabel;
	private JLabel activeRedNumbersLabel;
	private JLabel singingStickyLabel;
	private JLabel singingStickyLabel2;
	private JLabel childSingingImageLabel;
	private JLabel seniorSingingImageLabel;
	
	private JLabel whiteStickyLabel;
	private JLabel yellowStickyLabel;
	private JLabel redStickyLabel;
	private JLabel lblstatisticInfoLabel3_2;
	private JLabel lblstatisticInfoLabel3_3;
	private JLabel lblstatisticInfoLabel3_4;
	
	private JLabel closingTimeLabel;
	private JLabel exceptionLabel;
	
	private JLabel weatherSwitchButtonLabel;
	private JLabel singingOrActiveSwitchLabel;

	private ImageIcon whiteNormalIcon = new ImageIcon(getClass().getClassLoader().getResource("white_tyvek_normal.png"));
	private ImageIcon whiteSelectedIcon = new ImageIcon(getClass().getClassLoader().getResource("white_tyvek_selected.png"));
	private ImageIcon yellowNormalIcon = new ImageIcon(getClass().getClassLoader().getResource("yellow_tyvek_normal.png"));
	private ImageIcon yellowSelectedIcon = new ImageIcon(getClass().getClassLoader().getResource("yellow_tyvek_selected.png"));
	private ImageIcon redNormalIcon = new ImageIcon(getClass().getClassLoader().getResource("red_tyvek_normal.png"));
	private ImageIcon redSelectedIcon = new ImageIcon(getClass().getClassLoader().getResource("red_tyvek_selected.png"));
	
	Color DARKBROWN = new Color(55, 45, 40);
	Color ORANGE = new Color(251, 182, 0);
	Color GRASSGREEN = new Color(150, 169, 56);	
	Color BEIGE = new Color(239, 231, 219);
	Color GRAPHITE = new Color(69, 73, 78);
	Color PASTELWHITE = new Color(241, 241, 241);
	Color MACAROON = new Color(249, 224, 119);
	Color PASTELRED = new Color(200, 78, 56);
		
	Font myFont = new Font("Verdana", Font.BOLD, 22);
	Font mySmallFont = new Font("Verdana", Font.PLAIN, 20);
	Font myScriptFont = new Font("Segoe Script", Font.BOLD, 15);
	
	/**
	 * Create the frame.
	 */
	public ActivationForm(Model model) {
		this.model = model;
		
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource("user.png")).getImage());
		
		setBackground(new Color(169, 169, 169));
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1600, 900);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel topBorderPanel = new JPanel();
		topBorderPanel.setBackground(DARKBROWN);
		topBorderPanel.setBounds(0, 0, 1584, 40);
		contentPane.add(topBorderPanel);
		topBorderPanel.setLayout(null);

		JPanel topBorderPanel2 = new JPanel();
		topBorderPanel2.setBackground(GRASSGREEN);
		topBorderPanel2.setBounds(0, 40, 1584, 40);
		contentPane.add(topBorderPanel2);
		topBorderPanel2.setLayout(null);
		
		currentTimeLabel = new JLabel("");
		currentTimeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		currentTimeLabel.setForeground(Color.WHITE);
		currentTimeLabel.setFont(mySmallFont);
		currentTimeLabel.setBounds(10, 0, 180, 40);
		topBorderPanel2.add(currentTimeLabel);
		
		JLabel lblUserNameLabel = new JLabel(model.getCurrentUser().getLastName() + " " + model.getCurrentUser().getFirstName());
		lblUserNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUserNameLabel.setForeground(Color.WHITE);
		lblUserNameLabel.setFont(mySmallFont);
		lblUserNameLabel.setBounds(1290, 0, 239, 40);
		topBorderPanel2.add(lblUserNameLabel);
		
		JLabel lblBorderLabel = new JLabel("Karszalagok kiadása");
		lblBorderLabel.setBounds(75, 0, 280, 40);
		lblBorderLabel.setForeground(SystemColor.textHighlightText);
		lblBorderLabel.setFont(myFont);
		topBorderPanel.add(lblBorderLabel);

		mainActivationPanel = new ActivationDrawPanel();
		mainActivationPanel.setBounds(0, 80, 1584, 753);
		contentPane.add(mainActivationPanel);
				
		whiteWristImageLabel = new JLabel(whiteNormalIcon);
		whiteWristImageLabel.setName("whiteWristImageLabel");
		whiteWristImageLabel.setBounds(70, 60, 180, 106);
		//whiteWristImageLabel.setToolTipText("Fehér szalag hozzáadása");
		mainActivationPanel.add(whiteWristImageLabel);		
		yellowWristImageLabel = new JLabel(yellowNormalIcon);
		yellowWristImageLabel.setName("yellowWristImageLabel");
		yellowWristImageLabel.setBounds(70, 190, 180, 106);
		//yellowWristImageLabel.setToolTipText("Sárga szalag hozzáadása");
		mainActivationPanel.add(yellowWristImageLabel);			
		redWristImageLabel = new JLabel(redNormalIcon);
		redWristImageLabel.setName("redWristImageLabel");
		redWristImageLabel.setBounds(70, 320, 180, 106);
		//redWristImageLabel.setToolTipText("Piros szalag hozzáadása");
		mainActivationPanel.add(redWristImageLabel);
		
		JLabel lblBorderLabel2 = new JLabel("Összesen: ");
		lblBorderLabel2.setBounds(112, 518, 140, 43);
		lblBorderLabel2.setForeground(Color.WHITE);
		lblBorderLabel2.setFont(myFont);
		mainActivationPanel.add(lblBorderLabel2);
		
		lblSumTotalLabel = new JLabel("");
		lblSumTotalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblSumTotalLabel.setForeground(Color.WHITE);
		lblSumTotalLabel.setFont(myFont);
		lblSumTotalLabel.setBounds(388, 518, 129, 43);
		mainActivationPanel.add(lblSumTotalLabel);
		
		
		wristListPanel = new JPanel();
		wristListPanel.setPreferredSize(new Dimension(975, 380));
		scrollFrame = new JScrollPane(wristListPanel);
		wristListPanel.setAutoscrolls(true);
		scrollFrame.setPreferredSize(new Dimension(1000, 400));
		scrollFrame.setBounds(530, 70, 1000, 400);
		scrollFrame.setBorder(new BevelBorder(BevelBorder.RAISED));
		mainActivationPanel.add(scrollFrame);		
		
		btnAcceptButton = new JButton("Véglegesítés");
		btnAcceptButton.setBounds(300, 645, 206, 35);
		btnAcceptButton.setForeground(SystemColor.textHighlightText);
		btnAcceptButton.setBorder(new LineBorder(Color.GRAY, 2));
		btnAcceptButton.setBackground(DARKBROWN);
		btnAcceptButton.setFont(myFont);
		btnAcceptButton.setEnabled(false);
		btnAcceptButton.setFocusPainted( false );
		mainActivationPanel.add(btnAcceptButton);
		
		whiteCounterLabel = new JLabel();
		whiteCounterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		whiteCounterLabel.setForeground(SystemColor.textHighlightText);
		whiteCounterLabel.setFont(myFont);
		whiteCounterLabel.setBounds(300, 90, 90, 54);
		whiteCounterLabel.setBackground(ORANGE);
		whiteCounterLabel.setOpaque(true);
		//whiteCounterLabel.setToolTipText("+/- (egér görgõjével)");
		mainActivationPanel.add(whiteCounterLabel);
		
		yellowCounterLabel = new JLabel();
		yellowCounterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		yellowCounterLabel.setForeground(Color.WHITE);
		yellowCounterLabel.setFont(myFont);
		yellowCounterLabel.setBackground(ORANGE);
		yellowCounterLabel.setBounds(300, 218, 90, 54);
		yellowCounterLabel.setOpaque(true);
		mainActivationPanel.add(yellowCounterLabel);
		
		redCounterLabel = new JLabel();
		redCounterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		redCounterLabel.setForeground(Color.WHITE);
		redCounterLabel.setFont(myFont);
		redCounterLabel.setBackground(ORANGE);
		redCounterLabel.setBounds(300, 344, 90, 54);
		redCounterLabel.setOpaque(true);
		mainActivationPanel.add(redCounterLabel);
		
		JLabel lblWriteDb = new JLabel("db");
		lblWriteDb.setForeground(Color.WHITE);
		lblWriteDb.setBackground(ORANGE);
		lblWriteDb.setOpaque(true);
		lblWriteDb.setFont(myFont);
		lblWriteDb.setBounds(390, 90, 60, 54);
		mainActivationPanel.add(lblWriteDb);
		
		JLabel lblWriteDb_1 = new JLabel("db");
		lblWriteDb_1.setForeground(Color.WHITE);
		lblWriteDb_1.setBackground(ORANGE);
		lblWriteDb_1.setOpaque(true);
		lblWriteDb_1.setFont(myFont);
		lblWriteDb_1.setBounds(390, 218, 60, 54);
		mainActivationPanel.add(lblWriteDb_1);
		
		JLabel lblWriteDb_2 = new JLabel("db");
		lblWriteDb_2.setBackground(ORANGE);
		lblWriteDb_2.setForeground(Color.WHITE);
		lblWriteDb_2.setOpaque(true);
		lblWriteDb_2.setFont(myFont);
		lblWriteDb_2.setBounds(388, 344, 62, 54);
		mainActivationPanel.add(lblWriteDb_2);
		
		whiteCalculationLabel = new JLabel("");
		whiteCalculationLabel.setHorizontalAlignment(SwingConstants.LEFT);
		whiteCalculationLabel.setBounds(300, 155, 146, 20);
		whiteCalculationLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
		mainActivationPanel.add(whiteCalculationLabel);		
		yellowCalculationLabel = new JLabel("");
		yellowCalculationLabel.setHorizontalAlignment(SwingConstants.LEFT);
		yellowCalculationLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
		yellowCalculationLabel.setBounds(300, 283, 146, 20);
		mainActivationPanel.add(yellowCalculationLabel);		
		redCalculationLabel = new JLabel("");
		redCalculationLabel.setHorizontalAlignment(SwingConstants.LEFT);
		redCalculationLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
		redCalculationLabel.setBounds(300, 409, 146, 20);
		mainActivationPanel.add(redCalculationLabel);		
		
		JLabel nettoBruttoLabel = new JLabel("Adóalap                   ÁFA");
		nettoBruttoLabel.setHorizontalAlignment(SwingConstants.LEFT);
		nettoBruttoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		nettoBruttoLabel.setBounds(353, 595, 139, 20);
		mainActivationPanel.add(nettoBruttoLabel);
		
		taxBaseLabel = new JLabel("");
		taxBaseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		taxBaseLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
		taxBaseLabel.setBounds(330, 615, 93, 20);
		mainActivationPanel.add(taxBaseLabel);
		
		taxLabel = new JLabel("");
		taxLabel.setHorizontalAlignment(SwingConstants.CENTER);
		taxLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
		taxLabel.setBounds(424, 615, 93, 20);
		mainActivationPanel.add(taxLabel);

		discountSubtitleLabel = new JLabel("");
		discountSubtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		discountSubtitleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		discountSubtitleLabel.setBounds(400, 550, 100, 20);
		discountSubtitleLabel.setVisible(false);
		mainActivationPanel.add(discountSubtitleLabel);
		
		discountAmountLabel = new JLabel("");
		discountAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		discountAmountLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
		discountAmountLabel.setBounds(398, 570, 100, 20);
		mainActivationPanel.add(discountAmountLabel);
		
		JLabel lblBarNumberAdder = new JLabel("Vonalkód beolvasása:");
		lblBarNumberAdder.setHorizontalAlignment(SwingConstants.CENTER);
		lblBarNumberAdder.setForeground(Color.BLACK);
		lblBarNumberAdder.setFont(myFont);
		lblBarNumberAdder.setBounds(581, 502, 273, 40);
		mainActivationPanel.add(lblBarNumberAdder);
		
		//barCodeAdderTextField = new JTextField("1234567890000");
		barCodeAdderTextField = new JTextField("");
		barCodeAdderTextField.setHorizontalAlignment(SwingConstants.CENTER);
		barCodeAdderTextField.setBounds(864, 502, 486, 40);
		barCodeAdderTextField.setFont(myFont);	
		barCodeAdderTextField.setEnabled(false);
		mainActivationPanel.add(barCodeAdderTextField);
		
		btnAddBarCodeButton = new JButton("OK");
		btnAddBarCodeButton.setBounds(1360, 502, 172, 40);
		btnAddBarCodeButton.setBackground(ORANGE);
		btnAddBarCodeButton.setBorder(new LineBorder(Color.GRAY, 2));
		btnAddBarCodeButton.setFont(myFont);
		btnAddBarCodeButton.setFocusable(false);
		btnAddBarCodeButton.setEnabled(false);
		btnAddBarCodeButton.setToolTipText("Beolvasott vonalkód felvétele");
		mainActivationPanel.add(btnAddBarCodeButton);
		
		
		JPanel buttomBorderPanel2 = new JPanel();
		buttomBorderPanel2.setBackground(DARKBROWN);
		buttomBorderPanel2.setBounds(0, 831, 1584, 30);
		contentPane.add(buttomBorderPanel2);
		
		menuIconLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("menu_icon.png")));
		menuIconLabel.setBounds(1535, 0, 45, 40);
		topBorderPanel2.add(menuIconLabel);
		
		weatherSwitchButtonLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("fine_weather.png")));
		weatherSwitchButtonLabel.setBounds(740, -1, 110, 42);
		weatherSwitchButtonLabel.setToolTipText("Idõjárás kapcsoló"); //("Váltás napos/esõs idõ között");
		topBorderPanel2.add(weatherSwitchButtonLabel);
		
		signOutLabel = new JLabel("Kijelentkezés");
		signOutLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		signOutLabel.setBounds(1440, 2, 140, 34);
		signOutLabel.setFont(mySmallFont);
		signOutLabel.setForeground(Color.DARK_GRAY);
		signOutLabel.setBackground(BEIGE);
		signOutLabel.setBorder(new LineBorder(Color.DARK_GRAY, 1));
		signOutLabel.setOpaque(true);
		signOutLabel.setVisible(false);
		mainActivationPanel.add(signOutLabel);
		
		closingTimeLabel = new JLabel("19:00");
		closingTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		closingTimeLabel.setForeground(PASTELRED);	//setForeground(Color.WHITE);
		closingTimeLabel.setFont(new Font("Courier New", Font.BOLD, 19));
		//closingTimeLabel.setFont(new Font("Courier New", Font.BOLD, 19));
		closingTimeLabel.setBounds(1458, 600, 109, 55);
		closingTimeLabel.setToolTipText("+/- (egér gombjaival és görgõjével)");
		mainActivationPanel.add(closingTimeLabel);
		
		JLabel lblclosingTimeLabel = new JLabel("Zárás");
		lblclosingTimeLabel.setBounds(1472, 565, 70, 40);
		lblclosingTimeLabel.setFont(myScriptFont);
		lblclosingTimeLabel.setForeground(GRAPHITE);
		mainActivationPanel.add(lblclosingTimeLabel);
		
		/*
		closingTimeLabel = new JLabel("_"); //("10:00");
		closingTimeLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		closingTimeLabel.setForeground(Color.WHITE);
		closingTimeLabel.setFont(myScriptFont);
		closingTimeLabel.setBounds(1462, 592, 75, 62);
		closingTimeLabel.setToolTipText("+/- (egér gombjaival és görgõjével)");
		mainActivationPanel.add(closingTimeLabel);
		 */
		
		childSingingImageLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("child_singing.png")));
		childSingingImageLabel.setBounds(665, 585, 60, 60);
		mainActivationPanel.add(childSingingImageLabel);
		
		seniorSingingImageLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("senior_singing.png")));
		seniorSingingImageLabel.setBounds(795, 578, 80, 80);
		mainActivationPanel.add(seniorSingingImageLabel);
		
		availableChildSingingNumbersLabel = new JLabel("_");
		availableChildSingingNumbersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		availableChildSingingNumbersLabel.setBounds(665, 651, 60, 26);
		availableChildSingingNumbersLabel.setFont(myScriptFont);
		availableChildSingingNumbersLabel.setForeground(GRAPHITE);
		mainActivationPanel.add(availableChildSingingNumbersLabel);
		availableSeniorSingingNumbersLabel = new JLabel("_");
		availableSeniorSingingNumbersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		availableSeniorSingingNumbersLabel.setBounds(795, 663, 80, 26);
		availableSeniorSingingNumbersLabel.setFont(myScriptFont);
		availableSeniorSingingNumbersLabel.setForeground(GRAPHITE);
		mainActivationPanel.add(availableSeniorSingingNumbersLabel);
		
		lblstatisticInfoLabel3_2 = new JLabel("Fehér");
		lblstatisticInfoLabel3_2.setForeground(GRAPHITE);
		lblstatisticInfoLabel3_2.setFont(myScriptFont);
		lblstatisticInfoLabel3_2.setBounds(670, 595, 80, 20);
		lblstatisticInfoLabel3_2.setVisible(false);
		mainActivationPanel.add(lblstatisticInfoLabel3_2);
		lblstatisticInfoLabel3_3 = new JLabel("Sárga");
		lblstatisticInfoLabel3_3.setForeground(GRAPHITE);
		lblstatisticInfoLabel3_3.setFont(myScriptFont);
		lblstatisticInfoLabel3_3.setBounds(795, 595, 80, 20);
		lblstatisticInfoLabel3_3.setVisible(false);
		mainActivationPanel.add(lblstatisticInfoLabel3_3);
		lblstatisticInfoLabel3_4 = new JLabel("Piros");
		lblstatisticInfoLabel3_4.setHorizontalAlignment(SwingConstants.LEFT);
		lblstatisticInfoLabel3_4.setForeground(Color.BLACK);
		lblstatisticInfoLabel3_4.setFont(myScriptFont);
		lblstatisticInfoLabel3_4.setBounds(920, 600, 80, 20);
		lblstatisticInfoLabel3_4.setVisible(false);
		mainActivationPanel.add(lblstatisticInfoLabel3_4);
		
		activeWhiteNumbersLabel = new JLabel("__");
		activeWhiteNumbersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		activeWhiteNumbersLabel.setBounds(663, 630, 80, 26);
		activeWhiteNumbersLabel.setFont(myScriptFont);
		activeWhiteNumbersLabel.setForeground(GRAPHITE);
		activeWhiteNumbersLabel.setVisible(false);
		mainActivationPanel.add(activeWhiteNumbersLabel);
		activeYellowNumbersLabel = new JLabel("__");
		activeYellowNumbersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		activeYellowNumbersLabel.setBounds(783, 630, 80, 26);
		activeYellowNumbersLabel.setFont(myScriptFont);
		activeYellowNumbersLabel.setForeground(GRAPHITE);
		activeYellowNumbersLabel.setVisible(false);
		mainActivationPanel.add(activeYellowNumbersLabel);
		activeRedNumbersLabel = new JLabel("__");
		activeRedNumbersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		activeRedNumbersLabel.setBounds(903, 635, 80, 26);
		activeRedNumbersLabel.setFont(myScriptFont);
		activeRedNumbersLabel.setForeground(Color.BLACK);
		activeRedNumbersLabel.setVisible(false);
		mainActivationPanel.add(activeRedNumbersLabel);
		
		singingStickyLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("stickies/yellow_sticky_115.png")));
		singingStickyLabel.setBounds(643, 565, 115, 115);
		singingStickyLabel.setToolTipText("Gyerek beülõk száma");
		mainActivationPanel.add(singingStickyLabel);
		
		singingStickyLabel2 = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("stickies/blue_sticky_140.png")));
		singingStickyLabel2.setBounds(767, 555, 140, 140);
		singingStickyLabel2.setToolTipText("Felnõtt beülõk száma");
		mainActivationPanel.add(singingStickyLabel2);
		
		singingOrActiveSwitchLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("switch_left.png")));
		singingOrActiveSwitchLabel.setBounds(570, 580, 50, 30);
		singingOrActiveSwitchLabel.setToolTipText("Aktív szalagok megjelenítése");
		mainActivationPanel.add(singingOrActiveSwitchLabel);
		
		whiteStickyLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("stickies/sticky_white.png")));
		whiteStickyLabel.setBounds(653, 576, 100, 100);
		whiteStickyLabel.setToolTipText("Rendelkezésre álló fehér szalagok száma");
		whiteStickyLabel.setVisible(false);
		mainActivationPanel.add(whiteStickyLabel);
		
		yellowStickyLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("stickies/sticky_yellow2.png")));
		yellowStickyLabel.setBounds(773, 575, 100, 100);
		yellowStickyLabel.setToolTipText("Rendelkezésre álló sárga szalagok száma");
		yellowStickyLabel.setVisible(false);
		mainActivationPanel.add(yellowStickyLabel);
		
		redStickyLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("stickies/sticky_red.png")));
		redStickyLabel.setBounds(893, 576, 100, 100);
		redStickyLabel.setToolTipText("Rendelkezésre álló piros szalagok száma");
		redStickyLabel.setVisible(false);
		mainActivationPanel.add(redStickyLabel);
		
		/*
		JLabel grassStickyLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("stickies/sticky_grass.png")));
		grassStickyLabel.setBounds(1335, 550, 100, 100);
		mainActivationPanel.add(grassStickyLabel);
		*/
		
		/*
		JLabel alarmClockLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("alarm_clock.png")));
		alarmClockLabel.setBounds(1248, 0, 109, 70);
		alarmClockLabel.setToolTipText("Napi zárás idõpontja:");
		mainActivationPanel.add(alarmClockLabel);
		
		JLabel alarmClockLabel7 = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("alarm_clock_5.png")));
		alarmClockLabel7.setBounds(1134, 20, 104, 50);
		alarmClockLabel7.setToolTipText("Napi zárás idõpontja:");
		mainActivationPanel.add(alarmClockLabel7);
		 */
		
		JLabel alarmClockLabel6 = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("alarm_clock_4.png")));
		alarmClockLabel6.setBounds(1476, 600, 72, 50);
		alarmClockLabel6.setToolTipText("Napi zárás idõpontja:");
		mainActivationPanel.add(alarmClockLabel6);		
		
		JLabel whiteStickyLabel_2 = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("stickies/sticky_yellow2.png"))); //"stickies/sticky_white.png
		whiteStickyLabel_2.setBounds(1463, 570, 100, 100);
		//whiteStickyLabel_2.setVisible(false);
		mainActivationPanel.add(whiteStickyLabel_2);
		
		
		JLabel lblstatisticInfoLabel = new JLabel("Fehér");
		lblstatisticInfoLabel.setBounds(20, 463, 50, 26);
		mainActivationPanel.add(lblstatisticInfoLabel);
		lblstatisticInfoLabel.setFont(myScriptFont);
		lblstatisticInfoLabel.setForeground(PASTELWHITE);		
		whiteRegisteredNumberLabel = new JLabel("__");
		whiteRegisteredNumberLabel.setBounds(70, 463, 60, 26);
		mainActivationPanel.add(whiteRegisteredNumberLabel);
		whiteRegisteredNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
		whiteRegisteredNumberLabel.setFont(myScriptFont);
		whiteRegisteredNumberLabel.setForeground(PASTELWHITE);
		whiteRegisteredNumberLabel.setToolTipText("Rendelkezésre álló fehér szalagok száma");
		
		JLabel lblstatisticInfoLabel2 = new JLabel("Sárga");
		lblstatisticInfoLabel2.setBounds(180, 463, 50, 26);
		mainActivationPanel.add(lblstatisticInfoLabel2);
		lblstatisticInfoLabel2.setForeground(MACAROON);
		lblstatisticInfoLabel2.setFont(myScriptFont);		
		yellowRegisteredNumberLabel = new JLabel("__");
		yellowRegisteredNumberLabel.setBounds(230, 463, 60, 26);
		mainActivationPanel.add(yellowRegisteredNumberLabel);
		yellowRegisteredNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
		yellowRegisteredNumberLabel.setFont(myScriptFont);
		yellowRegisteredNumberLabel.setForeground(MACAROON);
		yellowRegisteredNumberLabel.setToolTipText("Rendelkezésre álló sárga szalagok száma");
		
		JLabel lblstatisticInfoLabel3 = new JLabel("Piros");
		lblstatisticInfoLabel3.setBounds(340, 463, 50, 26);
		mainActivationPanel.add(lblstatisticInfoLabel3);
		lblstatisticInfoLabel3.setForeground(PASTELRED);
		lblstatisticInfoLabel3.setFont(myScriptFont);		
		redRegisteredNumberLabel = new JLabel("__");
		redRegisteredNumberLabel.setBounds(390, 463, 60, 26);
		mainActivationPanel.add(redRegisteredNumberLabel);
		redRegisteredNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
		redRegisteredNumberLabel.setFont(myScriptFont);
		redRegisteredNumberLabel.setForeground(PASTELRED);
		redRegisteredNumberLabel.setToolTipText("Rendelkezésre álló piros szalagok száma");
		
		exceptionLabel = new JLabel("");
		exceptionLabel.setHorizontalAlignment(SwingConstants.LEFT);
		exceptionLabel.setForeground(PASTELRED);
		exceptionLabel.setFont(new Font("Segoe Script", Font.BOLD, 17));
		exceptionLabel.setBounds(690, 20, 700, 30);
		mainActivationPanel.add(exceptionLabel);
		
		JLabel bunny = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("bunny2.png")));
		bunny.setBounds(2, 657, 80, 80);
		bunny.setVisible(true);
		mainActivationPanel.add(bunny);

		JLabel train = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("little_train.png")));
		train.setBounds(1150, 618, 264, 80);
		train.setVisible(true);
		mainActivationPanel.add(train);
	}
	
	public void changeIconSize(JLabel source) {
		if(source == whiteWristImageLabel) {
			if (source.getWidth() == 180) {			
				source.setIcon(whiteSelectedIcon);
				source.setBounds(55, 51, 210,124);				
			} else {
				source.setIcon(whiteNormalIcon);
				source.setBounds(70, 60, 180, 106);
			}				
		}
		if(source == yellowWristImageLabel) {
			if (source.getWidth() == 180) {			
				source.setIcon(yellowSelectedIcon);
				source.setBounds(55, 181, 210,124);
			} else {
				source.setIcon(yellowNormalIcon);
				source.setBounds(70, 190, 180, 106);
			}				
		}
		if(source == redWristImageLabel) {
			if (source.getWidth() == 180) {					
				source.setIcon(redSelectedIcon);
				source.setBounds(55, 311, 210,124);
			} else {
				source.setIcon(redNormalIcon);
				source.setBounds(70, 320, 180, 106);
			}				
		}
	}

	public void refreshWristPanel() {
		wristListPanel.removeAll();
		wristListPanel.repaint();
		wristListPanel.setPreferredSize(new Dimension(975, model.getListOfPreparedWristbands().getNumberOfAllWrists() * 66));
		
		ArrayList<PreparedWristband> wristbands = model.getListOfPreparedWristbands().getWhiteWristbands();
		for (PreparedWristband wristband : wristbands) {
			CustomWristbandLabel customWrist = new CustomWristbandLabel(wristband);
			wristListPanel.add(customWrist);
		}
		wristbands = model.getListOfPreparedWristbands().getYellowWristbands();
		for (PreparedWristband wristband : wristbands) {
			CustomWristbandLabel customWrist = new CustomWristbandLabel(wristband);
			wristListPanel.add(customWrist);
		}
		wristbands = model.getListOfPreparedWristbands().getRedWristbands();
		for (PreparedWristband wristband : wristbands) {
			CustomWristbandLabel customWrist = new CustomWristbandLabel(wristband);
			wristListPanel.add(customWrist);
		}
	}

	
	public JButton getBtnAddBarCodeButton() {
		return btnAddBarCodeButton;
	}

	public JButton getBtnAcceptButton() {
		return btnAcceptButton;
	}

	public JPanel getWristListPanel() {
		return wristListPanel;
	}
	
	///	
	public JLabel getLblSumTotalLabel() {
		return lblSumTotalLabel;
	}	

	public JLabel getWhiteWristImageLabel() {
		return whiteWristImageLabel;
	}
	
	public JLabel getYellowWristImageLabel() {
		return yellowWristImageLabel;
	}
	
	public JLabel getRedWristImageLabel() {
		return redWristImageLabel;
	}
	
	public JLabel getWhiteCounterLabel() {
		return whiteCounterLabel;
	}
	
	public JLabel getYellowCounterLabel() {
		return yellowCounterLabel;
	}

	public JLabel getRedCounterLabel() {
		return redCounterLabel;
	}

	public JLabel getWhiteCalculationLabel() {
		return whiteCalculationLabel;
	}

	public JLabel getYellowCalculationLabel() {
		return yellowCalculationLabel;
	}

	public JLabel getRedCalculationLabel() {
		return redCalculationLabel;
	}

	public JLabel getTaxBaseLabel() {
		return taxBaseLabel;
	}

	public JLabel getTaxLabel() {
		return taxLabel;
	}
	
	public JLabel getDiscountAmountLabel() {
		return discountAmountLabel;
	}

	public JLabel getDiscountSubtitleLabel() {
		return discountSubtitleLabel;
	}
	
	public JTextField getBarCodeAdderTextField() {
		return barCodeAdderTextField;
	}	

	public JLabel getMenuIconLabel() {
		return menuIconLabel;
	}

	public JLabel getSignOutLabel() {
		return signOutLabel;
	}
			
	public JLabel getCurrentTimeLabel() {
		return currentTimeLabel;
	}	

	public JLabel getClosingTimeLabel() {
		return closingTimeLabel;
	}	
	
	public JLabel getAvailableChildSingingNumbersLabel() {
		return availableChildSingingNumbersLabel;
	}

	public JLabel getAvailableSeniorSingingNumbersLabel() {
		return availableSeniorSingingNumbersLabel;
	}

	public JLabel getWhiteRegisteredNumberLabel() {
		return whiteRegisteredNumberLabel;
	}

	public JLabel getYellowRegisteredNumberLabel() {
		return yellowRegisteredNumberLabel;
	}

	public JLabel getRedRegisteredNumberLabel() {
		return redRegisteredNumberLabel;
	}		
	
	public JLabel getActiveWhiteNumbersLabel() {
		return activeWhiteNumbersLabel;
	}

	public JLabel getActiveYellowNumbersLabel() {
		return activeYellowNumbersLabel;
	}

	public JLabel getActiveRedNumbersLabel() {
		return activeRedNumbersLabel;
	}

	public JLabel getExceptionLabel() {
		return exceptionLabel;
	}

	public JLabel getWeatherSwitchButtonLabel() {
		return weatherSwitchButtonLabel;
	}	

	public JLabel getSingingOrActiveSwitchLabel() {
		return singingOrActiveSwitchLabel;
	}	

	public JLabel getSingingStickyLabel() {
		return singingStickyLabel;
	}

	public JLabel getSingingStickyLabel2() {
		return singingStickyLabel2;
	}

	public JLabel getChildSingingImageLabel() {
		return childSingingImageLabel;
	}

	public JLabel getSeniorSingingImageLabel() {
		return seniorSingingImageLabel;
	}	

	public JLabel getWhiteStickyLabel() {
		return whiteStickyLabel;
	}

	public JLabel getYellowStickyLabel() {
		return yellowStickyLabel;
	}

	public JLabel getRedStickyLabel() {
		return redStickyLabel;
	}

	public JLabel getLblstatisticInfoLabel3_2() {
		return lblstatisticInfoLabel3_2;
	}

	public JLabel getLblstatisticInfoLabel3_3() {
		return lblstatisticInfoLabel3_3;
	}

	public JLabel getLblstatisticInfoLabel3_4() {
		return lblstatisticInfoLabel3_4;
	}

	@Override
	public void printExceptions(String exception, int lblNumber) {
		super.printExceptions(exception, lblNumber);
	}	

	@Override
	public void run() {	}

	@Override
	public void hideExceptions() {	}
}
