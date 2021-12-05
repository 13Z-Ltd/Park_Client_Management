package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import model.Model;
import model.db.PBEStringEncryptor;
import model.entities.AppUser;
import model.entities.ClosedWristband;
import model.entities.RegisteredWristband;
import model.entities.SettingsData;
import model.services.ActiveWristbandService;
import model.services.AppUserService;
import model.services.BadWeatherLogService;
import model.services.ClosedWristbandService;
import model.services.RegisteredWristbandService;
import model.services.SettingsDataService;
import view.View;
import view.forms.AdminForm;

public class AdminFormController implements ActionListener, MouseListener, MouseWheelListener{
	private View view;
	private Model model;
	private Controller controller;	
	private AdminForm adminForm;
	
	private final int REFRESHING_INTERVAL = 30000; //30sec
	private Timer clockTimer;
	
	private AppUserService appUserService;
	private RegisteredWristbandService registeredWristbandService;
	private ActiveWristbandService activeWristbandService;
	private ClosedWristbandService closedWristbandService;
	private SettingsDataService settingsDataService;
	private BadWeatherLogService badWeatherLogService;
	
	private PBEStringEncryptor encryptor;
	
	private boolean isWristbandRegistryPanel;
	private boolean isManageUser;
	private boolean isCheckStatistics;
	
	private boolean isPeriodicFolderClosed;	
	private boolean clearTextTimer;
	
	//Register panel
	private Long barCode;
	private int amount;
	private SettingsData currentSettings;
	
	//User Panel
	private List<AppUser> appUsers;
	private MouseListener userPanelMouseListener;
	private AppUser loadedAppUser;
	private boolean createNewPassword = false;
	
	//statistics Panel
	private Long numbersOfClosedWhites;
	private Long numbersOfClosedYellows;
	private Long numbersOfClosedReds;
	
	Color DARKBROWN = new Color(55, 45, 40);
	Color LIGHTBROWN = new Color(85, 75, 75);
	Color ORANGE = new Color(251, 182, 0);
	Color GRASSGREEN = new Color(150, 169, 56);
	Color FOLDERGREEN = new Color(96, 100, 76);
	Color FOLDERGREENON = new Color(111, 121, 58);
	Color LIGHTGRAY = new Color(235, 235, 235);
	Color SHADOW = new Color(37, 37, 37);
	Color BEIGE = new Color(239, 231, 219);
	Color PASTELWHITE = new Color(241, 241, 241);
	Color MACAROON = new Color(249, 224, 119);
	Color GRAPHITE = new Color(69, 73, 78);
	Color PASTELRED = new Color(200, 78, 56);
	Color PASTELBLUE = new Color(0, 168, 243);
	
	Font normalFont = new Font("Verdana", Font.PLAIN, 20);
	Font activeFont = new Font("Verdana", Font.BOLD, 21);
	
	ImageIcon birdQuestionIcon = new ImageIcon(getClass().getClassLoader().getResource("bird_question2.png"));
	
