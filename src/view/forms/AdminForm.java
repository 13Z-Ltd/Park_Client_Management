package view.forms;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import model.Model;
import view.drawpanels.AdminDrawPanel;

import com.toedter.calendar.JDateChooser;

public class AdminForm extends MainJFrameForm implements Runnable {

	private static final long serialVersionUID = 1L;

	private Model model;
	
	private JPanel contentPane;
	private AdminDrawPanel adminDrawPanel;
	
	private JPanel wristbandRegistrationPanel;
	private JPanel userManagementPanel;
	private JPanel statisticsManagementPanel;
	
	private JLabel currentTimeLabel;
	private JLabel menuIconLabel;
	private JLabel signOutLabel;
		
	private JLabel wristbandFolderLabel;
	private JLabel usersFolderLabel;
	private JLabel statisticsFolderLabel;	
	private JLabel exceptionLabel;
	
	private JTextField barCodeRegistryTextField;
	private JTextField amountOfWristbandsTextField;
	private JButton barCodeRegistryButton;
	private JLabel colorPickerLabel;
	private JLabel resoultImageLabel;
	private JLabel resoultLabel;
	
	private JLabel whiteRegWristCountLabel;
	private JLabel yellowRegWristCountLabel;
	private JLabel redRegWristCountLabel;
	
	//User panel elements
	private JPanel appUserListPanel;	
	private JLabel appUserUserNameLabel;
	private JTextField appUserLastNameTextField;
	private JTextField appUserFirstNameTextField;
	private JLabel appUserRoleLabel;
	private JLabel newPasswordLabel;
	private JLabel newPasswordLabel_1;
	private JLabel newPasswordLabel_2;
	private JPasswordField newPasswordTextField;
	private JPasswordField newPasswordRepeaterTextField;
	private JLabel appUserDataModifierButtonLabel;
	private JLabel userPanelExceptionsLabel;
	private JLabel appUserEraseButtonLabel;
	
	private JLabel amountOfChildSingingLabel;
	private JLabel amountOfSeniorSingingLabel;
	private JLabel amountOfReservedChildSingingLabel;
	private JLabel amountOfReservedSeniorSingingLabel;
	
	//Statistic panel
	private JLabel updateButtonLabel;
	private JLabel registeredWhiteWristbandsLabel;
	private JLabel registeredYellowWristbandsLabel;
	private JLabel registeredRedWristbandsLabel;
	
	private JLabel periodicClosedFolderLabel;
	private JLabel periodicBadWeatherFolderLabel;

	private JLabel activeWhiteWristbandsLabel;
	private JLabel activeYellowWristbandsLabel;
	private JLabel activeRedWristbandsLabel;
	private JLabel clearActiveWristbandListButtonLabel;

	private JLabel closedWhiteWristbandsLabel;
	private JLabel closedYellowWristbandsLabel;
	private JLabel closedRedWristbandsLabel;
	private JLabel closedWhiteWristPercentLabel;
	private JLabel closedYellowWristPercentLabel;
	private JLabel closedRedWristPercentLabel;
	private JLabel closedSumWristbandsLabel;
	
	private JLabel numberOfClosedWhite_0_Label;
	private JLabel numberOfClosedWhite_10_Label;
	private JLabel numberOfClosedWhite_20_Label;
	private JLabel numberOfClosedYellow_0_Label;
	private JLabel numberOfClosedYellow_10_Label;
	private JLabel numberOfClosedYellow_20_Label;
	private JLabel numberOfClosedRed_0_Label;
	private JLabel numberOfClosedRed_10_Label;
	private JLabel numberOfClosedRed_20_Label;
	private JLabel averageWhiteTimeLabel;
	private JLabel averageYellowTimeLabel;
	private JLabel averageRedTimeLabel;
	private JLabel numberOfWhiteOvertimeLabel;
	private JLabel numberOfYellowOvertimeLabel;
	private JLabel numberOfRedOvertimeLabel;
	private JLabel percentOfWhiteOvertimeLabel;
	private JLabel percentOfYellowOvertimeLabel;
	private JLabel percentOfRedOvertimeLabel;
	
	private JLabel numberOfBadWeatherActivations;
	private JLabel sumOfBadWeatherTimes;
	private JLabel sumOfCanceledWristbands;
	
	private JDateChooser dateChooserFrom;
	private JDateChooser dateChooserTo;
	private JLabel periodicWhiteWristbandsLabel;
	private JLabel periodicYellowWristbandsLabel;
	private JLabel periodicRedWristbandsLabel;
	private JLabel periodicInfoLabel;
	private JLabel periodicInfoLabel_2;
	private JLabel periodicWhiteWristbandsLabel_2;
	private JLabel periodicDataQueryButtonLabel;
	
	//Colors and Fonts
	Color DARKBROWN = new Color(55, 45, 40);
	Color ORANGE = new Color(251, 182, 0);
	Color GRASSGREEN = new Color(150, 169, 56);
	Color FOLDERGREEN = new Color(96, 100, 76);
	Color LIGHTGRAY = new Color(235, 235, 235);
	Color DARKGRAY = new Color(37, 37, 37);
	Color SHADOW = new Color(37, 37, 37);
	Color BEIGE = new Color(239, 231, 219);
	Color GRAPHITE = new Color(69, 73, 78);	
	Color PASTELWHITE = new Color(241, 241, 241);
	Color MACAROON = new Color(249, 224, 119);
	Color PASTELRED = new Color(200, 78, 56);
	
	Font normalFont = new Font("Verdana", Font.PLAIN, 20);
	Font biggerFont = new Font("Verdana", Font.BOLD, 26);
	Font menuFont = new Font("Verdana", Font.PLAIN, 19);
	Font statisticFont = new Font("Verdana", Font.PLAIN, 15);
	Font bigStatisticFont = new Font("Verdana", Font.PLAIN, 18);
	Font bigNumberFont = new Font("Verdana", Font.BOLD, 20);
	
	/**
	 * Create the frame.
	 */
	public AdminForm(Model receivedModel) {
		this.model = receivedModel;		
		
		setTitle("Adminisztációs ablak");
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource("bird_logo.png")).getImage());	
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0, 0, 1600, 900);
		setLocationRelativeTo(null);		
						
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(null);
		contentPane.setBackground(GRASSGREEN);
		setContentPane(contentPane);
		
		adminDrawPanel = new AdminDrawPanel();
		adminDrawPanel.setBounds(0, 0, 1584, 861);
		contentPane.add(adminDrawPanel);
		
		currentTimeLabel = new JLabel("");
		currentTimeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		currentTimeLabel.setForeground(Color.WHITE);
		currentTimeLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		currentTimeLabel.setBounds(10, 10, 180, 30);
		adminDrawPanel.add(currentTimeLabel);
		
		JLabel lblUserNameLabel = new JLabel(model.getCurrentUser().getLastName() + " " + model.getCurrentUser().getFirstName());
		lblUserNameLabel.setBounds(1285, 5, 240, 40);
		lblUserNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUserNameLabel.setForeground(Color.WHITE);
		lblUserNameLabel.setFont(normalFont);
		adminDrawPanel.add(lblUserNameLabel);
		
		menuIconLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("menu_icon.png")));
		menuIconLabel.setBounds(1535, 5, 45, 40);
		adminDrawPanel.add(menuIconLabel);
		
		signOutLabel = new JLabel("Kijelentkezés");
		signOutLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		signOutLabel.setBounds(1383, 56, 191, 30);
		signOutLabel.setFont(normalFont);
		signOutLabel.setForeground(Color.WHITE);
		signOutLabel.setVisible(false);
		adminDrawPanel.add(signOutLabel);
		
		wristbandFolderLabel = new JLabel("Karszalagok / Beülõk");
		wristbandFolderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		wristbandFolderLabel.setForeground(Color.WHITE);
		wristbandFolderLabel.setBackground(GRASSGREEN);
		wristbandFolderLabel.setOpaque(true);
		wristbandFolderLabel.setFont(menuFont);
		wristbandFolderLabel.setBounds(5, 50, 250, 40);
		adminDrawPanel.add(wristbandFolderLabel);
		
		usersFolderLabel = new JLabel("Felhasználók");
		usersFolderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usersFolderLabel.setForeground(Color.WHITE);
		usersFolderLabel.setBackground(GRASSGREEN);
		usersFolderLabel.setOpaque(true);
		usersFolderLabel.setFont(menuFont);
		usersFolderLabel.setBounds(260, 54, 140, 36);
		adminDrawPanel.add(usersFolderLabel);
		
		statisticsFolderLabel = new JLabel("Statisztikák");
		statisticsFolderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		statisticsFolderLabel.setForeground(Color.WHITE);
		statisticsFolderLabel.setBackground(GRASSGREEN);
		statisticsFolderLabel.setOpaque(true);
		statisticsFolderLabel.setFont(menuFont);
		statisticsFolderLabel.setBounds(405, 54, 140, 36);
		adminDrawPanel.add(statisticsFolderLabel);		
		
		initializeWristbandRegistrationPanel();
		
		initializeUserManagementPanel();
		
		initializeStatisticsManagementPanel();
	}
	
	private void initializeStatisticsManagementPanel() {
		statisticsManagementPanel = new JPanel();
		statisticsManagementPanel.setBounds(50, 100, 1500, 700);
		statisticsManagementPanel.setBackground(new Color(0, 0, 0, 0));
		statisticsManagementPanel.setLayout(null);
		adminDrawPanel.add(statisticsManagementPanel);
		
		updateButtonLabel = new JLabel("Frissítés");
		updateButtonLabel.setHorizontalAlignment(SwingConstants.CENTER);
		updateButtonLabel.setBounds(1280, 640, 200, 40);
		updateButtonLabel.setBorder(new LineBorder(Color.WHITE, 2));
		updateButtonLabel.setOpaque(true);
		updateButtonLabel.setBackground(DARKBROWN);
		updateButtonLabel.setForeground(LIGHTGRAY);
		updateButtonLabel.setFont(normalFont);
		statisticsManagementPanel.add(updateButtonLabel);
		
		JLabel lblRegistratedLabel_1_1 = new JLabel("Mennyiségi kimutatások");
		lblRegistratedLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistratedLabel_1_1.setForeground(new Color(235, 235, 235));
		lblRegistratedLabel_1_1.setBackground(GRASSGREEN);
		lblRegistratedLabel_1_1.setFont(bigStatisticFont); 
		lblRegistratedLabel_1_1.setOpaque(true);
		lblRegistratedLabel_1_1.setBounds(45, 30, 230, 25);
		statisticsManagementPanel.add(lblRegistratedLabel_1_1);
		
		JLabel lblRegistratedLabel_1 = new JLabel("Felhasználható");
		lblRegistratedLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblRegistratedLabel_1.setForeground(new Color(235, 235, 235));
		lblRegistratedLabel_1.setFont(statisticFont);
		lblRegistratedLabel_1.setBounds(50, 110, 120, 25);
		statisticsManagementPanel.add(lblRegistratedLabel_1);		
		
		JLabel lblRegistratedLabel_2 = new JLabel("Fehér szalagok");
		lblRegistratedLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistratedLabel_2.setForeground(Color.WHITE);
		lblRegistratedLabel_2.setFont(statisticFont);
		lblRegistratedLabel_2.setBounds(200, 60, 120, 25);
		statisticsManagementPanel.add(lblRegistratedLabel_2);
		
		JLabel lblRegistratedLabel_3 = new JLabel("Sárga szalagok");
		lblRegistratedLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistratedLabel_3.setForeground(Color.YELLOW);
		lblRegistratedLabel_3.setFont(statisticFont);
		lblRegistratedLabel_3.setBounds(350, 60, 120, 25);
		statisticsManagementPanel.add(lblRegistratedLabel_3);
		
		JLabel lblRegistratedLabel_4 = new JLabel("Piros szalagok");
		lblRegistratedLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistratedLabel_4.setForeground(PASTELRED);
		lblRegistratedLabel_4.setFont(statisticFont);
		lblRegistratedLabel_4.setBounds(500, 60, 120, 25);
		statisticsManagementPanel.add(lblRegistratedLabel_4);		
		
		registeredWhiteWristbandsLabel = new JLabel("__");
		registeredWhiteWristbandsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		registeredWhiteWristbandsLabel.setForeground(GRAPHITE);
		registeredWhiteWristbandsLabel.setFont(statisticFont);
		registeredWhiteWristbandsLabel.setBounds(200, 110, 120, 25);
		statisticsManagementPanel.add(registeredWhiteWristbandsLabel);
		
		registeredYellowWristbandsLabel = new JLabel("__");
		registeredYellowWristbandsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		registeredYellowWristbandsLabel.setForeground(GRAPHITE);
		registeredYellowWristbandsLabel.setFont(statisticFont);
		registeredYellowWristbandsLabel.setBounds(350, 110, 120, 25);
		statisticsManagementPanel.add(registeredYellowWristbandsLabel);
		
		registeredRedWristbandsLabel = new JLabel("__");
		registeredRedWristbandsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		registeredRedWristbandsLabel.setForeground(GRAPHITE);
		registeredRedWristbandsLabel.setFont(statisticFont);
		registeredRedWristbandsLabel.setBounds(500, 110, 120, 25);
		statisticsManagementPanel.add(registeredRedWristbandsLabel);
		
		JLabel lblRegistratedLabel_5 = new JLabel("Aktív");
		lblRegistratedLabel_5.setHorizontalAlignment(SwingConstants.LEFT);
		lblRegistratedLabel_5.setForeground(new Color(235, 235, 235));
		lblRegistratedLabel_5.setFont(statisticFont);
		lblRegistratedLabel_5.setBounds(50, 160, 110, 25);
		statisticsManagementPanel.add(lblRegistratedLabel_5);
		
		activeWhiteWristbandsLabel = new JLabel("__");
		activeWhiteWristbandsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		activeWhiteWristbandsLabel.setForeground(GRAPHITE);
		activeWhiteWristbandsLabel.setFont(statisticFont);
		activeWhiteWristbandsLabel.setBounds(200, 160, 120, 25);
		statisticsManagementPanel.add(activeWhiteWristbandsLabel);
		
		activeYellowWristbandsLabel = new JLabel("__");
		activeYellowWristbandsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		activeYellowWristbandsLabel.setForeground(GRAPHITE);
		activeYellowWristbandsLabel.setFont(statisticFont);
		activeYellowWristbandsLabel.setBounds(350, 160, 120, 25);
		statisticsManagementPanel.add(activeYellowWristbandsLabel);
		
		activeRedWristbandsLabel = new JLabel("__");
		activeRedWristbandsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		activeRedWristbandsLabel.setForeground(GRAPHITE);
		activeRedWristbandsLabel.setFont(statisticFont);
		activeRedWristbandsLabel.setBounds(500, 160, 120, 25);
		statisticsManagementPanel.add(activeRedWristbandsLabel);		
		
		clearActiveWristbandListButtonLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("delete_icon.png")));
		clearActiveWristbandListButtonLabel.setFont(statisticFont);
		clearActiveWristbandListButtonLabel.setBounds(630, 152, 40, 40);
		clearActiveWristbandListButtonLabel.setToolTipText("Töröli az adatbázisban lévõ aktív szalagokat!");
		statisticsManagementPanel.add(clearActiveWristbandListButtonLabel);
		
		JLabel lblRegistratedLabel_6 = new JLabel("Lezárt");
		lblRegistratedLabel_6.setHorizontalAlignment(SwingConstants.LEFT);
		lblRegistratedLabel_6.setForeground(new Color(235, 235, 235));
		lblRegistratedLabel_6.setFont(statisticFont);
		lblRegistratedLabel_6.setBounds(50, 210, 110, 25);
		statisticsManagementPanel.add(lblRegistratedLabel_6);
		
		closedWhiteWristbandsLabel = new JLabel("__");
		closedWhiteWristbandsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		closedWhiteWristbandsLabel.setForeground(GRAPHITE);
		closedWhiteWristbandsLabel.setFont(statisticFont);
		closedWhiteWristbandsLabel.setBounds(200, 210, 120, 25);
		statisticsManagementPanel.add(closedWhiteWristbandsLabel);
		
		closedYellowWristbandsLabel = new JLabel("__");
		closedYellowWristbandsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		closedYellowWristbandsLabel.setForeground(GRAPHITE);
		closedYellowWristbandsLabel.setFont(statisticFont);
		closedYellowWristbandsLabel.setBounds(350, 210, 120, 25);
		statisticsManagementPanel.add(closedYellowWristbandsLabel);
		
		closedRedWristbandsLabel = new JLabel("__");
		closedRedWristbandsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		closedRedWristbandsLabel.setForeground(GRAPHITE);
		closedRedWristbandsLabel.setFont(statisticFont);
		closedRedWristbandsLabel.setBounds(500, 210, 120, 25);
		statisticsManagementPanel.add(closedRedWristbandsLabel);
				
		closedWhiteWristPercentLabel = new JLabel("%");
		closedWhiteWristPercentLabel.setHorizontalAlignment(SwingConstants.CENTER);
		closedWhiteWristPercentLabel.setForeground(GRAPHITE);
		closedWhiteWristPercentLabel.setFont(statisticFont);
		closedWhiteWristPercentLabel.setBounds(225, 250, 120, 25);
		statisticsManagementPanel.add(closedWhiteWristPercentLabel);
		
		closedYellowWristPercentLabel = new JLabel("%");
		closedYellowWristPercentLabel.setHorizontalAlignment(SwingConstants.CENTER);
		closedYellowWristPercentLabel.setForeground(GRAPHITE);
		closedYellowWristPercentLabel.setFont(statisticFont);
		closedYellowWristPercentLabel.setBounds(375, 250, 120, 25);
		statisticsManagementPanel.add(closedYellowWristPercentLabel);
		
		closedRedWristPercentLabel = new JLabel("%");
		closedRedWristPercentLabel.setHorizontalAlignment(SwingConstants.CENTER);
		closedRedWristPercentLabel.setForeground(GRAPHITE);
		closedRedWristPercentLabel.setFont(statisticFont);
		closedRedWristPercentLabel.setBounds(525, 250, 120, 25);
		statisticsManagementPanel.add(closedRedWristPercentLabel);
		
		JLabel lblRegistratedSumLabel = new JLabel("Összesen:");
		lblRegistratedSumLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblRegistratedSumLabel.setForeground(new Color(235, 235, 235));
		lblRegistratedSumLabel.setFont(statisticFont);
		lblRegistratedSumLabel.setBounds(210, 285, 110, 40);
		statisticsManagementPanel.add(lblRegistratedSumLabel);
		
		closedSumWristbandsLabel = new JLabel("__");
		closedSumWristbandsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		closedSumWristbandsLabel.setBackground(GRAPHITE);
		closedSumWristbandsLabel.setForeground(PASTELWHITE);
		closedSumWristbandsLabel.setOpaque(true);
		closedSumWristbandsLabel.setFont(statisticFont);
		closedSumWristbandsLabel.setBounds(350, 285, 120, 40);
		statisticsManagementPanel.add(closedSumWristbandsLabel);
		
		
		JLabel lblRegistratedLabel_8 = new JLabel("Kedvezmények szerinti kimutatások");
		lblRegistratedLabel_8.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistratedLabel_8.setForeground(new Color(235, 235, 235));
		lblRegistratedLabel_8.setBackground(GRASSGREEN);
		lblRegistratedLabel_8.setFont(bigStatisticFont); //statisticFont
		lblRegistratedLabel_8.setOpaque(true);
		lblRegistratedLabel_8.setBounds(940, 30, 340, 25);
		statisticsManagementPanel.add(lblRegistratedLabel_8);
		
		JLabel lblRegistratedLabel_9 = new JLabel("0%");
		lblRegistratedLabel_9.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistratedLabel_9.setForeground(new Color(235, 235, 235));
		lblRegistratedLabel_9.setFont(statisticFont);
		lblRegistratedLabel_9.setBounds(900, 100, 110, 25); 
		statisticsManagementPanel.add(lblRegistratedLabel_9);
		
		JLabel lblRegistratedLabel_10 = new JLabel("10%");
		lblRegistratedLabel_10.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistratedLabel_10.setForeground(new Color(235, 235, 235));
		lblRegistratedLabel_10.setFont(statisticFont);
		lblRegistratedLabel_10.setBounds(900, 150, 110, 25);
		statisticsManagementPanel.add(lblRegistratedLabel_10);
		
		JLabel lblRegistratedLabel_11 = new JLabel("20%");
		lblRegistratedLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistratedLabel_11.setForeground(new Color(235, 235, 235));
		lblRegistratedLabel_11.setFont(statisticFont);
		lblRegistratedLabel_11.setBounds(900, 201, 110, 25);
		statisticsManagementPanel.add(lblRegistratedLabel_11);
		
		numberOfClosedWhite_0_Label = new JLabel("_");
		numberOfClosedWhite_0_Label.setHorizontalAlignment(SwingConstants.CENTER);
		numberOfClosedWhite_0_Label.setForeground(GRAPHITE);
		numberOfClosedWhite_0_Label.setFont(statisticFont);
		numberOfClosedWhite_0_Label.setBounds(1001, 100, 110, 25);
		statisticsManagementPanel.add(numberOfClosedWhite_0_Label);
		
		numberOfClosedWhite_10_Label = new JLabel("_");
		numberOfClosedWhite_10_Label.setHorizontalAlignment(SwingConstants.CENTER);
		numberOfClosedWhite_10_Label.setForeground(GRAPHITE);
		numberOfClosedWhite_10_Label.setFont(statisticFont);
		numberOfClosedWhite_10_Label.setBounds(1001, 150, 110, 25); 
		statisticsManagementPanel.add(numberOfClosedWhite_10_Label);
		
		numberOfClosedWhite_20_Label = new JLabel("_");
		numberOfClosedWhite_20_Label.setHorizontalAlignment(SwingConstants.CENTER);
		numberOfClosedWhite_20_Label.setForeground(GRAPHITE);
		numberOfClosedWhite_20_Label.setFont(statisticFont);
		numberOfClosedWhite_20_Label.setBounds(1001, 201, 110, 25); 
		statisticsManagementPanel.add(numberOfClosedWhite_20_Label);
		
		numberOfClosedYellow_0_Label = new JLabel("_");
		numberOfClosedYellow_0_Label.setHorizontalAlignment(SwingConstants.CENTER);
		numberOfClosedYellow_0_Label.setForeground(GRAPHITE);
		numberOfClosedYellow_0_Label.setFont(statisticFont);
		numberOfClosedYellow_0_Label.setBounds(1150, 100, 110, 25); 
		statisticsManagementPanel.add(numberOfClosedYellow_0_Label);
		
		numberOfClosedYellow_10_Label = new JLabel("_");
		numberOfClosedYellow_10_Label.setHorizontalAlignment(SwingConstants.CENTER);
		numberOfClosedYellow_10_Label.setForeground(GRAPHITE);
		numberOfClosedYellow_10_Label.setFont(statisticFont);
		numberOfClosedYellow_10_Label.setBounds(1150, 150, 110, 25); 
		statisticsManagementPanel.add(numberOfClosedYellow_10_Label);
		
		numberOfClosedYellow_20_Label = new JLabel("_");
		numberOfClosedYellow_20_Label.setHorizontalAlignment(SwingConstants.CENTER);
		numberOfClosedYellow_20_Label.setForeground(GRAPHITE);
		numberOfClosedYellow_20_Label.setFont(statisticFont);
		numberOfClosedYellow_20_Label.setBounds(1150, 201, 110, 25);
		statisticsManagementPanel.add(numberOfClosedYellow_20_Label);
		
		numberOfClosedRed_0_Label = new JLabel("_");
		numberOfClosedRed_0_Label.setHorizontalAlignment(SwingConstants.CENTER);
		numberOfClosedRed_0_Label.setForeground(GRAPHITE);
		numberOfClosedRed_0_Label.setFont(statisticFont);
		numberOfClosedRed_0_Label.setBounds(1300, 100, 110, 25);
		statisticsManagementPanel.add(numberOfClosedRed_0_Label);
		
		numberOfClosedRed_10_Label = new JLabel("_");
		numberOfClosedRed_10_Label.setHorizontalAlignment(SwingConstants.CENTER);
		numberOfClosedRed_10_Label.setForeground(GRAPHITE);
		numberOfClosedRed_10_Label.setFont(statisticFont);
		numberOfClosedRed_10_Label.setBounds(1300, 150, 110, 25); 
		statisticsManagementPanel.add(numberOfClosedRed_10_Label);
		
		numberOfClosedRed_20_Label = new JLabel("_");
		numberOfClosedRed_20_Label.setHorizontalAlignment(SwingConstants.CENTER);
		numberOfClosedRed_20_Label.setForeground(GRAPHITE);
		numberOfClosedRed_20_Label.setFont(statisticFont);
		numberOfClosedRed_20_Label.setBounds(1300, 201, 110, 25);
		statisticsManagementPanel.add(numberOfClosedRed_20_Label);
		
		//Time statistics
		JLabel lblRegistratedLabel_12 = new JLabel("Átlagos idõk");
		lblRegistratedLabel_12.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistratedLabel_12.setForeground(new Color(235, 235, 235));
		lblRegistratedLabel_12.setBackground(GRASSGREEN);
		lblRegistratedLabel_12.setFont(bigStatisticFont); 
		lblRegistratedLabel_12.setOpaque(true);
		lblRegistratedLabel_12.setBounds(775, 255, 140, 25);
		statisticsManagementPanel.add(lblRegistratedLabel_12);
		
		JLabel lblRegistratedLabel_8_1 = new JLabel("Átlagos pályán töltött idõ");
		lblRegistratedLabel_8_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRegistratedLabel_8_1.setForeground(new Color(235, 235, 235));
		lblRegistratedLabel_8_1.setFont(statisticFont);
		lblRegistratedLabel_8_1.setBounds(740, 300, 230, 25);
		statisticsManagementPanel.add(lblRegistratedLabel_8_1);
		
		averageWhiteTimeLabel = new JLabel("_");
		averageWhiteTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		averageWhiteTimeLabel.setForeground(GRAPHITE);
		averageWhiteTimeLabel.setFont(statisticFont);
		averageWhiteTimeLabel.setBounds(1000, 300, 110, 25);
		statisticsManagementPanel.add(averageWhiteTimeLabel);
		
		averageYellowTimeLabel = new JLabel("_");
		averageYellowTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		averageYellowTimeLabel.setForeground(GRAPHITE);
		averageYellowTimeLabel.setFont(statisticFont);
		averageYellowTimeLabel.setBounds(1150, 300, 110, 25);
		statisticsManagementPanel.add(averageYellowTimeLabel);
		
		averageRedTimeLabel = new JLabel("_");
		averageRedTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		averageRedTimeLabel.setForeground(GRAPHITE);
		averageRedTimeLabel.setFont(statisticFont);
		averageRedTimeLabel.setBounds(1300, 300, 110, 25);
		statisticsManagementPanel.add(averageRedTimeLabel);
		
		JLabel lblRegistratedLabel_8_1_1 = new JLabel("Idõ túllépések száma");
		lblRegistratedLabel_8_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRegistratedLabel_8_1_1.setForeground(new Color(235, 235, 235));
		lblRegistratedLabel_8_1_1.setFont(statisticFont);
		lblRegistratedLabel_8_1_1.setBounds(740, 365, 230, 25);
		statisticsManagementPanel.add(lblRegistratedLabel_8_1_1);
		
		numberOfWhiteOvertimeLabel = new JLabel("_");
		numberOfWhiteOvertimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		numberOfWhiteOvertimeLabel.setForeground(new Color(69, 73, 78));
		numberOfWhiteOvertimeLabel.setFont(statisticFont);
		numberOfWhiteOvertimeLabel.setBounds(1000, 370, 110, 25);
		statisticsManagementPanel.add(numberOfWhiteOvertimeLabel);
		
		numberOfYellowOvertimeLabel = new JLabel("_");
		numberOfYellowOvertimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		numberOfYellowOvertimeLabel.setForeground(new Color(69, 73, 78));
		numberOfYellowOvertimeLabel.setFont(statisticFont);
		numberOfYellowOvertimeLabel.setBounds(1150, 370, 110, 25);
		statisticsManagementPanel.add(numberOfYellowOvertimeLabel);
		
		numberOfRedOvertimeLabel = new JLabel("_");
		numberOfRedOvertimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		numberOfRedOvertimeLabel.setForeground(new Color(69, 73, 78));
		numberOfRedOvertimeLabel.setFont(statisticFont);
		numberOfRedOvertimeLabel.setBounds(1300, 370, 110, 25);
		statisticsManagementPanel.add(numberOfRedOvertimeLabel);		
		
		percentOfWhiteOvertimeLabel = new JLabel("%");
		percentOfWhiteOvertimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		percentOfWhiteOvertimeLabel.setForeground(new Color(69, 73, 78));
		percentOfWhiteOvertimeLabel.setFont(statisticFont);
		percentOfWhiteOvertimeLabel.setBounds(1050, 420, 60, 25);
		statisticsManagementPanel.add(percentOfWhiteOvertimeLabel);		
		percentOfYellowOvertimeLabel = new JLabel("%");
		percentOfYellowOvertimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		percentOfYellowOvertimeLabel.setForeground(new Color(69, 73, 78));
		percentOfYellowOvertimeLabel.setFont(statisticFont);
		percentOfYellowOvertimeLabel.setBounds(1200, 420, 60, 25);
		statisticsManagementPanel.add(percentOfYellowOvertimeLabel);		
		percentOfRedOvertimeLabel = new JLabel("%");
		percentOfRedOvertimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		percentOfRedOvertimeLabel.setForeground(new Color(69, 73, 78));
		percentOfRedOvertimeLabel.setFont(statisticFont);
		percentOfRedOvertimeLabel.setBounds(1350, 420, 60, 25);
		statisticsManagementPanel.add(percentOfRedOvertimeLabel);
		
		//Bad weather
		JLabel lblRegistratedLabel_12_1 = new JLabel("Rossz idõ statisztikák");
		lblRegistratedLabel_12_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistratedLabel_12_1.setForeground(new Color(235, 235, 235));
		lblRegistratedLabel_12_1.setBackground(GRASSGREEN);
		lblRegistratedLabel_12_1.setFont(bigStatisticFont); //statisticFont
		lblRegistratedLabel_12_1.setOpaque(true);
		lblRegistratedLabel_12_1.setBounds(775, 475, 210, 25);
		statisticsManagementPanel.add(lblRegistratedLabel_12_1);
		
		JLabel lblRegistratedLabel_13 = new JLabel("aktivált");
		lblRegistratedLabel_13.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistratedLabel_13.setForeground(new Color(235, 235, 235));		
		lblRegistratedLabel_13.setFont(statisticFont);		
		lblRegistratedLabel_13.setBounds(800, 520, 110, 25);
		statisticsManagementPanel.add(lblRegistratedLabel_13);
		JLabel lblRegistratedLabel_14 = new JLabel("összesített idõ");
		lblRegistratedLabel_14.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistratedLabel_14.setForeground(new Color(235, 235, 235));		
		lblRegistratedLabel_14.setFont(statisticFont);		
		lblRegistratedLabel_14.setBounds(1035, 520, 110, 25);
		statisticsManagementPanel.add(lblRegistratedLabel_14);
		JLabel lblRegistratedLabel_15 = new JLabel("érintett szalagok");
		lblRegistratedLabel_15.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistratedLabel_15.setForeground(new Color(235, 235, 235));		
		lblRegistratedLabel_15.setFont(statisticFont);		
		lblRegistratedLabel_15.setBounds(1260, 520, 130, 25);
		statisticsManagementPanel.add(lblRegistratedLabel_15);
		
		numberOfBadWeatherActivations = new JLabel("_");
		numberOfBadWeatherActivations.setHorizontalAlignment(SwingConstants.CENTER);
		numberOfBadWeatherActivations.setForeground(new Color(69, 73, 78));
		numberOfBadWeatherActivations.setFont(statisticFont);
		numberOfBadWeatherActivations.setBounds(800, 560, 110, 25);
		statisticsManagementPanel.add(numberOfBadWeatherActivations);
		
		sumOfBadWeatherTimes = new JLabel("_");
		sumOfBadWeatherTimes.setHorizontalAlignment(SwingConstants.CENTER);
		sumOfBadWeatherTimes.setForeground(new Color(69, 73, 78));
		sumOfBadWeatherTimes.setFont(statisticFont);
		sumOfBadWeatherTimes.setBounds(1035, 560, 110, 25);
		statisticsManagementPanel.add(sumOfBadWeatherTimes);
		
		sumOfCanceledWristbands = new JLabel("_");
		sumOfCanceledWristbands.setHorizontalAlignment(SwingConstants.CENTER);
		sumOfCanceledWristbands.setForeground(new Color(69, 73, 78));
		sumOfCanceledWristbands.setFont(statisticFont);
		sumOfCanceledWristbands.setBounds(1270, 560, 110, 25);
		statisticsManagementPanel.add(sumOfCanceledWristbands);
		
		JPanel panel = new JPanel();
		panel.setBounds(20, 390, 635, 280);
		panel.setBackground(FOLDERGREEN);
		statisticsManagementPanel.add(panel);
		panel.setLayout(null);
		
		periodicClosedFolderLabel = new JLabel("Szalag adatok");
		periodicClosedFolderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		periodicClosedFolderLabel.setForeground(Color.WHITE);
		periodicClosedFolderLabel.setBackground(FOLDERGREEN);
		periodicClosedFolderLabel.setOpaque(true);
		periodicClosedFolderLabel.setFont(menuFont);
		periodicClosedFolderLabel.setBounds(25, 350, 140, 40);
		statisticsManagementPanel.add(periodicClosedFolderLabel);
		
		periodicBadWeatherFolderLabel = new JLabel("Idõjárás adatok");
		periodicBadWeatherFolderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		periodicBadWeatherFolderLabel.setForeground(Color.WHITE);
		periodicBadWeatherFolderLabel.setBackground(SHADOW);
		periodicBadWeatherFolderLabel.setOpaque(true);
		periodicBadWeatherFolderLabel.setFont(menuFont);
		periodicBadWeatherFolderLabel.setBounds(175, 354, 160, 36);
		statisticsManagementPanel.add(periodicBadWeatherFolderLabel);
		
		dateChooserFrom = new JDateChooser();
		dateChooserFrom.setBounds(40, 50, 200, 30);
		dateChooserFrom.setFont(new Font("Verdana", Font.PLAIN, 13));
		panel.add(dateChooserFrom);
		
		dateChooserTo = new JDateChooser();
		dateChooserTo.setBounds(400, 50, 200, 30);
		dateChooserTo.setFont(new Font("Verdana", Font.PLAIN, 13));
		panel.add(dateChooserTo);
		
		JLabel lblRegistratedLabel_8_1_2 = new JLabel("tól");
		lblRegistratedLabel_8_1_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblRegistratedLabel_8_1_2.setForeground(new Color(235, 235, 235));
		lblRegistratedLabel_8_1_2.setFont(statisticFont);
		lblRegistratedLabel_8_1_2.setBounds(250, 50, 50, 30);
		panel.add(lblRegistratedLabel_8_1_2);
		
		JLabel lblRegistratedLabel_8_1_3 = new JLabel("ig");
		lblRegistratedLabel_8_1_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblRegistratedLabel_8_1_3.setForeground(new Color(235, 235, 235));
		lblRegistratedLabel_8_1_3.setFont(statisticFont);
		lblRegistratedLabel_8_1_3.setBounds(610, 50, 27, 30);
		panel.add(lblRegistratedLabel_8_1_3);
		
		periodicWhiteWristbandsLabel = new JLabel("__");
		periodicWhiteWristbandsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		periodicWhiteWristbandsLabel.setBackground(PASTELWHITE);
		periodicWhiteWristbandsLabel.setOpaque(true);
		periodicWhiteWristbandsLabel.setForeground(new Color(69, 73, 78));
		periodicWhiteWristbandsLabel.setFont(statisticFont);
		periodicWhiteWristbandsLabel.setBounds(100, 152, 120, 40);
		panel.add(periodicWhiteWristbandsLabel);
		
		periodicYellowWristbandsLabel = new JLabel("__");
		periodicYellowWristbandsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		periodicYellowWristbandsLabel.setBackground(MACAROON);
		periodicYellowWristbandsLabel.setOpaque(true);
		periodicYellowWristbandsLabel.setForeground(new Color(69, 73, 78));
		periodicYellowWristbandsLabel.setFont(statisticFont);
		periodicYellowWristbandsLabel.setBounds(265, 152, 120, 40);
		panel.add(periodicYellowWristbandsLabel);
		
		periodicRedWristbandsLabel = new JLabel("__");
		periodicRedWristbandsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		periodicRedWristbandsLabel.setBackground(PASTELRED);
		periodicRedWristbandsLabel.setOpaque(true);
		periodicRedWristbandsLabel.setForeground(new Color(69, 73, 78));
		periodicRedWristbandsLabel.setFont(statisticFont);
		periodicRedWristbandsLabel.setBounds(430, 152, 120, 40);
		panel.add(periodicRedWristbandsLabel);
		
		JLabel lblRegistratedLabel_8_1_4 = new JLabel("Idõszakos lekérdezések");
		lblRegistratedLabel_8_1_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRegistratedLabel_8_1_4.setForeground(new Color(235, 235, 235));
		lblRegistratedLabel_8_1_4.setFont(statisticFont);
		lblRegistratedLabel_8_1_4.setBounds(420, 5, 200, 25);
		panel.add(lblRegistratedLabel_8_1_4);
		
		periodicInfoLabel = new JLabel("Lezárt szalagok száma");
		//periodicInfoLabel = new JLabel("Aktíválások száma          Összes idõ            Érintett szalagok");
		periodicInfoLabel.setHorizontalAlignment(SwingConstants.LEFT);
		periodicInfoLabel.setForeground(new Color(235, 235, 235));
		periodicInfoLabel.setFont(statisticFont);
		periodicInfoLabel.setBounds(94, 110, 466, 25);
		panel.add(periodicInfoLabel);
		
		periodicDataQueryButtonLabel = new JLabel("Lekérés");
		periodicDataQueryButtonLabel.setOpaque(true);
		periodicDataQueryButtonLabel.setHorizontalAlignment(SwingConstants.CENTER);
		periodicDataQueryButtonLabel.setForeground(new Color(235, 235, 235));
		periodicDataQueryButtonLabel.setFont(statisticFont);
		periodicDataQueryButtonLabel.setBorder(new LineBorder(Color.WHITE, 2));
		periodicDataQueryButtonLabel.setBackground(DARKBROWN);
		periodicDataQueryButtonLabel.setBounds(430, 235, 190, 30);
		periodicDataQueryButtonLabel.setToolTipText("Megadott idõpontok közötti adatok lekérése");
		panel.add(periodicDataQueryButtonLabel);
		
		periodicInfoLabel_2 = new JLabel("Összesen:");		
		periodicInfoLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		periodicInfoLabel_2.setForeground(new Color(235, 235, 235));
		periodicInfoLabel_2.setFont(statisticFont);
		periodicInfoLabel_2.setBounds(100, 230, 120, 25);
		panel.add(periodicInfoLabel_2);
		
		periodicWhiteWristbandsLabel_2 = new JLabel("__");
		periodicWhiteWristbandsLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		periodicWhiteWristbandsLabel_2.setBackground(GRAPHITE);
		periodicWhiteWristbandsLabel_2.setOpaque(true);
		periodicWhiteWristbandsLabel_2.setForeground(PASTELWHITE);
		periodicWhiteWristbandsLabel_2.setFont(statisticFont);
		periodicWhiteWristbandsLabel_2.setBounds(265, 220, 120, 40);
		panel.add(periodicWhiteWristbandsLabel_2);
	}
	
	private void initializeWristbandRegistrationPanel() {
		wristbandRegistrationPanel = new JPanel();		
		wristbandRegistrationPanel.setBounds(50, 100, 1500, 700);
		wristbandRegistrationPanel.setBackground(new Color(0, 0, 0, 0));
		wristbandRegistrationPanel.setLayout(null);
		adminDrawPanel.add(wristbandRegistrationPanel);
				
		exceptionLabel = new JLabel("");
		exceptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		exceptionLabel.setBounds(150, 150, 700, 30);
		exceptionLabel.setForeground(Color.RED);
		exceptionLabel.setFont(menuFont);
		wristbandRegistrationPanel.add(exceptionLabel);
		
		barCodeRegistryTextField = new JTextField("");		
		barCodeRegistryTextField.setHorizontalAlignment(SwingConstants.CENTER);
		barCodeRegistryTextField.setBounds(50, 100, 500, 40);
		barCodeRegistryTextField.setFont(normalFont);		
		wristbandRegistrationPanel.add(barCodeRegistryTextField);
		
		amountOfWristbandsTextField = new JTextField("100");
		amountOfWristbandsTextField.setHorizontalAlignment(SwingConstants.CENTER);
		amountOfWristbandsTextField.setBackground(ORANGE);
		amountOfWristbandsTextField.setForeground(Color.WHITE);
		amountOfWristbandsTextField.setFont(biggerFont);
		amountOfWristbandsTextField.setBorder(new LineBorder(Color.WHITE, 2));
		amountOfWristbandsTextField.setBounds(650, 100, 150, 40);
		wristbandRegistrationPanel.add(amountOfWristbandsTextField);
		
		colorPickerLabel = new JLabel("Fehér");
		colorPickerLabel.setName("WHITE");
		colorPickerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		colorPickerLabel.setBounds(900, 100, 150, 40);
		colorPickerLabel.setBorder(new LineBorder(Color.WHITE, 2));
		colorPickerLabel.setOpaque(true);
		colorPickerLabel.setBackground(DARKBROWN);
		colorPickerLabel.setForeground(LIGHTGRAY);
		colorPickerLabel.setFont(normalFont);
		wristbandRegistrationPanel.add(colorPickerLabel);
		
		barCodeRegistryButton = new JButton("Hozzáadás");
		barCodeRegistryButton.setBounds(1150, 100, 200, 40);
		barCodeRegistryButton.setBackground(DARKBROWN);
		barCodeRegistryButton.setBorder(new LineBorder(Color.WHITE, 2));
		barCodeRegistryButton.setForeground(Color.WHITE);
		barCodeRegistryButton.setFont(normalFont);
		wristbandRegistrationPanel.add(barCodeRegistryButton);
		
		JLabel lblNewLabel = new JLabel("A regisztrálandó kötet elsõ karszalagának száma:");
		lblNewLabel.setBounds(50, 64, 500, 35);
		lblNewLabel.setForeground(DARKBROWN);
		lblNewLabel.setFont(menuFont);		
		wristbandRegistrationPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Mennyiség:");		
		lblNewLabel_1.setBounds(650, 64, 130, 35);
		lblNewLabel_1.setForeground(DARKBROWN);
		lblNewLabel_1.setFont(menuFont);
		wristbandRegistrationPanel.add(lblNewLabel_1);
				
		JLabel lblNewLabel_1_1 = new JLabel("Szín:");
		lblNewLabel_1_1.setForeground(new Color(55, 45, 40));
		lblNewLabel_1_1.setFont(new Font("Verdana", Font.PLAIN, 19));
		lblNewLabel_1_1.setBounds(900, 64, 130, 35);
		wristbandRegistrationPanel.add(lblNewLabel_1_1);
		
		resoultImageLabel = new JLabel();
		resoultImageLabel.setBounds(50, 5, 64, 60);
		resoultImageLabel.setVisible(false);
		wristbandRegistrationPanel.add(resoultImageLabel);
		
		resoultLabel = new JLabel("");
		resoultLabel.setHorizontalAlignment(SwingConstants.LEFT);
		resoultLabel.setBounds(130, 15, 1230, 40);
		resoultLabel.setFont(normalFont);
		resoultLabel.setForeground(Color.WHITE);
		wristbandRegistrationPanel.add(resoultLabel);
		
		/////
		JPanel numbersOfSingingsPanel = new JPanel();
		numbersOfSingingsPanel.setBounds(50, 470, 851, 190);
		numbersOfSingingsPanel.setBackground(FOLDERGREEN);
		numbersOfSingingsPanel.setLayout(null);
		numbersOfSingingsPanel.setBorder(new LineBorder(DARKGRAY, 1));
		wristbandRegistrationPanel.add(numbersOfSingingsPanel);
		
		JLabel lblSingingsTitleLabel = new JLabel("Beülõk száma");
		lblSingingsTitleLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblSingingsTitleLabel.setForeground(Color.WHITE);
		lblSingingsTitleLabel.setFont(bigStatisticFont);
		lblSingingsTitleLabel.setBounds(20, 0, 250, 30);
		numbersOfSingingsPanel.add(lblSingingsTitleLabel);
		
		JLabel kidsSingingImageLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("child_singing.png")));
		kidsSingingImageLabel.setToolTipText("Gyerek beülõ");
		kidsSingingImageLabel.setBounds(100, 40, 60, 60);
		numbersOfSingingsPanel.add(kidsSingingImageLabel);
		
		amountOfChildSingingLabel = new JLabel("0");
		amountOfChildSingingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		amountOfChildSingingLabel.setToolTipText("Gyerek beülõk száma");
		amountOfChildSingingLabel.setBackground(PASTELWHITE);
		amountOfChildSingingLabel.setOpaque(true);
		amountOfChildSingingLabel.setForeground(GRAPHITE);
		amountOfChildSingingLabel.setFont(biggerFont);
		amountOfChildSingingLabel.setBorder(new LineBorder(GRAPHITE, 2));
		amountOfChildSingingLabel.setBounds(200, 50, 150, 40);
		numbersOfSingingsPanel.add(amountOfChildSingingLabel);
		
		JLabel seniorSingingImageLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("senior_singing.png")));
		seniorSingingImageLabel.setToolTipText("Felnõtt beülõ");
		seniorSingingImageLabel.setBounds(450, 30, 80, 80);
		numbersOfSingingsPanel.add(seniorSingingImageLabel);
		
		amountOfSeniorSingingLabel = new JLabel("0");
		amountOfSeniorSingingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		amountOfSeniorSingingLabel.setToolTipText("Felnõtt beülõk száma");
		amountOfSeniorSingingLabel.setBackground(MACAROON);
		amountOfSeniorSingingLabel.setOpaque(true);
		amountOfSeniorSingingLabel.setForeground(GRAPHITE);
		amountOfSeniorSingingLabel.setFont(biggerFont);
		amountOfSeniorSingingLabel.setBorder(new LineBorder(GRAPHITE, 2));
		amountOfSeniorSingingLabel.setBounds(570, 50, 150, 40);
		numbersOfSingingsPanel.add(amountOfSeniorSingingLabel);
		
		JLabel lblSingingsTitleLabel_2 = new JLabel("Fenntartott");
		lblSingingsTitleLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblSingingsTitleLabel_2.setForeground(Color.WHITE);
		lblSingingsTitleLabel_2.setFont(bigStatisticFont);
		lblSingingsTitleLabel_2.setBounds(20, 130, 129, 30);
		numbersOfSingingsPanel.add(lblSingingsTitleLabel_2);
		
		amountOfReservedChildSingingLabel = new JLabel("_");
		amountOfReservedChildSingingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		amountOfReservedChildSingingLabel.setToolTipText("Fenntartott gyerek beülõk száma");
		amountOfReservedChildSingingLabel.setBackground(PASTELWHITE);
		amountOfReservedChildSingingLabel.setOpaque(true);
		amountOfReservedChildSingingLabel.setForeground(GRAPHITE);
		amountOfReservedChildSingingLabel.setFont(normalFont);
		amountOfReservedChildSingingLabel.setBorder(new LineBorder(GRAPHITE, 2));
		amountOfReservedChildSingingLabel.setBounds(270, 130, 80, 30);
		numbersOfSingingsPanel.add(amountOfReservedChildSingingLabel);
		
		amountOfReservedSeniorSingingLabel = new JLabel("_");
		amountOfReservedSeniorSingingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		amountOfReservedSeniorSingingLabel.setToolTipText("Fenntartott felnõtt beülõk száma");
		amountOfReservedSeniorSingingLabel.setBackground(MACAROON);
		amountOfReservedSeniorSingingLabel.setOpaque(true);
		amountOfReservedSeniorSingingLabel.setForeground(GRAPHITE);
		amountOfReservedSeniorSingingLabel.setFont(normalFont);
		amountOfReservedSeniorSingingLabel.setBorder(new LineBorder(GRAPHITE, 2));
		amountOfReservedSeniorSingingLabel.setBounds(640, 130, 80, 30);
		numbersOfSingingsPanel.add(amountOfReservedSeniorSingingLabel);
		
		/////
		JPanel registeredWristbandStatisticPanel = new JPanel();
		registeredWristbandStatisticPanel.setBounds(50, 240, 851, 190);
		registeredWristbandStatisticPanel.setBackground(FOLDERGREEN);
		registeredWristbandStatisticPanel.setLayout(null);
		registeredWristbandStatisticPanel.setBorder(new LineBorder(DARKGRAY, 1));
		wristbandRegistrationPanel.add(registeredWristbandStatisticPanel);
		
		JLabel lblStatisticsTitleLabel = new JLabel("Regisztrált karszalagok");
		lblStatisticsTitleLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblStatisticsTitleLabel.setForeground(Color.WHITE);
		lblStatisticsTitleLabel.setFont(bigStatisticFont);
		lblStatisticsTitleLabel.setBounds(20, 0, 300, 30);
		registeredWristbandStatisticPanel.add(lblStatisticsTitleLabel);
		
		JLabel lblStatisticsTitle_6 = new JLabel("Fehérek");
		lblStatisticsTitle_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatisticsTitle_6.setForeground(Color.WHITE);
		lblStatisticsTitle_6.setFont(bigNumberFont);
		lblStatisticsTitle_6.setBounds(70, 60, 120, 26);
		registeredWristbandStatisticPanel.add(lblStatisticsTitle_6);
		
		JLabel lblStatisticsTitle_5 = new JLabel("Sárgák");
		lblStatisticsTitle_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatisticsTitle_5.setForeground(new Color(251, 182, 0));
		lblStatisticsTitle_5.setFont(bigNumberFont);
		lblStatisticsTitle_5.setBounds(360, 60, 120, 26);
		registeredWristbandStatisticPanel.add(lblStatisticsTitle_5);
		
		JLabel lblStatisticsTitle_4 = new JLabel("Pirosak");
		lblStatisticsTitle_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatisticsTitle_4.setForeground(PASTELRED);
		lblStatisticsTitle_4.setFont(bigNumberFont);
		lblStatisticsTitle_4.setBounds(655, 60, 120, 26);
		registeredWristbandStatisticPanel.add(lblStatisticsTitle_4);
		
		whiteRegWristCountLabel = new JLabel("0");
		whiteRegWristCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		whiteRegWristCountLabel.setForeground(Color.WHITE);
		whiteRegWristCountLabel.setFont(bigNumberFont);
		whiteRegWristCountLabel.setBounds(70, 110, 120, 26);
		registeredWristbandStatisticPanel.add(whiteRegWristCountLabel);
		
		yellowRegWristCountLabel = new JLabel("0");
		yellowRegWristCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		yellowRegWristCountLabel.setForeground(ORANGE);
		yellowRegWristCountLabel.setFont(bigNumberFont);
		yellowRegWristCountLabel.setBounds(360, 110, 120, 26);
		registeredWristbandStatisticPanel.add(yellowRegWristCountLabel);
		
		redRegWristCountLabel = new JLabel("0");
		redRegWristCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		redRegWristCountLabel.setForeground(PASTELRED);
		redRegWristCountLabel.setFont(bigNumberFont);
		redRegWristCountLabel.setBounds(655, 110, 120, 26);
		registeredWristbandStatisticPanel.add(redRegWristCountLabel);		
	}
	
	private void initializeUserManagementPanel() {		
		userManagementPanel = new JPanel();		
		userManagementPanel.setBounds(50, 60, 1500, 700);
		userManagementPanel.setBackground(new Color(0, 0, 0, 0));
		adminDrawPanel.add(userManagementPanel);
		userManagementPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Regisztrált felhasználók");
		lblNewLabel.setBounds(50, 60, 351, 40);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(biggerFont);
		userManagementPanel.add(lblNewLabel);
		
		//
		appUserListPanel = new JPanel();
		appUserListPanel.setPreferredSize(new Dimension(375, 380));
		appUserListPanel.setBackground(BEIGE);
		JScrollPane scrollFrame = new JScrollPane(appUserListPanel);
		appUserListPanel.setAutoscrolls(true);
		scrollFrame.setPreferredSize(new Dimension(400, 400));
		scrollFrame.setBounds(50, 120, 400, 400);
		scrollFrame.setBorder(new BevelBorder(BevelBorder.RAISED));
		scrollFrame.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		userManagementPanel.add(scrollFrame);		
		
		JLabel lblUserDatasLabel = new JLabel("Felhasználó adatai:");
		lblUserDatasLabel.setForeground(Color.WHITE);
		lblUserDatasLabel.setFont(normalFont);
		lblUserDatasLabel.setBounds(600, 60, 300, 40);
		userManagementPanel.add(lblUserDatasLabel);
		
		JLabel lblUserDatasLabel_2 = new JLabel("Felhasználónév");
		lblUserDatasLabel_2.setForeground(Color.WHITE);
		lblUserDatasLabel_2.setFont(statisticFont);
		lblUserDatasLabel_2.setBounds(600, 120, 209, 30);
		userManagementPanel.add(lblUserDatasLabel_2);
		JLabel lblUserDatasLabel_3 = new JLabel("Vezetéknév");
		lblUserDatasLabel_3.setForeground(Color.WHITE);
		lblUserDatasLabel_3.setFont(statisticFont);
		lblUserDatasLabel_3.setBounds(851, 120, 209, 30);
		userManagementPanel.add(lblUserDatasLabel_3);
		JLabel lblUserDatasLabel_4 = new JLabel("Keresztnév");
		lblUserDatasLabel_4.setForeground(Color.WHITE);
		lblUserDatasLabel_4.setFont(statisticFont);
		lblUserDatasLabel_4.setBounds(1131, 120, 209, 30);
		userManagementPanel.add(lblUserDatasLabel_4);
		//
		
		appUserUserNameLabel = new JLabel("");
		appUserUserNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		appUserUserNameLabel.setOpaque(true);
		appUserUserNameLabel.setForeground(DARKBROWN);
		appUserUserNameLabel.setBackground(BEIGE);
		appUserUserNameLabel.setBorder(new LineBorder(DARKBROWN, 2));
		appUserUserNameLabel.setFont(biggerFont);
		appUserUserNameLabel.setBounds(600, 150, 180, 40);
		userManagementPanel.add(appUserUserNameLabel);
		
		appUserLastNameTextField = new JTextField("");
		appUserLastNameTextField.setForeground(DARKBROWN);
		appUserLastNameTextField.setBackground(BEIGE);
		appUserLastNameTextField.setFont(normalFont);
		appUserLastNameTextField.setHorizontalAlignment(SwingConstants.CENTER);
		appUserLastNameTextField.setBounds(850, 150, 210, 40);
		userManagementPanel.add(appUserLastNameTextField);
		
		appUserFirstNameTextField = new JTextField("");
		appUserFirstNameTextField.setForeground(DARKBROWN);
		appUserFirstNameTextField.setBackground(BEIGE);
		appUserFirstNameTextField.setFont(normalFont);
		appUserFirstNameTextField.setHorizontalAlignment(SwingConstants.CENTER);
		appUserFirstNameTextField.setBounds(1130, 150, 210, 40);
		userManagementPanel.add(appUserFirstNameTextField);
		
		JLabel lblUserDatasLabel_1 = new JLabel("Jogosultsága:");
		lblUserDatasLabel_1.setForeground(Color.WHITE);
		lblUserDatasLabel_1.setFont(normalFont);
		lblUserDatasLabel_1.setBounds(600, 300, 209, 40);
		userManagementPanel.add(lblUserDatasLabel_1);
		
		appUserRoleLabel = new JLabel("ADMIN");
		appUserRoleLabel.setOpaque(true);
		appUserRoleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		appUserRoleLabel.setForeground(Color.WHITE);
		appUserRoleLabel.setFont(normalFont);
		appUserRoleLabel.setBorder(new LineBorder(Color.WHITE, 2));
		appUserRoleLabel.setBackground(new Color(55, 45, 40));
		appUserRoleLabel.setBounds(850, 300, 210, 40);
		userManagementPanel.add(appUserRoleLabel);
		
		newPasswordLabel = new JLabel("Jelszó módosítása"); 
		newPasswordLabel.setOpaque(true);
		newPasswordLabel.setForeground(ORANGE);
		newPasswordLabel.setBackground(GRASSGREEN);
		newPasswordLabel.setFont(normalFont);
		newPasswordLabel.setBounds(600, 450, 209, 40);
		userManagementPanel.add(newPasswordLabel);
		
		newPasswordTextField = new JPasswordField("");
		newPasswordTextField.setForeground(new Color(55, 45, 40));
		newPasswordTextField.setBackground(BEIGE);
		newPasswordTextField.setFont(normalFont);
		newPasswordTextField.setBounds(850, 450, 210, 40);
		newPasswordTextField.setVisible(false);
		userManagementPanel.add(newPasswordTextField);
		
		newPasswordRepeaterTextField = new JPasswordField("");
		newPasswordRepeaterTextField.setForeground(new Color(55, 45, 40));
		newPasswordRepeaterTextField.setBackground(BEIGE);
		newPasswordRepeaterTextField.setFont(normalFont);
		newPasswordRepeaterTextField.setBounds(1130, 450, 210, 40);
		newPasswordRepeaterTextField.setVisible(false);
		userManagementPanel.add(newPasswordRepeaterTextField);
		
		newPasswordLabel_1 = new JLabel("Új jelszó");
		newPasswordLabel_1.setForeground(Color.WHITE);
		newPasswordLabel_1.setFont(statisticFont);
		newPasswordLabel_1.setBounds(850, 420, 209, 30);
		newPasswordLabel_1.setVisible(false);
		userManagementPanel.add(newPasswordLabel_1);
		
		newPasswordLabel_2 = new JLabel("Jelszó megerõsítése");
		newPasswordLabel_2.setForeground(Color.WHITE);
		newPasswordLabel_2.setFont(statisticFont);
		newPasswordLabel_2.setBounds(1130, 420, 209, 30);
		newPasswordLabel_2.setVisible(false);
		userManagementPanel.add(newPasswordLabel_2);
		
		appUserDataModifierButtonLabel = new JLabel("Módosítás");
		appUserDataModifierButtonLabel.setOpaque(true);
		appUserDataModifierButtonLabel.setHorizontalAlignment(SwingConstants.CENTER);
		appUserDataModifierButtonLabel.setForeground(Color.WHITE);
		appUserDataModifierButtonLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		appUserDataModifierButtonLabel.setBorder(new LineBorder(Color.WHITE, 2));
		appUserDataModifierButtonLabel.setBackground(new Color(55, 45, 40));
		appUserDataModifierButtonLabel.setBounds(1130, 600, 210, 40);
		userManagementPanel.add(appUserDataModifierButtonLabel);

		appUserEraseButtonLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("delete_icon.png")));
		appUserEraseButtonLabel.setToolTipText("Felhasználó törlése!");
		appUserEraseButtonLabel.setBounds(600, 600, 40, 40);
		userManagementPanel.add(appUserEraseButtonLabel);
		
		userPanelExceptionsLabel = new JLabel("");
		userPanelExceptionsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		userPanelExceptionsLabel.setForeground(Color.RED);
		userPanelExceptionsLabel.setFont(menuFont);
		userPanelExceptionsLabel.setBounds(851, 535, 489, 30);
		userManagementPanel.add(userPanelExceptionsLabel);			
	}
	
	public void changeResoultImageLabel(boolean ok) {
		if(ok){
			resoultImageLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ok_icon.png")));
			resoultImageLabel.setVisible(true);
		}else {
			resoultImageLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("error_icon.png")));
			resoultImageLabel.setVisible(true);
		}		
	}

	@Override
	public void run() {	}

	@Override
	public void hideExceptions() {	}	
	
	
	//DrawPanel
	public AdminDrawPanel getAdminDrawPanel() {
		return adminDrawPanel;
	}

	public JLabel getCurrentTimeLabel() {
		return currentTimeLabel;
	}

	public JLabel getMenuIconLabel() {
		return menuIconLabel;
	}

	public JLabel getSignOutLabel() {
		return signOutLabel;
	}

	public JLabel getWristbandFolderLabel() {
		return wristbandFolderLabel;
	}

	public JLabel getUsersFolderLabel() {
		return usersFolderLabel;
	}

	public JLabel getStatisticsFolderLabel() {
		return statisticsFolderLabel;
	}

	public JPanel getWristbandRegistrationPanel() {
		return wristbandRegistrationPanel;
	}

	public JPanel getUserManagementPanel() {
		return userManagementPanel;
	}

	public JPanel getStatisticsManagementPanel() {
		return statisticsManagementPanel;
	}

	public JTextField getBarCodeRegistryTextField() {
		return barCodeRegistryTextField;
	}

	public JTextField getAmountOfWristbandsTextField() {
		return amountOfWristbandsTextField;
	}
	
	public JLabel getColorPickerLabel() {
		return colorPickerLabel;
	}

	public JButton getBarCodeRegistryButton() {
		return barCodeRegistryButton;
	}

	public JLabel getExceptionLabel() {
		return exceptionLabel;
	}

	public JLabel getResoultLabel() {
		return resoultLabel;
	}
	
	public JLabel getResoultImageLabel() {
		return resoultImageLabel;
	}

	public JLabel getWhiteRegWristCountLabel() {
		return whiteRegWristCountLabel;
	}

	public JLabel getYellowRegWristCountLabel() {
		return yellowRegWristCountLabel;
	}

	public JLabel getRedRegWristCountLabel() {
		return redRegWristCountLabel;
	}	

	public JLabel getAmountOfChildSingingLabel() {
		return amountOfChildSingingLabel;
	}

	public JLabel getAmountOfSeniorSingingLabel() {
		return amountOfSeniorSingingLabel;
	}
	
	public JLabel getAmountOfReservedChildSingingLabel() {
		return amountOfReservedChildSingingLabel;
	}

	public JLabel getAmountOfReservedSeniorSingingLabel() {
		return amountOfReservedSeniorSingingLabel;
	}

	public JLabel getNumberOfBadWeatherActivations() {
		return numberOfBadWeatherActivations;
	}

	public JLabel getSumOfBadWeatherTimes() {
		return sumOfBadWeatherTimes;
	}

	public JLabel getSumOfCanceledWristbands() {
		return sumOfCanceledWristbands;
	}

	//User Panel getters
	public JPanel getAppUserListPanel() {
		return appUserListPanel;
	}

	public JLabel getAppUserUserNameLabel() {
		return appUserUserNameLabel;
	}

	public JTextField getAppUserLastNameTextField() {
		return appUserLastNameTextField;
	}

	public JTextField getAppUserFirstNameTextField() {
		return appUserFirstNameTextField;
	}

	public JLabel getAppUserRoleLabel() {
		return appUserRoleLabel;
	}

	public JLabel getNewPasswordLabel() {
		return newPasswordLabel;
	}
	
	public JLabel getNewPasswordLabel_1() {
		return newPasswordLabel_1;
	}
	
	public JLabel getNewPasswordLabel_2() {
		return newPasswordLabel_2;
	}

	public JPasswordField getNewPasswordTextField() {
		return newPasswordTextField;
	}

	public JPasswordField getNewPasswordRepeaterTextField() {
		return newPasswordRepeaterTextField;
	}

	public JLabel getAppUserDataModifierButtonLabel() {
		return appUserDataModifierButtonLabel;
	}

	public JLabel getAppUserEraseButtonLabel() {
		return appUserEraseButtonLabel;
	}

	public JLabel getUserPanelExceptionsLabel() {
		return userPanelExceptionsLabel;
	}
	
	//statistics	
	public JLabel getUpdateButtonLabel() {
		return updateButtonLabel;
	}

	public JLabel getRegisteredWhiteWristbandsLabel() {
		return registeredWhiteWristbandsLabel;
	}

	public JLabel getRegisteredYellowWristbandsLabel() {
		return registeredYellowWristbandsLabel;
	}

	public JLabel getRegisteredRedWristbandsLabel() {
		return registeredRedWristbandsLabel;
	}

	public JLabel getActiveWhiteWristbandsLabel() {
		return activeWhiteWristbandsLabel;
	}

	public JLabel getActiveYellowWristbandsLabel() {
		return activeYellowWristbandsLabel;
	}

	public JLabel getActiveRedWristbandsLabel() {
		return activeRedWristbandsLabel;
	}

	public JLabel getClosedWhiteWristbandsLabel() {
		return closedWhiteWristbandsLabel;
	}

	public JLabel getClosedYellowWristbandsLabel() {
		return closedYellowWristbandsLabel;
	}

	public JLabel getClosedRedWristbandsLabel() {
		return closedRedWristbandsLabel;
	}	
	
	public JLabel getClosedSumWristbandsLabel() {
		return closedSumWristbandsLabel;
	}

	/////////////
	public JLabel getNumberOfClosedWhite_0_Label() {
		return numberOfClosedWhite_0_Label;
	}

	public JLabel getNumberOfClosedWhite_10_Label() {
		return numberOfClosedWhite_10_Label;
	}

	public JLabel getNumberOfClosedWhite_20_Label() {
		return numberOfClosedWhite_20_Label;
	}

	public JLabel getNumberOfClosedYellow_0_Label() {
		return numberOfClosedYellow_0_Label;
	}

	public JLabel getNumberOfClosedYellow_10_Label() {
		return numberOfClosedYellow_10_Label;
	}

	public JLabel getNumberOfClosedYellow_20_Label() {
		return numberOfClosedYellow_20_Label;
	}

	public JLabel getNumberOfClosedRed_0_Label() {
		return numberOfClosedRed_0_Label;
	}

	public JLabel getNumberOfClosedRed_10_Label() {
		return numberOfClosedRed_10_Label;
	}

	public JLabel getNumberOfClosedRed_20_Label() {
		return numberOfClosedRed_20_Label;
	}

	public JLabel getAverageWhiteTimeLabel() {
		return averageWhiteTimeLabel;
	}

	public JLabel getAverageYellowTimeLabel() {
		return averageYellowTimeLabel;
	}

	public JLabel getAverageRedTimeLabel() {
		return averageRedTimeLabel;
	}

	public JLabel getNumberOfWhiteOvertimeLabel() {
		return numberOfWhiteOvertimeLabel;
	}

	public JLabel getNumberOfYellowOvertimeLabel() {
		return numberOfYellowOvertimeLabel;
	}

	public JLabel getNumberOfRedOvertimeLabel() {
		return numberOfRedOvertimeLabel;
	}

	public JDateChooser getDateChooserFrom() {
		return dateChooserFrom;
	}
	
	public JDateChooser getDateChooserTo() {
		return dateChooserTo;
	}

	public JLabel getPeriodicWhiteWristbandsLabel() {
		return periodicWhiteWristbandsLabel;
	}
	
	public JLabel getPeriodicWhiteWristbandsLabel_2() {
		return periodicWhiteWristbandsLabel_2;
	}

	public JLabel getPeriodicYellowWristbandsLabel() {
		return periodicYellowWristbandsLabel;
	}

	public JLabel getPeriodicRedWristbandsLabel() {
		return periodicRedWristbandsLabel;
	}
	
	public JLabel getPeriodicInfoLabel() {
		return periodicInfoLabel;
	}
	
	public JLabel getPeriodicInfoLabel_2() {
		return periodicInfoLabel_2;
	}

	public JLabel getPeriodicDataQueryButtonLabel() {
		return periodicDataQueryButtonLabel;
	}

	public JLabel getClosedWhiteWristPercentLabel() {
		return closedWhiteWristPercentLabel;
	}

	public JLabel getClosedYellowWristPercentLabel() {
		return closedYellowWristPercentLabel;
	}

	public JLabel getClosedRedWristPercentLabel() {
		return closedRedWristPercentLabel;
	}

	public JLabel getPercentOfWhiteOvertimeLabel() {
		return percentOfWhiteOvertimeLabel;
	}

	public JLabel getPercentOfYellowOvertimeLabel() {
		return percentOfYellowOvertimeLabel;
	}

	public JLabel getPercentOfRedOvertimeLabel() {
		return percentOfRedOvertimeLabel;
	}

	public JLabel getClearActiveWristbandListButtonLabel() {
		return clearActiveWristbandListButtonLabel;
	}

	public JLabel getPeriodicClosedFolderLabel() {
		return periodicClosedFolderLabel;
	}

	public JLabel getPeriodicBadWeatherFolderLabel() {
		return periodicBadWeatherFolderLabel;
	}		
}