	public AdminFormController(View getView, Model model, Controller controller) {
		this.view = getView;
		this.model = model;
		this.controller = controller;		
		adminForm = (AdminForm) view.getCurrentView();
		registeredWristbandService = new RegisteredWristbandService();
		activeWristbandService = new ActiveWristbandService();
		closedWristbandService = new ClosedWristbandService();
		appUserService = new AppUserService();
		settingsDataService = new SettingsDataService();
		badWeatherLogService = new BadWeatherLogService();
		
		isCheckStatistics = isManageUser = false;
		isWristbandRegistryPanel = true;
		isPeriodicFolderClosed = true;
		
		adminForm.getMenuIconLabel().addMouseListener(this);
		adminForm.getSignOutLabel().addMouseListener(this);
		
		adminForm.getWristbandFolderLabel().addMouseListener(this);
		adminForm.getUsersFolderLabel().addMouseListener(this);
		adminForm.getStatisticsFolderLabel().addMouseListener(this);
		
		userPanelMouseListener = getUserPanelMouseListener();
		
		adminForm.getBarCodeRegistryTextField().addActionListener(this);
		adminForm.getColorPickerLabel().addMouseListener(this);
		adminForm.getBarCodeRegistryButton().addMouseListener(this);
		adminForm.getBarCodeRegistryButton().addActionListener(this);
		adminForm.getAmountOfWristbandsTextField().addMouseListener(this);
		adminForm.getAmountOfChildSingingLabel().addMouseListener(this);
		adminForm.getAmountOfSeniorSingingLabel().addMouseListener(this);
		adminForm.getAmountOfChildSingingLabel().addMouseWheelListener(this);
		adminForm.getAmountOfSeniorSingingLabel().addMouseWheelListener(this);
		adminForm.getAmountOfReservedChildSingingLabel().addMouseListener(this);
		adminForm.getAmountOfReservedSeniorSingingLabel().addMouseListener(this);
		adminForm.getAmountOfReservedChildSingingLabel().addMouseWheelListener(this);
		adminForm.getAmountOfReservedSeniorSingingLabel().addMouseWheelListener(this);
		
		currentSettings = settingsDataService.findById(1);		
		adminForm.getAmountOfChildSingingLabel().setText(Integer.toString(currentSettings.getChildSinging()));
		adminForm.getAmountOfSeniorSingingLabel().setText(Integer.toString(currentSettings.getSeniorSinging()));
		adminForm.getAmountOfReservedChildSingingLabel().setText(Integer.toString(currentSettings.getReservedChildSinging()));
		adminForm.getAmountOfReservedSeniorSingingLabel().setText(Integer.toString(currentSettings.getReservedSeniorSinging()));
		
		//User Panel Listeners		
		adminForm.getAppUserDataModifierButtonLabel().addMouseListener(this);
		adminForm.getNewPasswordLabel().addMouseListener(this);
		adminForm.getAppUserEraseButtonLabel().addMouseListener(this);
		adminForm.getAppUserRoleLabel().addMouseListener(this);
		adminForm.getAppUserRoleLabel().addMouseWheelListener(this);		
				
		//statistics Panel
		adminForm.getUpdateButtonLabel().addMouseListener(this);
		adminForm.getPeriodicDataQueryButtonLabel().addMouseListener(this);
		adminForm.getClearActiveWristbandListButtonLabel().addMouseListener(this);
		adminForm.getPeriodicClosedFolderLabel().addMouseListener(this);
		adminForm.getPeriodicBadWeatherFolderLabel().addMouseListener(this);
		
		changeFolderLabels();
		
		startTimer();
		
		updateRegWristStatPanel();
		
		updateStatisticsPanel();
		
		appUsers = appUserService.findAll();
		loadedAppUser = appUsers.get(0);		
		
		adminForm.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if(view.showExitDialog() == JOptionPane.YES_OPTION) {					
					adminExitProcess();
					System.exit(0);
				}
		    }
		});
	}	
	
	private void startTimer() {
		final DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		Date date = new Date();
		adminForm.getCurrentTimeLabel().setText(timeFormat.format(date));
				
		clockTimer = new Timer(REFRESHING_INTERVAL, new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				Date date = new Date();
				adminForm.getCurrentTimeLabel().setText(timeFormat.format(date));
				if(isWristbandRegistryPanel) {
					updateRegWristStatPanel();
					
					if(clearTextTimer) {
						adminForm.getResoultLabel().setText("");						
						adminForm.getResoultImageLabel().setVisible(false);
						adminForm.getBarCodeRegistryTextField().setText("");
						
						clearTextTimer = false;
						adminForm.repaint();
					}
					
					if(adminForm.getResoultLabel().getText().length() > 0) {
						clearTextTimer = true;
					}
				}
			}			
		});		
		clockTimer.start();
	}
	
	private void changeFolderLabels() {
		adminForm.getWristbandFolderLabel().setBounds(5, 54, 250, 36); adminForm.getWristbandFolderLabel().setBackground(FOLDERGREEN);
		adminForm.getUsersFolderLabel().setBounds(260, 54, 140, 36); adminForm.getUsersFolderLabel().setBackground(FOLDERGREEN);
		adminForm.getStatisticsFolderLabel().setBounds(405, 54, 140, 36); adminForm.getStatisticsFolderLabel().setBackground(FOLDERGREEN);
		
		if(isWristbandRegistryPanel) {
			adminForm.getWristbandFolderLabel().setBounds(5, 50, 250, 40);
			adminForm.getWristbandFolderLabel().setBackground(GRASSGREEN);
			adminForm.getAdminDrawPanel().changePaintingElements(1);
			
			adminForm.getWristbandRegistrationPanel().setVisible(true);
			adminForm.getUserManagementPanel().setVisible(false);
			adminForm.getStatisticsManagementPanel().setVisible(false);
		}
		if(isManageUser) {
			adminForm.getUsersFolderLabel().setBounds(260, 50, 140, 40);
			adminForm.getUsersFolderLabel().setBackground(GRASSGREEN);
			adminForm.getAdminDrawPanel().changePaintingElements(2);
			
			adminForm.getWristbandRegistrationPanel().setVisible(false);
			adminForm.getUserManagementPanel().setVisible(true);
			adminForm.getStatisticsManagementPanel().setVisible(false);

			loadAppUserList();
			fillAppUserData();
		}
		if(isCheckStatistics) {
			adminForm.getStatisticsFolderLabel().setBounds(405, 50, 140, 40);
			adminForm.getStatisticsFolderLabel().setBackground(GRASSGREEN);
			adminForm.getAdminDrawPanel().changePaintingElements(3);
						
			adminForm.getWristbandRegistrationPanel().setVisible(false);
			adminForm.getUserManagementPanel().setVisible(false);
			adminForm.getStatisticsManagementPanel().setVisible(true);
		}

		adminForm.repaint();
	}
	
	private void loadAppUserList() {
		appUsers = appUserService.findAll();
		//appUsers.addAll(appUserService.findAll());		
		adminForm.getAppUserListPanel().removeAll();
		adminForm.getAppUserListPanel().setPreferredSize(new Dimension(375, 60 * appUsers.size()));
				
		//Add to panel
		int index = 0;
		if(appUsers != null) {
			for (AppUser appUser : appUsers) {
				JLabel userNameLabel = new JLabel(appUser.getUserName());
				userNameLabel.setName(appUser.getUserName());
				userNameLabel.setOpaque(true);
				userNameLabel.setFont(activeFont);
				userNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
				if(index % 2 == 0) {
					userNameLabel.setBackground(DARKBROWN);
					userNameLabel.setForeground(LIGHTGRAY);
				}else {
					userNameLabel.setBackground(Color.WHITE);
					userNameLabel.setForeground(DARKBROWN);
				}
				userNameLabel.setPreferredSize(new Dimension(370, 50));
				
				if(loadedAppUser != null && loadedAppUser.getUserName().equals(appUser.getUserName())) {
					userNameLabel.setBorder(new LineBorder(ORANGE, 3));
				}
				
				userNameLabel.addMouseListener(userPanelMouseListener);
				adminForm.getAppUserListPanel().add(userNameLabel);
				index++;
			}
		}
	}
	
	private void fillAppUserData() {
		if(loadedAppUser != null) {
			adminForm.getAppUserUserNameLabel().setText(loadedAppUser.getUserName());
			adminForm.getAppUserFirstNameTextField().setText(loadedAppUser.getFirstName());
			adminForm.getAppUserLastNameTextField().setText(loadedAppUser.getLastName());
				
			if(loadedAppUser.getRole().equals("ADMIN"))
				adminForm.getAppUserRoleLabel().setText("ADMIN");
			if(loadedAppUser.getRole().equals("DRESSING"))
				adminForm.getAppUserRoleLabel().setText("ÖLTÖZTETÉS");
			if(loadedAppUser.getRole().equals("REGISTER"))
				adminForm.getAppUserRoleLabel().setText("KASSZA");			
		}
	}
	
	private void scanAndCheckBarCode() {
		adminForm.getExceptionLabel().setText("");
		if (adminForm.getBarCodeRegistryTextField().getText().length() == 13) {
			barCode = 0L;
			try {
				barCode = Long.parseLong(adminForm.getBarCodeRegistryTextField().getText());				
				String type = adminForm.getBarCodeRegistryTextField().getText().substring(5, 7);
				
				if(type.equals("00")){
					adminForm.getColorPickerLabel().setName("WHITE");
					adminForm.getColorPickerLabel().setText("Fehér");
					adminForm.getColorPickerLabel().setForeground(LIGHTGRAY);
				}
				if(type.equals("01")) {
					adminForm.getColorPickerLabel().setName("YELLOW");
					adminForm.getColorPickerLabel().setText("Sárga");
					adminForm.getColorPickerLabel().setForeground(ORANGE);
				}					
				if(type.equals("02")) {
					adminForm.getColorPickerLabel().setName("RED");
					adminForm.getColorPickerLabel().setText("Piros");
					adminForm.getColorPickerLabel().setForeground(PASTELRED);
				}				
			}catch (Exception ex) {
				adminForm.getExceptionLabel().setText("Nem megfelelõ formátum!");
				adminForm.getExceptionLabel().setBounds(150, 150, 400, 30);
			}
		}else {
			adminForm.getExceptionLabel().setText("Nem megfelelõ vonalkód!");
			adminForm.getExceptionLabel().setBounds(150, 150, 400, 30);
		}
		adminForm.repaint();
	}
	
	private void startRegistrationProcedure() {
		adminForm.getExceptionLabel().setText("");
		adminForm.getResoultLabel().setText("");
		adminForm.getResoultImageLabel().setVisible(false);
		boolean correctBarCode = false;
		boolean correctAmount = false;
		adminForm.repaint();
		
		if (adminForm.getBarCodeRegistryTextField().getText().length() == 13) {
			barCode = 0L;
			try {
				barCode = Long.parseLong(adminForm.getBarCodeRegistryTextField().getText());				
				correctBarCode = true;				
			}catch (Exception ex) {
				adminForm.getExceptionLabel().setText("Nem megfelelõ formátum!");
				adminForm.getExceptionLabel().setBounds(150, 200, 400, 30);
			}
		}else {
			adminForm.getExceptionLabel().setText("Rosszul megadott vonalkód!");
			adminForm.getExceptionLabel().setBounds(150, 200, 400, 30);
		}
		
		if(adminForm.getAmountOfWristbandsTextField().getText().length() < 7) {
			amount = 0;
			try {
				amount = Integer.parseInt(adminForm.getAmountOfWristbandsTextField().getText());
				correctAmount = true;
			} catch (Exception exc) {
				adminForm.getExceptionLabel().setText("Rossz mennyiség érték lett megadva!");
				adminForm.getExceptionLabel().setBounds(500, 200, 400, 30);
				adminForm.getAmountOfWristbandsTextField().setText("100");
			}
		}else {
			adminForm.getExceptionLabel().setText("Kissebb értéket adjon meg!");
			adminForm.getExceptionLabel().setBounds(480, 200, 400, 30);
			adminForm.getAmountOfWristbandsTextField().setText("100");
		}
		
		if(correctBarCode && correctAmount) {
			int answer = showCustomJOptionPane("Biztosan regisztrálni szeretné a szalagokat?");
			switch (answer) {
				case JOptionPane.OK_OPTION: 
					registryIntoDatabase();
					updateRegWristStatPanel();
					break;
				case JOptionPane.NO_OPTION: 
					//System.out.println("NO!");
					break;
				default:
					//System.out.println("Cancel???");
			}
		}		
		adminForm.repaint();
	}
	
	@SuppressWarnings("null")
	private void registryIntoDatabase() {
		//check
		RegisteredWristband checkWristband = null;
		ClosedWristband checkedClosedWristband = null;
		
		checkWristband = registeredWristbandService.findByGroupOfIds(barCode, amount);
		if(checkWristband == null) {
			checkedClosedWristband = closedWristbandService.findByGroupOfIds(barCode, amount);
		}

		if(checkWristband == null && checkedClosedWristband == null) {		
			//Free to register!
			registeredWristbandService.insertGroupOfWristbands(barCode, amount, adminForm.getColorPickerLabel().getName(), model.getCurrentUser());
			
			adminForm.changeResoultImageLabel(true);
			adminForm.getResoultLabel().setText("A " + barCode + " kódszámmal kezdõdõ " + amount + " db " + adminForm.getColorPickerLabel().getText() + " szalag regisztrálása sikeres!");
		}else{
			if(checkWristband != null) {
				adminForm.changeResoultImageLabel(false);
				adminForm.getResoultLabel().setText("A " + checkWristband.getBarCode() + " vonalkódú szalag már szerepel a regisztráltak között!");
				adminForm.getExceptionLabel().setText("A " + checkWristband.getBarCode() + " vonalkódú szalag már szerepel a regisztráltak között!");
			}else if(checkedClosedWristband != null) {
				adminForm.changeResoultImageLabel(false);
				adminForm.getResoultLabel().setText("A " + checkWristband.getBarCode() + " vonalkódú szalag már szerepel a lezártak között!");
				adminForm.getExceptionLabel().setText("A " + checkWristband.getBarCode() + " vonalkódú szalag már szerepel a lezártak között!");
			}
		}		
	}
	
	public int showCustomJOptionPane(String discription) {
		Object[] options = {"Igen", "Nem"};
		int answer = JOptionPane.showOptionDialog(null,
				discription,
			    "Ellenõrzés",
			    JOptionPane.YES_NO_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    birdQuestionIcon,     //do not use a custom Icon
			    options,  //the titles of buttons
			    options[0]); //default button title
		return answer;
	}

	private void updateRegWristStatPanel() {
		adminForm.getWhiteRegWristCountLabel().setText(Long.toString(registeredWristbandService.countByColor("WHITE")));
		adminForm.getYellowRegWristCountLabel().setText(Long.toString(registeredWristbandService.countByColor("YELLOW")));
		adminForm.getRedRegWristCountLabel().setText(Long.toString(registeredWristbandService.countByColor("RED")));
	}
	
	@SuppressWarnings({ "deprecation", "static-access" })
	private void updateAppUser() {		
		int answer = showCustomJOptionPane("Biztosan módósítani szeretné az adatokat?");
		switch (answer) {
			case JOptionPane.OK_OPTION: 
				loadedAppUser.setFirstName(adminForm.getAppUserFirstNameTextField().getText());
				loadedAppUser.setLastName(adminForm.getAppUserLastNameTextField().getText());
				
				if(adminForm.getAppUserRoleLabel().getText().equals("ADMIN"))
					loadedAppUser.setRole("ADMIN");
				if(adminForm.getAppUserRoleLabel().getText().equals("ÖLTÖZTETÉS"))
					loadedAppUser.setRole("DRESSING");
				if(adminForm.getAppUserRoleLabel().getText().equals("KASSZA"))
					loadedAppUser.setRole("REGISTER");

				if(createNewPassword) {
					loadedAppUser.setPassword(encryptor.getEncryptedString(adminForm.getNewPasswordTextField().getText()));
				}
				
				try {
					appUserService.update(loadedAppUser);
				} catch (Exception e) {
					e.printStackTrace();
					adminForm.getExceptionLabel().setText("A felhasználó adatait nem sikerült módosítani!");
				}
				break;
			case JOptionPane.NO_OPTION: 
				//System.out.println("Nem lett módosítva!");
				break;
			default:
				//System.out.println("Cancel!");
				break;
		}		
	}
	
	private void eraseAppUser() {
		int answer = showCustomJOptionPane("Biztosan törölni szeretné az felhasználót?");
		switch (answer) {
			case JOptionPane.OK_OPTION: 
				try {
					appUserService.delete(loadedAppUser.getUserName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case JOptionPane.NO_OPTION: 
				//System.out.println("NO!");
				break;
			default:
				//System.out.println("Cancel!");
				break;
		}
		loadedAppUser = null;
		adminForm.getAppUserUserNameLabel().setText("");
		adminForm.getAppUserFirstNameTextField().setText("");
		adminForm.getAppUserLastNameTextField().setText("");
		adminForm.getAppUserRoleLabel().setText("");
		
		appUsers = appUserService.findAll();
		loadedAppUser = appUsers.get(0);
		loadAppUserList();
	}
	
	private void changeColorMethod(boolean forward) {		
		if(adminForm.getColorPickerLabel().getName() == "WHITE") {
			if(forward) {
				adminForm.getColorPickerLabel().setName("YELLOW");
				adminForm.getColorPickerLabel().setText("Sárga");
				adminForm.getColorPickerLabel().setForeground(ORANGE);
			}else {
				adminForm.getColorPickerLabel().setName("RED");
				adminForm.getColorPickerLabel().setText("Piros");
				adminForm.getColorPickerLabel().setForeground(PASTELRED);
			}
		}else if(adminForm.getColorPickerLabel().getName().equals("YELLOW")) {
			if(forward) {
				adminForm.getColorPickerLabel().setName("RED");
				adminForm.getColorPickerLabel().setText("Piros");
				adminForm.getColorPickerLabel().setForeground(PASTELRED);
			}else {
				adminForm.getColorPickerLabel().setName("WHITE");
				adminForm.getColorPickerLabel().setText("Fehér");
				adminForm.getColorPickerLabel().setForeground(LIGHTGRAY);
			}
		}else if(adminForm.getColorPickerLabel().getName().equals("RED")) {
			if(forward) {
				adminForm.getColorPickerLabel().setName("WHITE");
				adminForm.getColorPickerLabel().setText("Fehér");
				adminForm.getColorPickerLabel().setForeground(LIGHTGRAY);
			}else {
				adminForm.getColorPickerLabel().setName("YELLOW");
				adminForm.getColorPickerLabel().setText("Sárga");
				adminForm.getColorPickerLabel().setForeground(ORANGE);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private boolean checkExceptions() {
		boolean isItOK = true;
		adminForm.getUserPanelExceptionsLabel().setText("");

		if (adminForm.getAppUserFirstNameTextField().getText().length() < 2) {
			adminForm.getUserPanelExceptionsLabel().setText("Rövid keresznév (Min 2 karakter)!");
			isItOK = false;
		}
		if (adminForm.getAppUserFirstNameTextField().getText().length() > 40) {
			adminForm.getUserPanelExceptionsLabel().setText("Maximum 40 karakter!");
			isItOK = false;
		}
		
		if (adminForm.getAppUserLastNameTextField().getText().length() < 2) {
			adminForm.getUserPanelExceptionsLabel().setText("Rövid vezetéknév (Min 2 karakter)!");
			isItOK = false;
		}
		if (adminForm.getAppUserLastNameTextField().getText().length() > 40) {
			adminForm.getUserPanelExceptionsLabel().setText("Maximum 40 karakter!");
			isItOK = false;
		}
		
		if (adminForm.getNewPasswordTextField().getText().length() < 2 && createNewPassword) {
			adminForm.getUserPanelExceptionsLabel().setText("Rövid jelszó (Min 4 karakter)!");
			isItOK = false;
		}
		if (adminForm.getNewPasswordTextField().getText().length() > 20 && createNewPassword) {
			adminForm.getUserPanelExceptionsLabel().setText("Túl hosszú jelszó (Max 20 karakter)!");
			isItOK = false;
		}
		
		if (!adminForm.getNewPasswordTextField().getText().equals(adminForm.getNewPasswordRepeaterTextField().getText()) && createNewPassword) {
			adminForm.getUserPanelExceptionsLabel().setText("A megadott jelszavak nem egyeznek!");
			isItOK = false;
		}
		
		return isItOK;
	}
	
	private void changeAppUserRole() {
		switch (adminForm.getAppUserRoleLabel().getText()) {
		case "ADMIN":
			adminForm.getAppUserRoleLabel().setText("KASSZA");
			break;
		case "KASSZA":
			adminForm.getAppUserRoleLabel().setText("ÖLTÖZTETÉS");
			break;
		case "ÖLTÖZTETÉS":
			adminForm.getAppUserRoleLabel().setText("ADMIN");
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + loadedAppUser.getRole());
		}
	}
	
	private void updateStatisticsPanel() {
		//registered stats
		adminForm.getRegisteredWhiteWristbandsLabel().setText(Long.toString(registeredWristbandService.countByColor("WHITE")));
		adminForm.getRegisteredYellowWristbandsLabel().setText(Long.toString(registeredWristbandService.countByColor("YELLOW")));
		adminForm.getRegisteredRedWristbandsLabel().setText(Long.toString(registeredWristbandService.countByColor("RED")));
		
		adminForm.getActiveWhiteWristbandsLabel().setText(Long.toString(activeWristbandService.countByColor("WHITE")));
		adminForm.getActiveYellowWristbandsLabel().setText(Long.toString(activeWristbandService.countByColor("YELLOW")));
		adminForm.getActiveRedWristbandsLabel().setText(Long.toString(activeWristbandService.countByColor("RED")));
		
		numbersOfClosedWhites = closedWristbandService.countByColor("WHITE");
		numbersOfClosedYellows = closedWristbandService.countByColor("YELLOW");
		numbersOfClosedReds = closedWristbandService.countByColor("RED");
		Long all = numbersOfClosedWhites + numbersOfClosedYellows + numbersOfClosedReds;
		
		adminForm.getClosedWhiteWristbandsLabel().setText(Long.toString(numbersOfClosedWhites)); //+ "  (" +  (int)(numbersOfClosedWhites * 100.0 / all + 0.5) + "%)");
		adminForm.getClosedWhiteWristPercentLabel().setText((int)(numbersOfClosedWhites * 100.0 / all + 0.5) + "%");
		adminForm.getClosedYellowWristbandsLabel().setText(Long.toString(numbersOfClosedYellows)); // + "  (" +  (int)(numbersOfClosedYellows * 100.0 / all + 0.5) + "%)");
		adminForm.getClosedYellowWristPercentLabel().setText((int)(numbersOfClosedYellows * 100.0 / all + 0.5) + "%");
		adminForm.getClosedRedWristbandsLabel().setText(Long.toString(numbersOfClosedReds)); // + "  (" +  (int)(numbersOfClosedReds * 100.0 / all + 0.5) + "%)");
		adminForm.getClosedRedWristPercentLabel().setText((int)(numbersOfClosedReds * 100.0 / all + 0.5) + "%");
		
		adminForm.getClosedSumWristbandsLabel().setText(Long.toString(numbersOfClosedWhites + numbersOfClosedYellows + numbersOfClosedReds));

		adminForm.getNumberOfClosedWhite_0_Label().setText(Long.toString(closedWristbandService.countByPercentAndColor(0, "WHITE")));
		adminForm.getNumberOfClosedWhite_10_Label().setText(Long.toString(closedWristbandService.countByPercentAndColor(10, "WHITE")));
		adminForm.getNumberOfClosedWhite_20_Label().setText(Long.toString(closedWristbandService.countByPercentAndColor(20, "WHITE")));
		
		adminForm.getNumberOfClosedYellow_0_Label().setText(Long.toString(closedWristbandService.countByPercentAndColor(0, "YELLOW")));
		adminForm.getNumberOfClosedYellow_10_Label().setText(Long.toString(closedWristbandService.countByPercentAndColor(10, "YELLOW")));
		adminForm.getNumberOfClosedYellow_20_Label().setText(Long.toString(closedWristbandService.countByPercentAndColor(20, "YELLOW")));
		
		adminForm.getNumberOfClosedRed_0_Label().setText(Long.toString(closedWristbandService.countByPercentAndColor(0, "RED")));
		adminForm.getNumberOfClosedRed_10_Label().setText(Long.toString(closedWristbandService.countByPercentAndColor(10, "RED")));
		adminForm.getNumberOfClosedRed_20_Label().setText(Long.toString(closedWristbandService.countByPercentAndColor(20, "RED")));
		
		//average times
		Long numbersOfClosedWhitesInTime = closedWristbandService.countByColorIfItUnder5Hours("WHITE");
		Long numbersOfClosedYellowsInTime = closedWristbandService.countByColorIfItUnder5Hours("YELLOW");
		Long numbersOfClosedRedsInTime = closedWristbandService.countByColorIfItUnder5Hours("RED");
		adminForm.getAverageWhiteTimeLabel().setText(calculateAverageTime(closedWristbandService.sumSpentTime("WHITE"), numbersOfClosedWhitesInTime) + "");		
		adminForm.getAverageYellowTimeLabel().setText(calculateAverageTime(closedWristbandService.sumSpentTime("YELLOW"), numbersOfClosedYellowsInTime) + "");
		adminForm.getAverageRedTimeLabel().setText(calculateAverageTime(closedWristbandService.sumSpentTime("RED"), numbersOfClosedRedsInTime) + "");
		
		//overtime
		Long whiteOvertime = closedWristbandService.countOverTimeByColor("WHITE");
		Long yellowOvertime = closedWristbandService.countOverTimeByColor("YELLOW");
		Long redOvertime = closedWristbandService.countOverTimeByColor("RED");
		
		/*
		Long whiteOvertime = numbersOfClosedWhites - numbersOfClosedWhitesInTime;
		Long yellowOvertime = numbersOfClosedYellows - numbersOfClosedYellowsInTime;
		Long redOvertime = numbersOfClosedReds - numbersOfClosedRedsInTime;
		 */
		
		adminForm.getNumberOfWhiteOvertimeLabel().setText(Long.toString(whiteOvertime)); // + " (" + (int)(whiteOvertime * 100.0 / numbersOfClosedWhites + 0.5) + "%)");
		adminForm.getPercentOfWhiteOvertimeLabel().setText((int)(whiteOvertime * 100.0 / numbersOfClosedWhites + 0.5) + "%");
		adminForm.getNumberOfYellowOvertimeLabel().setText(Long.toString(yellowOvertime)); // + " (" + (int)(yellowOvertime * 100.0 / numbersOfClosedYellows + 0.5) + "%)");
		adminForm.getPercentOfYellowOvertimeLabel().setText((int)(yellowOvertime * 100.0 / numbersOfClosedYellows + 0.5) + "%");
		adminForm.getNumberOfRedOvertimeLabel().setText(Long.toString(redOvertime)); // + " (" + (int)(redOvertime * 100.0 / numbersOfClosedWhites + 0.5) + "%)");
		adminForm.getPercentOfRedOvertimeLabel().setText((int)(redOvertime * 100.0 / numbersOfClosedWhites + 0.5) + "%");
		
		adminForm.getNumberOfBadWeatherActivations().setText(Long.toString(badWeatherLogService.getNumberOfBadWeatherEvents()));		
		Long sumTime = badWeatherLogService.getSumOfBadWeatherTimes();
		NumberFormat numberFormatter = new DecimalFormat("00");
		String minutesString = numberFormatter.format((int)(sumTime % 3600 / 60));
		String secondsString = numberFormatter.format((int)(sumTime % 60));
		adminForm.getSumOfBadWeatherTimes().setText((sumTime / 3600) + ":" + minutesString + ":" + secondsString);
		adminForm.getSumOfCanceledWristbands().setText(badWeatherLogService.getNumberOfCanceledWristbands() + "");
		
		adminForm.repaint();
	}
	
	private void updatePeriodicStatistics() {
		if(adminForm.getDateChooserFrom().getDate() != null && adminForm.getDateChooserTo().getDate() != null) {
			Date dateTo = adminForm.getDateChooserTo().getDate();
			
			long periodicWhite = closedWristbandService.countByDateAndColor(adminForm.getDateChooserFrom().getDate(), getNextDay(dateTo), "WHITE");
			long periodicYellow = closedWristbandService.countByDateAndColor(adminForm.getDateChooserFrom().getDate(), getNextDay(dateTo), "YELLOW");
			long periodicRed = closedWristbandService.countByDateAndColor(adminForm.getDateChooserFrom().getDate(), getNextDay(dateTo), "RED"); 
			
			adminForm.getPeriodicWhiteWristbandsLabel().setText(periodicWhite + " db");
			adminForm.getPeriodicYellowWristbandsLabel().setText(periodicYellow + " db");
			adminForm.getPeriodicRedWristbandsLabel().setText(periodicRed + " db");
			
			adminForm.getPeriodicWhiteWristbandsLabel_2().setText((periodicWhite + periodicYellow + periodicRed) + " db");
			
			/*
			adminForm.getPeriodicWhiteWristbandsLabel().setText(closedWristbandService.countByDateAndColor(adminForm.getDateChooserFrom().getDate(), getNextDay(dateTo), "WHITE") + " db");
			adminForm.getPeriodicYellowWristbandsLabel().setText(closedWristbandService.countByDateAndColor(adminForm.getDateChooserFrom().getDate(), getNextDay(dateTo), "YELLOW") + " db");
			adminForm.getPeriodicRedWristbandsLabel().setText(closedWristbandService.countByDateAndColor(adminForm.getDateChooserFrom().getDate(), getNextDay(dateTo), "RED") + " db");
			 */
		}else if(adminForm.getDateChooserFrom().getDate() != null || adminForm.getDateChooserTo().getDate() != null) { 
			Date date;
			if (adminForm.getDateChooserFrom().getDate() != null) 
				date = adminForm.getDateChooserFrom().getDate();
			else
				date = adminForm.getDateChooserTo().getDate();

			long periodicWhite = closedWristbandService.countByDateAndColor(date, getNextDay(date), "WHITE");
			long periodicYellow = closedWristbandService.countByDateAndColor(date, getNextDay(date), "YELLOW");
			long periodicRed = closedWristbandService.countByDateAndColor(date, getNextDay(date), "RED"); 
			
			adminForm.getPeriodicWhiteWristbandsLabel().setText(periodicWhite + " db");
			adminForm.getPeriodicYellowWristbandsLabel().setText(periodicYellow + " db");
			adminForm.getPeriodicRedWristbandsLabel().setText(periodicRed + " db");
			
			adminForm.getPeriodicWhiteWristbandsLabel_2().setText((periodicWhite + periodicYellow + periodicRed) + " db");
			
			/*
			adminForm.getPeriodicWhiteWristbandsLabel().setText(closedWristbandService.countByDateAndColor(date, getNextDay(date), "WHITE") + " db");
			adminForm.getPeriodicYellowWristbandsLabel().setText(closedWristbandService.countByDateAndColor(date, getNextDay(date), "YELLOW") + " db");
			adminForm.getPeriodicRedWristbandsLabel().setText(closedWristbandService.countByDateAndColor(date, getNextDay(date), "RED") + " db");
			 */
		}else {	
			adminForm.getPeriodicWhiteWristbandsLabel().setText("");
			adminForm.getPeriodicYellowWristbandsLabel().setText("");
			adminForm.getPeriodicRedWristbandsLabel().setText("");
		}
	}
	
	private void updatePeriodicBadWeatherStatistics() {
		if(adminForm.getDateChooserFrom().getDate() != null && adminForm.getDateChooserTo().getDate() != null) {
			Date dateTo = adminForm.getDateChooserTo().getDate();
			
			adminForm.getPeriodicWhiteWristbandsLabel().setText(badWeatherLogService.countByDate(adminForm.getDateChooserFrom().getDate(), getNextDay(dateTo)) + " alkalom");
			
			Long sumTime = badWeatherLogService.getSumOfBadWeatherTimesByDate(adminForm.getDateChooserFrom().getDate(), getNextDay(dateTo));
			NumberFormat numberFormatter = new DecimalFormat("00");
			String minutesString = numberFormatter.format((int)(sumTime % 3600 / 60));
			String secondsString = numberFormatter.format((int)(sumTime % 60));
			adminForm.getPeriodicYellowWristbandsLabel().setText((sumTime / 3600) + ":" + minutesString + ":" + secondsString);			
			
			adminForm.getPeriodicRedWristbandsLabel().setText(badWeatherLogService.getNumberOfCanceledWristbandsByDate(adminForm.getDateChooserFrom().getDate(), getNextDay(dateTo)) + " db");			
		}else if(adminForm.getDateChooserFrom().getDate() != null || adminForm.getDateChooserTo().getDate() != null) {  //}else if (adminForm.getDateChooserFrom().getDate() != null) {
			Date date;
			if (adminForm.getDateChooserFrom().getDate() != null) 
				date = adminForm.getDateChooserFrom().getDate();
			else
				date = adminForm.getDateChooserTo().getDate();

			adminForm.getPeriodicWhiteWristbandsLabel().setText(badWeatherLogService.countByDate(date, getNextDay(date)) + " alkalom");
			
			Long sumTime = badWeatherLogService.getSumOfBadWeatherTimesByDate(date, getNextDay(date));
			NumberFormat numberFormatter = new DecimalFormat("00");
			String minutesString = numberFormatter.format((int)(sumTime % 3600 / 60));
			String secondsString = numberFormatter.format((int)(sumTime % 60));
			adminForm.getPeriodicYellowWristbandsLabel().setText((sumTime / 3600) + ":" + minutesString + ":" + secondsString);
			
			adminForm.getPeriodicRedWristbandsLabel().setText(badWeatherLogService.getNumberOfCanceledWristbandsByDate(date, getNextDay(date)) + " db");
		} else {		
			adminForm.getPeriodicWhiteWristbandsLabel().setText("");
			adminForm.getPeriodicYellowWristbandsLabel().setText("");
			adminForm.getPeriodicRedWristbandsLabel().setText("");
		}
	}
	
	public void changePeriodicPanelAndFolder(boolean firstFolder) {
		adminForm.getPeriodicWhiteWristbandsLabel().setText("");
		adminForm.getPeriodicYellowWristbandsLabel().setText("");
		adminForm.getPeriodicRedWristbandsLabel().setText("");
		
		if(firstFolder) {
			adminForm.getPeriodicClosedFolderLabel().setBackground(FOLDERGREEN);				
			adminForm.getPeriodicClosedFolderLabel().setBounds(40, 350, 140, 40);
			adminForm.getPeriodicBadWeatherFolderLabel().setBackground(SHADOW);
			adminForm.getPeriodicBadWeatherFolderLabel().setBounds(190, 354, 160, 36);
			
			adminForm.getPeriodicInfoLabel().setText("Lezárt szalagok száma");
			adminForm.getPeriodicWhiteWristbandsLabel().setBackground(PASTELWHITE);
			adminForm.getPeriodicYellowWristbandsLabel().setBackground(MACAROON);
			adminForm.getPeriodicRedWristbandsLabel().setBackground(PASTELRED);	
			
			adminForm.getPeriodicInfoLabel_2().setVisible(true);
			adminForm.getPeriodicWhiteWristbandsLabel_2().setVisible(true);
			
			isPeriodicFolderClosed = true;
		}else {
			adminForm.getPeriodicBadWeatherFolderLabel().setBackground(FOLDERGREEN);
			adminForm.getPeriodicClosedFolderLabel().setBounds(40, 354, 140, 36);
			adminForm.getPeriodicClosedFolderLabel().setBackground(SHADOW);
			adminForm.getPeriodicBadWeatherFolderLabel().setBounds(190, 350, 160, 40);
			
			adminForm.getPeriodicInfoLabel().setText("Aktíválások száma          Összes idõ            Érintett szalagok");
			adminForm.getPeriodicWhiteWristbandsLabel().setBackground(PASTELBLUE);
			adminForm.getPeriodicYellowWristbandsLabel().setBackground(PASTELBLUE);
			adminForm.getPeriodicRedWristbandsLabel().setBackground(PASTELBLUE);
			
			adminForm.getPeriodicInfoLabel_2().setVisible(false);
			adminForm.getPeriodicWhiteWristbandsLabel_2().setVisible(false);
			
			isPeriodicFolderClosed = false;
		}
	}
	
	private Date getNextDay(Date date) {
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.setTime(date);			
		tomorrow.set(Calendar.HOUR, 0);
		tomorrow.set(Calendar.MINUTE, 0);
		tomorrow.set(Calendar.SECOND, 0);
		
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);		
		return tomorrow.getTime();
	}
	
	@SuppressWarnings("deprecation")
	private Time calculateAverageTime(Long seconds, Long division) {
		if(division != 0) {
			int averageSeconds = (int)(seconds / division);
			return new Time(averageSeconds/3600, averageSeconds % 3600 / 60, averageSeconds % 60);
		} else {
			return new Time(0, 0, 0);
		}
	}

	private void adminExitProcess() {
		model.persistLoggedInUserActivity();
		settingsDataService.update(currentSettings);
		clockTimer.stop();		
	}
	
	public MouseListener getUserPanelMouseListener( ) {
		return new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent e) {	}			
			@Override
			public void mousePressed(MouseEvent e) {
				JLabel source = ((JLabel) e.getSource());
				loadedAppUser = appUserService.findByUserName(source.getName());
				fillAppUserData();
				loadAppUserList();
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				if(!((JLabel)e.getSource()).getText().equals(loadedAppUser.getUserName())) {
					((JLabel)e.getSource()).setBorder(null);
				}
			}			
			@Override
			public void mouseEntered(MouseEvent e) {
				((JLabel)e.getSource()).setBorder(new LineBorder(ORANGE, 3));
			}			
			@Override
			public void mouseClicked(MouseEvent e) {	}
		};
	}

	@Override
	public void mouseClicked(MouseEvent e) {	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(adminForm.getSignOutLabel().isVisible() && e.getSource() != adminForm.getMenuIconLabel()) {
			adminForm.getSignOutLabel().setVisible(false);
		}
		if (e.getSource() == adminForm.getMenuIconLabel()) {
			adminForm.getSignOutLabel().setVisible(!adminForm.getSignOutLabel().isVisible());
		}
		if (e.getSource() == adminForm.getSignOutLabel()) {
			adminExitProcess();
			controller.initailize();
		}		
		
		if(e.getSource() == adminForm.getWristbandFolderLabel() && !isWristbandRegistryPanel) {
			isWristbandRegistryPanel = true;
			isManageUser = isCheckStatistics = false;
			changeFolderLabels();
		}
		if(e.getSource() == adminForm.getUsersFolderLabel() && !isManageUser) {
			isManageUser = true;
			isWristbandRegistryPanel = isCheckStatistics = false;
			settingsDataService.update(currentSettings);
			changeFolderLabels();
		}
		if(e.getSource() == adminForm.getStatisticsFolderLabel() && !isCheckStatistics) {
			isCheckStatistics = true;
			isManageUser = isWristbandRegistryPanel = false;
			settingsDataService.update(currentSettings);
			changeFolderLabels();
		}		
		
		if(e.getSource() == adminForm.getColorPickerLabel()) {				
			if (e.getButton() == MouseEvent.BUTTON1)
				changeColorMethod(true);
			if (e.getButton() == MouseEvent.BUTTON3)
				changeColorMethod(false);
		}
		
		if(e.getSource() == adminForm.getAmountOfChildSingingLabel()) {
			if (e.getButton() == MouseEvent.BUTTON1 && currentSettings.getChildSinging() < 1000) {
				currentSettings.setChildSinging(currentSettings.getChildSinging() + 1);
			}
			if (e.getButton() == MouseEvent.BUTTON3 && currentSettings.getChildSinging() > 0) {
				currentSettings.setChildSinging(currentSettings.getChildSinging() - 1);
			}
			
			adminForm.getAmountOfChildSingingLabel().setText(Integer.toString(currentSettings.getChildSinging()));
		}
		if(e.getSource() == adminForm.getAmountOfSeniorSingingLabel()) {
			if (e.getButton() == MouseEvent.BUTTON1 && currentSettings.getSeniorSinging() < 1000) {
				currentSettings.setSeniorSinging(currentSettings.getSeniorSinging() + 1);
			}
			if (e.getButton() == MouseEvent.BUTTON3 && currentSettings.getSeniorSinging() > 0) {
				currentSettings.setSeniorSinging(currentSettings.getSeniorSinging() - 1);
			}
			
			adminForm.getAmountOfSeniorSingingLabel().setText(Integer.toString(currentSettings.getSeniorSinging()));
		}
		
		if(e.getSource() == adminForm.getAmountOfReservedChildSingingLabel()) {
			if (e.getButton() == MouseEvent.BUTTON1 && currentSettings.getReservedChildSinging() < currentSettings.getChildSinging()) {
				currentSettings.setReservedChildSinging(currentSettings.getReservedChildSinging() + 1);
			}
			if (e.getButton() == MouseEvent.BUTTON3 && currentSettings.getReservedChildSinging() > 0) {
				currentSettings.setReservedChildSinging(currentSettings.getReservedChildSinging() - 1);
			}
			
			adminForm.getAmountOfReservedChildSingingLabel().setText(Integer.toString(currentSettings.getReservedChildSinging()));
		}
		if(e.getSource() == adminForm.getAmountOfReservedSeniorSingingLabel()) {
			if (e.getButton() == MouseEvent.BUTTON1 && currentSettings.getReservedSeniorSinging() < currentSettings.getSeniorSinging()) {
				currentSettings.setReservedSeniorSinging(currentSettings.getReservedSeniorSinging() + 1);
			}
			if (e.getButton() == MouseEvent.BUTTON3 && currentSettings.getReservedSeniorSinging() > 0) {
				currentSettings.setReservedSeniorSinging(currentSettings.getReservedSeniorSinging() - 1);
			}
			
			adminForm.getAmountOfReservedSeniorSingingLabel().setText(Integer.toString(currentSettings.getReservedSeniorSinging()));
		}
		
		//AppUser Panel		
		if(e.getSource() == adminForm.getNewPasswordLabel()) {
			createNewPassword = !createNewPassword;
			adminForm.getAdminDrawPanel().showPasswordFields();			
			if(createNewPassword) {
				adminForm.getNewPasswordLabel().setForeground(FOLDERGREENON);
				adminForm.getNewPasswordLabel().setText("Nem módosítom");
				
				adminForm.getNewPasswordLabel_1().setVisible(true);
				adminForm.getNewPasswordLabel_2().setVisible(true);
				adminForm.getNewPasswordTextField().setVisible(true);
				adminForm.getNewPasswordRepeaterTextField().setVisible(true);
			}else {
				adminForm.getNewPasswordLabel().setForeground(ORANGE);
				adminForm.getNewPasswordLabel().setText("Jelszó módósítása");
				
				adminForm.getNewPasswordTextField().setText("");
				adminForm.getNewPasswordRepeaterTextField().setText("");
				adminForm.getNewPasswordLabel_1().setVisible(false);
				adminForm.getNewPasswordLabel_2().setVisible(false);
				adminForm.getNewPasswordTextField().setVisible(false);
				adminForm.getNewPasswordRepeaterTextField().setVisible(false);
			}
			adminForm.repaint();
		}
		if(e.getSource() == adminForm.getAppUserDataModifierButtonLabel()) {
			if(loadedAppUser != null && checkExceptions()) {
				updateAppUser();
				
				adminForm.getNewPasswordTextField().setText("");
				adminForm.getNewPasswordRepeaterTextField().setText("");
			}
			adminForm.repaint();
		}
		if(e.getSource() == adminForm.getAppUserEraseButtonLabel()) {
			if(loadedAppUser != null) {
				adminForm.repaint();
				eraseAppUser();
			}
		}
		if(e.getSource() == adminForm.getAppUserRoleLabel()) {			
			changeAppUserRole();
		}
		
		
		//Statistic Panel	
		if(e.getSource() == adminForm.getUpdateButtonLabel()) {
			updateStatisticsPanel();
		}
		
		if(e.getSource() == adminForm.getPeriodicDataQueryButtonLabel()) {
			if(isPeriodicFolderClosed)
				updatePeriodicStatistics();
			else
				updatePeriodicBadWeatherStatistics();
		}
		
		if(e.getSource() == adminForm.getClearActiveWristbandListButtonLabel()) {
			if(showCustomJOptionPane("Biztosan törölni szeretné az aktív szalagok tábláját?") == JOptionPane.OK_OPTION) {				
				activeWristbandService.deleteAll();
				updateStatisticsPanel();
			}else {
				//System.out.println("Cancel!");
			}			
		}
		
		if(e.getSource() == adminForm.getPeriodicClosedFolderLabel()) {
			if(!isPeriodicFolderClosed) {
				changePeriodicPanelAndFolder(true);
				adminForm.repaint();
			}			
		}
		if(e.getSource() == adminForm.getPeriodicBadWeatherFolderLabel()) {
			if(isPeriodicFolderClosed) {
				changePeriodicPanelAndFolder(false);
				adminForm.repaint();
			}			
		}		
		//adminForm.repaint();
	}	

	@Override
	public void mouseReleased(MouseEvent e) {	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == adminForm.getMenuIconLabel()) {
			adminForm.getMenuIconLabel().setIcon(new ImageIcon(getClass().getClassLoader().getResource("menu_gray.png")));
		}
		if(e.getSource() == adminForm.getSignOutLabel()) {
			adminForm.getSignOutLabel().setForeground(LIGHTGRAY);
			adminForm.getSignOutLabel().setFont(activeFont);
		}
		if(e.getSource() == adminForm.getWristbandFolderLabel() && !isWristbandRegistryPanel) {
			adminForm.getWristbandFolderLabel().setBackground(FOLDERGREENON);
		}
		if(e.getSource() == adminForm.getUsersFolderLabel() && !isManageUser) {
			adminForm.getUsersFolderLabel().setBackground(FOLDERGREENON);
		}
		if(e.getSource() == adminForm.getStatisticsFolderLabel() && !isCheckStatistics) {
			adminForm.getStatisticsFolderLabel().setBackground(FOLDERGREENON);
		}
		
		if(e.getSource() == adminForm.getColorPickerLabel()) {
			adminForm.getColorPickerLabel().setBackground(LIGHTBROWN);
		}		
		if(e.getSource() == adminForm.getBarCodeRegistryButton()) {
			adminForm.getBarCodeRegistryButton().setBackground(LIGHTBROWN);
		}
		if(e.getSource() == adminForm.getAmountOfWristbandsTextField()) {
			adminForm.getAmountOfWristbandsTextField().setBorder(new LineBorder(ORANGE, 2));
		}
		if(e.getSource() == adminForm.getAmountOfChildSingingLabel()) {
			adminForm.getAmountOfChildSingingLabel().setBorder(new LineBorder(PASTELWHITE, 2));
		}
		if(e.getSource() == adminForm.getAmountOfSeniorSingingLabel()) {
			adminForm.getAmountOfSeniorSingingLabel().setBorder(new LineBorder(MACAROON, 2));
		}
		if(e.getSource() == adminForm.getAmountOfReservedChildSingingLabel()) {
			adminForm.getAmountOfReservedChildSingingLabel().setBorder(new LineBorder(PASTELWHITE, 2));
		}
		if(e.getSource() == adminForm.getAmountOfReservedSeniorSingingLabel()) {
			adminForm.getAmountOfReservedSeniorSingingLabel().setBorder(new LineBorder(MACAROON, 2));
		}
		
		//AppUser Panel listeners
		if(e.getSource() == adminForm.getAppUserDataModifierButtonLabel()) {
			adminForm.getAppUserDataModifierButtonLabel().setBackground(ORANGE);
			adminForm.getAppUserDataModifierButtonLabel().setBorder(new LineBorder(ORANGE, 2));
		}
		if(e.getSource() == adminForm.getNewPasswordLabel()) {
			adminForm.getNewPasswordLabel().setForeground(Color.WHITE);
		}
		if(e.getSource() == adminForm.getAppUserEraseButtonLabel()) {
			adminForm.getAppUserEraseButtonLabel().setIcon(new ImageIcon(getClass().getClassLoader().getResource("delete_icon_50.png")));
			adminForm.getAppUserEraseButtonLabel().setBounds(595, 595, 50, 50);
			adminForm.repaint();
		}
		if(e.getSource() == adminForm.getAppUserRoleLabel()) {			
			adminForm.getAppUserRoleLabel().setBackground(ORANGE);
			adminForm.getAppUserRoleLabel().setBorder(new LineBorder(ORANGE, 2));
		}
		
		if(e.getSource() == adminForm.getUpdateButtonLabel()) {			
			adminForm.getUpdateButtonLabel().setBackground(ORANGE);
			adminForm.getUpdateButtonLabel().setBorder(new LineBorder(ORANGE, 2));
		}
		
		if(e.getSource() == adminForm.getPeriodicDataQueryButtonLabel()) {
			adminForm.getPeriodicDataQueryButtonLabel().setBackground(ORANGE);
			adminForm.getPeriodicDataQueryButtonLabel().setBorder(new LineBorder(ORANGE, 2));
		}
		
		if(e.getSource() == adminForm.getClearActiveWristbandListButtonLabel()) {
			adminForm.getClearActiveWristbandListButtonLabel().setIcon(new ImageIcon(getClass().getClassLoader().getResource("delete_icon_50.png")));
			adminForm.getClearActiveWristbandListButtonLabel().setBounds(625, 147, 50, 50);
			adminForm.repaint();
		}
		
		if(e.getSource() == adminForm.getPeriodicClosedFolderLabel() && !isPeriodicFolderClosed) {
			adminForm.getPeriodicClosedFolderLabel().setBackground(FOLDERGREENON);
		}
		if(e.getSource() == adminForm.getPeriodicBadWeatherFolderLabel() && isPeriodicFolderClosed) {
			adminForm.getPeriodicBadWeatherFolderLabel().setBackground(FOLDERGREENON);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == adminForm.getMenuIconLabel()) {
			adminForm.getMenuIconLabel().setIcon(new ImageIcon(getClass().getClassLoader().getResource("menu_icon.png")));
		}
		if(e.getSource() == adminForm.getSignOutLabel()) {
			adminForm.getSignOutLabel().setForeground(Color.WHITE);
			adminForm.getSignOutLabel().setFont(normalFont);
		}
		if(e.getSource() == adminForm.getWristbandFolderLabel() && !isWristbandRegistryPanel) {
			adminForm.getWristbandFolderLabel().setBackground(FOLDERGREEN);
		}
		if(e.getSource() == adminForm.getUsersFolderLabel() && !isManageUser) {
			adminForm.getUsersFolderLabel().setBackground(FOLDERGREEN);
		}
		if(e.getSource() == adminForm.getStatisticsFolderLabel() && !isCheckStatistics) {
			adminForm.getStatisticsFolderLabel().setBackground(FOLDERGREEN);
		}
		
		if(e.getSource() == adminForm.getColorPickerLabel()) {
			adminForm.getColorPickerLabel().setBackground(DARKBROWN);
		}	
		if(e.getSource() == adminForm.getBarCodeRegistryButton()) {
			adminForm.getBarCodeRegistryButton().setBackground(DARKBROWN);
		}
		if(e.getSource() == adminForm.getAmountOfWristbandsTextField()) {
			adminForm.getAmountOfWristbandsTextField().setBorder(new LineBorder(Color.WHITE, 2));
		}
		if(e.getSource() == adminForm.getAmountOfChildSingingLabel()) {
			adminForm.getAmountOfChildSingingLabel().setBorder(new LineBorder(GRAPHITE, 2));
		}
		if(e.getSource() == adminForm.getAmountOfSeniorSingingLabel()) {
			adminForm.getAmountOfSeniorSingingLabel().setBorder(new LineBorder(GRAPHITE, 2));
		}
		if(e.getSource() == adminForm.getAmountOfReservedChildSingingLabel()) {
			adminForm.getAmountOfReservedChildSingingLabel().setBorder(new LineBorder(GRAPHITE, 2));
		}
		if(e.getSource() == adminForm.getAmountOfReservedSeniorSingingLabel()) {
			adminForm.getAmountOfReservedSeniorSingingLabel().setBorder(new LineBorder(GRAPHITE, 2));
		}
		
		//AppUser Panel listeners
		if(e.getSource() == adminForm.getAppUserDataModifierButtonLabel()) {
			adminForm.getAppUserDataModifierButtonLabel().setBackground(DARKBROWN);
			adminForm.getAppUserDataModifierButtonLabel().setBorder(new LineBorder(Color.WHITE, 2));
		}
		if(e.getSource() == adminForm.getNewPasswordLabel()) {
			if(createNewPassword)
				adminForm.getNewPasswordLabel().setForeground(FOLDERGREENON);
			else
				adminForm.getNewPasswordLabel().setForeground(ORANGE);
		}
		if(e.getSource() == adminForm.getAppUserEraseButtonLabel()) {
			adminForm.getAppUserEraseButtonLabel().setIcon(new ImageIcon(getClass().getClassLoader().getResource("delete_icon.png")));
			adminForm.getAppUserEraseButtonLabel().setBounds(600, 600, 40, 40);
			adminForm.repaint();
		}
		if(e.getSource() == adminForm.getUpdateButtonLabel()) {			
			adminForm.getUpdateButtonLabel().setBackground(DARKBROWN);
			adminForm.getUpdateButtonLabel().setBorder(new LineBorder(Color.WHITE, 2));
		}
				
		//Statistics
		if(e.getSource() == adminForm.getAppUserRoleLabel()) {			
			adminForm.getAppUserRoleLabel().setBackground(DARKBROWN);
			adminForm.getAppUserRoleLabel().setBorder(new LineBorder(Color.WHITE, 2));
		}
		
		if(e.getSource() == adminForm.getPeriodicDataQueryButtonLabel()) {
			adminForm.getPeriodicDataQueryButtonLabel().setBackground(DARKBROWN);
			adminForm.getPeriodicDataQueryButtonLabel().setBorder(new LineBorder(Color.WHITE, 2));
			adminForm.repaint();
		}
		
		if(e.getSource() == adminForm.getClearActiveWristbandListButtonLabel()) {
			adminForm.getClearActiveWristbandListButtonLabel().setIcon(new ImageIcon(getClass().getClassLoader().getResource("delete_icon.png")));
			adminForm.getClearActiveWristbandListButtonLabel().setBounds(630, 152, 40, 40);
			adminForm.repaint();
		}
		
		if(e.getSource() == adminForm.getPeriodicClosedFolderLabel() && !isPeriodicFolderClosed) {
				adminForm.getPeriodicClosedFolderLabel().setBackground(SHADOW);
		}
		
		if(e.getSource() == adminForm.getPeriodicBadWeatherFolderLabel() && isPeriodicFolderClosed) {
				adminForm.getPeriodicBadWeatherFolderLabel().setBackground(SHADOW);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == adminForm.getBarCodeRegistryButton()) {
			if(adminForm.getBarCodeRegistryTextField().getText().length() != 0 ) {
				startRegistrationProcedure();
			} else {
				adminForm.getExceptionLabel().setText("");
				adminForm.repaint();
			}
		}
		
		if(e.getSource() == adminForm.getBarCodeRegistryTextField()) {
			scanAndCheckBarCode();
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getSource() == adminForm.getAppUserRoleLabel()) {
				changeAppUserRole();
		}
		
		if(e.getSource() == adminForm.getAmountOfChildSingingLabel()) {
			int notches = e.getWheelRotation();
			if (notches > 0) {
				if(currentSettings.getChildSinging() > 0)
					currentSettings.setChildSinging(currentSettings.getChildSinging() - 1);				
				
			}else {				
				if(currentSettings.getChildSinging() < 1000)
					currentSettings.setChildSinging(currentSettings.getChildSinging() + 1);
			}
			adminForm.getAmountOfChildSingingLabel().setText(Integer.toString(currentSettings.getChildSinging()));
		}		
		if(e.getSource() == adminForm.getAmountOfSeniorSingingLabel()) {
			int notches = e.getWheelRotation();
			if (notches > 0) {
				if(currentSettings.getSeniorSinging() > 0)
					currentSettings.setSeniorSinging(currentSettings.getSeniorSinging() - 1);			
			}else {				
				if(currentSettings.getSeniorSinging() < 1000)
					currentSettings.setSeniorSinging(currentSettings.getSeniorSinging() + 1);	
			}
			adminForm.getAmountOfSeniorSingingLabel().setText(Integer.toString(currentSettings.getSeniorSinging()));
		}
		
		if(e.getSource() == adminForm.getAmountOfReservedChildSingingLabel()) {
			int notches = e.getWheelRotation();
			if (notches > 0) {
				if (currentSettings.getReservedChildSinging() > 0) {
					currentSettings.setReservedChildSinging(currentSettings.getReservedChildSinging() - 1);
				}
			}else {
				if (currentSettings.getReservedChildSinging() < currentSettings.getChildSinging()) {
					currentSettings.setReservedChildSinging(currentSettings.getReservedChildSinging() + 1);				
				}				
			}
			adminForm.getAmountOfReservedChildSingingLabel().setText(Integer.toString(currentSettings.getReservedChildSinging()));
		}
		if(e.getSource() == adminForm.getAmountOfReservedSeniorSingingLabel()) {
			int notches = e.getWheelRotation();
			if (notches > 0) {
				if (currentSettings.getReservedSeniorSinging() > 0) {
					currentSettings.setReservedSeniorSinging(currentSettings.getReservedSeniorSinging() - 1);
				}
			}else {
				if (currentSettings.getReservedSeniorSinging() < currentSettings.getSeniorSinging()) {
					currentSettings.setReservedSeniorSinging(currentSettings.getReservedSeniorSinging() + 1);				
				}				
			}
			adminForm.getAmountOfReservedSeniorSingingLabel().setText(Integer.toString(currentSettings.getReservedSeniorSinging()));
		}
	}
}
