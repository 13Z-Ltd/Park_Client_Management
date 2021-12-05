package controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import model.Model;
import model.entities.ActiveWristband;
import model.entities.ClosedWristband;
import model.entities.SettingsData;
import model.services.ActiveWristbandService;
import model.services.ClosedWristbandService;
import model.services.SettingsDataService;
import view.View;
import view.forms.DressingForm;

public class DressingFormController implements ActionListener, MouseListener {
	private View view;
	private Model model;
	private Controller controller;	
	private DressingForm dressingForm;
	
	private ActiveWristbandService activeWristbandService;
	private ClosedWristbandService closedWristbandService;
	private SettingsDataService settingsDataService;
	private SettingsData currentSettings;
	
	private final int REFRESHING_INTERVAL = 20000; //20sec
	private Timer clockTimer;
	private Timer deleteTimer;
	private Timer wristbandClosingTimer;
	private boolean rainyIconIsOn = false;
	private int wristbandClosingSeconds = 0;
			
	private ActiveWristband checkedActiveWristband;
	private ActiveWristband wristbandToBeClosed;
	private LocalDateTime currentDateTime;
	private Time netTime;
	private Time badWeatherTime;
	private int extraCharges;
	private int userActivityClosedNumbers;
		
	private KeyAdapter buttonKeyAdapter;
	
	private Color DARKBROWN = new Color(55, 45, 40);
	private Color ORANGE = new Color(251, 182, 0);
	private Color GRASSGREEN = new Color(150, 169, 56);
	private Color BACKGROUNDGRAY = new Color(200, 200, 200);
	private Color BEIGE = new Color(239, 231, 219);	
	
	Font normalFont = new Font("Verdana", Font.PLAIN, 20);
	Font activeFont = new Font("Verdana", Font.BOLD, 20);
	
	public DressingFormController(View getView, Model getModel, Controller controller) {
		this.view = getView;
		this.model = getModel;
		this.controller = controller;
		activeWristbandService = new ActiveWristbandService();
		closedWristbandService = new ClosedWristbandService();
		settingsDataService = new SettingsDataService();		
		
		dressingForm = (DressingForm) view.getCurrentView();		
		currentSettings = settingsDataService.findById(1);
		
		userActivityClosedNumbers = 0;
		
		dressingForm.getMenuIconLabel().addMouseListener(this);
		dressingForm.getSignOutLabel().addMouseListener(this);
		dressingForm.getBarCodeReceiverButton().addActionListener(this);
		dressingForm.getWristbandClosingButton().addActionListener(this);
		dressingForm.getBarCodeReceiverTextField().addActionListener(this);

		dressingForm.getBarCodeReceiverButton().addMouseListener(this);
		dressingForm.getWristbandClosingButton().addMouseListener(this);
		
		buttonKeyAdapter = getKeyAdapter(); 
		dressingForm.getBarCodeReceiverButton().addKeyListener(buttonKeyAdapter);
		dressingForm.getWristbandClosingButton().addKeyListener(buttonKeyAdapter);
		
		dressingForm.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	if(activeWristbandService.countActive() > 0) {
					if(showExitDialogWhenHaveActiveWrist() == JOptionPane.YES_OPTION){
						model.setUserActivityClosedNumber(userActivityClosedNumbers);
						model.persistLoggedInUserActivity();
						clockTimer.stop();
						System.exit(0);
					}
		    	}else {		    	
					if(view.showExitDialog() == JOptionPane.YES_OPTION) {
						model.setUserActivityClosedNumber(userActivityClosedNumbers);
						model.persistLoggedInUserActivity();
						clockTimer.stop();
						System.exit(0);
					}
		    	}
		    }
		});

		startTimer();
		updateStatistics();
	}	
	
	private void wristbandSearchingProcedure() {
		setDataLabelsVisible(false);
		dressingForm.getExtraChargesLabel().setText("");
		dressingForm.getBadWeatherElapsedTimeLabel().setText("");
		
		if (dressingForm.getBarCodeReceiverTextField().getText().length() == 13) {			
			long barCode = 0;
			try {
				barCode = Long.parseLong(dressingForm.getBarCodeReceiverTextField().getText());
				
				checkedActiveWristband = activeWristbandService.findById(barCode);
				
				if (checkedActiveWristband != null) {					
					if(checkedActiveWristband.getActivatingDateTime() == null) {
						stopWristbandClosingTimer();
						
						setStartingTimeForCheckedWristband();
						startNewDeleteTimer(1);
						updateStatistics();						
					}else {
						if(wristbandToBeClosed != null && wristbandToBeClosed.getBarCode() == checkedActiveWristband.getBarCode()) {
							//wristbandClosingSeconds = 0;
							//wristbandToBeClosed = null;
							//stopWristbandClosingTimer();							
							wristbandClosingProcedure();
						}else {
							stopDeleteTimer();
							
							dressingForm.getCustomWristToDraw().setCustomWristbandLabel(checkedActiveWristband);
							dressingForm.getCustomWristToDraw().setVisible(true);
							
							dressingForm.getCheckLabel().setVisible(false);
							setDataLabelsVisible(true);
							showWristbandData(checkedActiveWristband);
							
							wristbandToBeClosed = checkedActiveWristband;
							startNewWristbandClosingTimer();
							//stopDeleteTimer();
						}
					}
					//dressingForm.getBarCodeReceiverTextField().setText("");
				}else {
					wristbandToBeClosed = null;
					wristbandClosingSeconds = 0;
					
					ClosedWristband closedWristband = closedWristbandService.findById(barCode);
					if(closedWristband != null) {
						dressingForm.getExceptionLabel().setText("A \"" + barCode + "\" számú karszalag már le lett zárva!");	
					}else {
						dressingForm.getExceptionLabel().setText("Nincs ilyen aktív karszalag! \"" + dressingForm.getBarCodeReceiverTextField().getText() + "\"");	
					}				
					dressingForm.getExceptionLabel().setVisible(true);										
					dressingForm.getCheckLabel().setVisible(true);
					
					startNewDeleteTimer(1);
				}
			}catch (Exception ex) {				
				//ex.printStackTrace();
				dressingForm.getExceptionLabel().setText("Nem megfelelõ formátum! \"" + dressingForm.getBarCodeReceiverTextField().getText() + "\"");
				dressingForm.getExceptionLabel().setVisible(true);
				
				dressingForm.getCustomWristToDraw().setVisible(false);
				dressingForm.getCheckLabel().setVisible(true);
				
				stopWristbandClosingTimer();
				//wristbandToBeClosed = null;
				//wristbandClosingSeconds = 0;
			}
		} else if (dressingForm.getBarCodeReceiverTextField().getText().length() > 0){
			dressingForm.getExceptionLabel().setText("13 karakterbõl álló vonalkódot kell megadni! \"" + dressingForm.getBarCodeReceiverTextField().getText() + "\"");
			dressingForm.getExceptionLabel().setVisible(true);
			
			dressingForm.getCustomWristToDraw().setVisible(false);
			dressingForm.getCheckLabel().setVisible(true);
			
			startNewDeleteTimer(2);
			
			stopWristbandClosingTimer();
			//wristbandToBeClosed = null;
			//wristbandClosingSeconds = 0;
		}
		
		//??
		dressingForm.getBarCodeReceiverTextField().setText("");
	}
	
	private void stopWristbandClosingTimer() {
		if(wristbandClosingTimer != null) {
			wristbandClosingTimer.stop();
		}
		wristbandToBeClosed = null;
		wristbandClosingSeconds = 0;
		
		dressingForm.getWristbandClosingTimeLabel().setText("");
	}
	
	private void setDataLabelsVisible(boolean b) {
		dressingForm.getStartDateLabel().setVisible(b);
		dressingForm.getStopDateLabel().setVisible(b);
		dressingForm.getSpentTimeLabel().setVisible(b);
		//
		dressingForm.getRemainingTimeLabel().setVisible(b);
		dressingForm.getCheckTimeLabel().setVisible(b);
		
		dressingForm.getExtraChargesLabel().setVisible(b);
		dressingForm.getCustomWristToDraw().setVisible(b);	//????
		
		dressingForm.getWristbandClosingButton().setEnabled(b);
		//receiverForm.getWristbandClosingButton().setFocusable(b);
	}	
	
	@SuppressWarnings("deprecation")
	private void showWristbandData(ActiveWristband checkedWristband) {
		//1.) setting dates and variables
		extraCharges = 0;
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		dressingForm.getStartDateLabel().setText(checkedWristband.getActivatingDateTime().format(formatter));		
		currentDateTime = LocalDateTime.now();
		dressingForm.getStopDateLabel().setText(currentDateTime.format(formatter));
		
		//2.) a, check badWeather Amount and print out		
		if(checkedWristband.getBadWeatherElapsedTime() != null || currentSettings.getBadWeatherStartTime() != null) {
			if(checkedWristband.getBadWeatherElapsedTime() != null ) {
				dressingForm.getBadWeatherElapsedTimeLabel().setText("Pálya zárva tartott: " + checkedWristband.getBadWeatherElapsedTime());				
			}
			if(currentSettings.getBadWeatherStartTime() != null) {
				dressingForm.getBadWeatherElapsedTimeLabel().setText("Pálya zárva tart: " + currentSettings.getBadWeatherElapsedTimeString());
			}
			if(checkedWristband.getBadWeatherElapsedTime() != null && currentSettings.getBadWeatherStartTime() != null) {
				dressingForm.getBadWeatherElapsedTimeLabel().setText("Pálya zár összesen: " 
													+ calculateSumTime(currentSettings.getBadWeatherElapsedTime(), checkedWristband.getBadWeatherElapsedTime()));
			}
			
			dressingForm.getBadWeatherElapsedTimeLabel().setVisible(true);
		}else {
			dressingForm.getBadWeatherElapsedTimeLabel().setVisible(false);
		}
		
		//3.) spent time calculation a.) total time
		Duration duration = Duration.between(checkedWristband.getActivatingDateTime(), currentDateTime);
		int hours = (int)(duration.getSeconds() / (60*60));
		int minutes = (int) (duration.getSeconds() % (60*60) / 60);
		int seconds = (int) (duration.getSeconds() % 60);
		
		if(hours > 23) {
			hours = 23;
			dressingForm.getBadWeatherElapsedTimeLabel().setText("A szalag lejárt több mint egy napja! Kérem ellenõrizze!");
		}		
		Time timeSpent = new Time(hours, minutes, seconds);
		
		//- current bad weather elapsed time
		netTime = calculateNetTime(timeSpent, currentSettings.getBadWeatherElapsedTime());
		
		//4.) calculate netTime 2nd step
		netTime = calculateNetTime(netTime, checkedWristband.getBadWeatherElapsedTime());		
		
		NumberFormat numberFormatter = new DecimalFormat("00");
		String minutesString = numberFormatter.format(netTime.getMinutes());
		String secondsString = numberFormatter.format(netTime.getSeconds());
		dressingForm.getSpentTimeLabel().setText(netTime.getHours() + ":" + minutesString + ":" + secondsString);		
		
		//5.) check wrist type and max time limit
		int allowedTime = 3;
		if(checkedWristband.getColor().equals("WHITE")) {
			allowedTime = 2;
		}
		
		//6.) calculate and print out remaining time
		if(netTime.getHours() >= allowedTime ) { // || (netTime.getHours() == allowedTime && netTime.getMinutes() >= 5)) {
			if(netTime.getMinutes() >= 5) {
			dressingForm.getRemainingTimeLabel().setForeground(Color.RED);
			dressingForm.getRemainingTimeLabel().setText("Lejárt");
			}else {
				dressingForm.getRemainingTimeLabel().setForeground(Color.BLACK);
				dressingForm.getRemainingTimeLabel().setText("Türelmi idõben");
			}
		}else { //if(netTime.getHours() < allowedTime){
			int remainingSeconds = 60 - netTime.getSeconds();
			int remainingMinutes = 60 - netTime.getMinutes() - 1;
			
			if(netTime.getSeconds() == 0) {
				remainingSeconds = 00;
				remainingMinutes++;
			}
			
			minutesString = numberFormatter.format(remainingMinutes);
			secondsString = numberFormatter.format(remainingSeconds);
			dressingForm.getRemainingTimeLabel().setForeground(Color.BLACK);
			dressingForm.getRemainingTimeLabel().setText((allowedTime - netTime.getHours() - 1) + ":" + minutesString + ":" + secondsString);
		}
		
		//7.) calculate and save bad weather times
		badWeatherTime = calculateSumTime(currentSettings.getBadWeatherElapsedTime(), checkedWristband.getBadWeatherElapsedTime());			
		
		//8.) print out the plus charges 
		dressingForm.getCheckTimeLabel().setBounds(820, 175, 64, 60);
		
		if(netTime.getHours() > allowedTime || (netTime.getHours() == allowedTime && netTime.getMinutes() >= 5)) { 
			dressingForm.changeCheckTimeLabelIcon(false);
			dressingForm.getSpentTimeLabel().setForeground(Color.RED);
			extraCharges = (netTime.getHours() - allowedTime) * 60 + netTime.getMinutes() - 4;
			dressingForm.getExtraChargesLabel().setForeground(Color.RED);
			dressingForm.getExtraChargesLabel().setText("Plusz költség: (" + extraCharges + " perc) " + extraCharges * 30 + " Ft!");
		}else {
			dressingForm.changeCheckTimeLabelIcon(true);
			dressingForm.getSpentTimeLabel().setForeground(Color.BLACK);
		}		
	}
	
	@SuppressWarnings("deprecation")
	private Time calculateNetTime(Time timeFrom, Time badWeatherElapsedTime) {
		if(badWeatherElapsedTime != null) {
			int timeInsec = (timeFrom.getHours() * 3600 + timeFrom.getMinutes() * 60 + timeFrom.getSeconds())
								- (badWeatherElapsedTime.getHours() * 3600 + badWeatherElapsedTime.getMinutes() * 60 + badWeatherElapsedTime.getSeconds());
			
			if(timeInsec > 0) {
				int hours = (int)(timeInsec / (60*60));
				int minutes = (int) (timeInsec % (60*60) / 60);
				int seconds = (int) (timeInsec % 60);
				return new Time(hours, minutes, seconds);
			}else {
				return new Time(0, 0, 0);
			}
		}else {
			return timeFrom;
		}
	}
	
	@SuppressWarnings("deprecation")
	private Time calculateSumTime(Time timeFrom, Time timePlus) {
		if(timeFrom != null && timePlus != null) {
			int timeInsec = (timeFrom.getHours() * 3600 + timeFrom.getMinutes() * 60 + timeFrom.getSeconds())
								+ (timePlus.getHours() * 3600 + timePlus.getMinutes() * 60 + timePlus.getSeconds());
			
			int hours = (int)(timeInsec / (60*60));
			int minutes = (int) (timeInsec % (60*60) / 60);
			int seconds = (int) (timeInsec % 60);
			return new Time(hours, minutes, seconds);
		}else {
			if(timeFrom != null){
				return timeFrom;
			}
			if(timePlus != null) {
				return timePlus;
			}
			
			return null;
		}		
	}

	private void setStartingTimeForCheckedWristband() {
		checkedActiveWristband.setActivatingDateTime(LocalDateTime.now());
		dressingForm.getCustomWristToDraw().setCustomWristbandLabel(checkedActiveWristband);
		dressingForm.getCustomWristToDraw().setVisible(true);		
		dressingForm.getCheckLabel().setVisible(false);	
		
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		dressingForm.getStartDateLabel().setText(checkedActiveWristband.getActivatingDateTime().format(formatter));
		dressingForm.getStartDateLabel().setVisible(true);
		
		try {
			activeWristbandService.update(checkedActiveWristband);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		dressingForm.getExtraChargesLabel().setForeground(Color.BLACK);
		dressingForm.getExtraChargesLabel().setText("A karszalag aktiválodott!");
		dressingForm.getExtraChargesLabel().setVisible(true);
		dressingForm.changeCheckTimeLabelIcon(true);
		dressingForm.getCheckTimeLabel().setBounds(220, 105, 64, 60);
		dressingForm.getCheckTimeLabel().setVisible(true);
		
		dressingForm.getBarCodeReceiverTextField().setText("");
	}
	
	private void wristbandClosingProcedure() {  //Add to closedWristband!!!
		ClosedWristband closingWristband = new ClosedWristband(
				checkedActiveWristband,
				currentDateTime,
				netTime, //timeSpent,
				badWeatherTime,
				model.getCurrentUser(),
				(extraCharges * 30)
		);
		closedWristbandService.insert(closingWristband);
		
		activeWristbandService.delete(checkedActiveWristband.getBarCode());
		
		userActivityClosedNumbers++;		
		checkedActiveWristband = null;		
		setDataLabelsVisible(false);
		dressingForm.getWristbandClosingButton().setBackground(ORANGE);
		dressingForm.getBadWeatherElapsedTimeLabel().setText("");
		
		stopWristbandClosingTimer();
		
		updateStatistics();		
	}
	
	private void updateStatistics() {
		dressingForm.getDressingNumberLabel().setText(Long.toString(activeWristbandService.countDressing()));	
		dressingForm.getActiveNumberLabel().setText(Long.toString(activeWristbandService.countActive()));		
		dressingForm.getMoreThen2HoursLabel().setText(Long.toString(activeWristbandService.countMoreThen(2, true)));
		dressingForm.getMoreThen3HoursLabel().setText(Long.toString(activeWristbandService.countMoreThen(3, false)));
		
		dressingForm.getWhiteTrackCounterLabel().setText(Long.toString(activeWristbandService.countByColor("WHITE")));
		dressingForm.getYellowTrackCounterLabel().setText(Long.toString(activeWristbandService.countByColor("YELLOW")));
		dressingForm.getRedTrackCounterLabel().setText(Long.toString(activeWristbandService.countByColor("RED")));
	}
	
	private void checkBadWeather() {
		currentSettings = settingsDataService.findById(1);
		
		if(currentSettings != null && currentSettings.getBadWeatherStartTime() != null) {
			if(!rainyIconIsOn) {
				dressingForm.getRainyIconLabel().setVisible(true);
				dressingForm.getRainyElapsedTimeLabel().setText(currentSettings.getBadWeatherElapsedTimeString());
				rainyIconIsOn = true;				
			}else {
				dressingForm.getRainyElapsedTimeLabel().setText(currentSettings.getBadWeatherElapsedTimeString());
			}
		}else if(rainyIconIsOn) {
			dressingForm.getRainyIconLabel().setVisible(false);
			dressingForm.getRainyElapsedTimeLabel().setText("");
			rainyIconIsOn = false;
		}
	}
	
	private void startTimer() {
		final DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		Date date = new Date();
		dressingForm.getCurrentTimeLabel().setText(timeFormat.format(date));
				
		clockTimer = new Timer(REFRESHING_INTERVAL, new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				Date date = new Date();
				dressingForm.getCurrentTimeLabel().setText(timeFormat.format(date));
				
				updateStatistics();
				checkBadWeather();
			}
		});		
		clockTimer.start();
	}
	
	private void startNewDeleteTimer(int deleteTimeMultiplicator) {
		if(deleteTimer != null) {
			deleteTimer.stop();
		}
		
		deleteTimer = new Timer(REFRESHING_INTERVAL * deleteTimeMultiplicator, new ActionListener() {					
			@Override
			public void actionPerformed(ActionEvent e) {			
				checkedActiveWristband = null;					
				setDataLabelsVisible(false);
				dressingForm.getWristbandClosingButton().setBackground(ORANGE);
				
				dressingForm.getExceptionLabel().setVisible(false);
				dressingForm.getCheckLabel().setVisible(false);
				dressingForm.getBarCodeReceiverTextField().setText("");											
				
				deleteTimer.stop();
			}
		});		
		deleteTimer.start();
	}
	
	private void stopDeleteTimer() {
		if (deleteTimer != null) {
			deleteTimer.stop();
		}
	}
	
	private void startNewWristbandClosingTimer() {
		if(wristbandClosingTimer != null) {
			wristbandClosingTimer.stop();
			System.out.println("wristbandClosingTimer Stop!");
		}
		
		wristbandClosingSeconds = 20;
		dressingForm.getWristbandClosingTimeLabel().setText(Integer.toString(wristbandClosingSeconds));
		dressingForm.getWristbandClosingTimeLabel().setVisible(true);
		
		wristbandClosingTimer = new Timer(1000, new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(wristbandClosingSeconds > 1) {
					wristbandClosingSeconds--;
					
					dressingForm.getWristbandClosingTimeLabel().setText(Integer.toString(wristbandClosingSeconds));
					//dressingForm.getWristbandClosingTimeLabel().setText("Lezárható: " + wristbandClosingSeconds);
				}else {
					wristbandToBeClosed = null;
					wristbandClosingTimer.stop();
					
					dressingForm.getWristbandClosingTimeLabel().setText("");
					dressingForm.getWristbandClosingTimeLabel().setVisible(false);
					
					setDataLabelsVisible(false);
					dressingForm.getExtraChargesLabel().setText("");
					dressingForm.getBadWeatherElapsedTimeLabel().setText("");
				}				
			}
		});
		wristbandClosingTimer.start();
	}
	
	public int showExitDialogWhenHaveActiveWrist() {
		Object[] options = {"Igen", "Nem"};
		return (JOptionPane.showOptionDialog(dressingForm,
				"Biztosan be szeretné zárni az alkalmazást? Néhány szalag még aktív!", "Alkalmazás bezárása?",
			    JOptionPane.YES_NO_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    new ImageIcon(getClass().getClassLoader().getResource("bird_question2.png")),
			    options,
			    options[0]));	
	}
	
	private KeyAdapter getKeyAdapter() {
		return new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
					if(e.getSource() == dressingForm.getBarCodeReceiverButton()) {
						wristbandSearchingProcedure();
						dressingForm.getWristbandClosingButton().requestFocus();
					}
					if(e.getSource() == dressingForm.getWristbandClosingButton()) {
						wristbandClosingProcedure();
						dressingForm.getBarCodeReceiverButton().requestFocus();
					}
				}
			}
		};	
	}

	@Override
	public void mouseClicked(MouseEvent e) {	}

	@Override
	public void mousePressed(MouseEvent e) {		
		if(dressingForm.getSignOutLabel().isVisible() && e.getSource() != dressingForm.getMenuIconLabel())
			dressingForm.getSignOutLabel().setVisible(false);
		
		if (e.getSource() == dressingForm.getMenuIconLabel()) {
			dressingForm.getSignOutLabel().setVisible(!dressingForm.getSignOutLabel().isVisible());
		}
		if (e.getSource() == dressingForm.getSignOutLabel()) {
			if(activeWristbandService.countActive() > 0) {
				if(showExitDialogWhenHaveActiveWrist() == JOptionPane.NO_OPTION){
					return;
				}				
			}			
			clockTimer.stop();
			
			model.setUserActivityClosedNumber(userActivityClosedNumbers);
			model.persistLoggedInUserActivity();
			
			controller.initailize();			
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == dressingForm.getMenuIconLabel()) {
			dressingForm.getMenuIconLabel().setIcon(new ImageIcon(getClass().getClassLoader().getResource("menu_gray.png")));
		}
		if(e.getSource() == dressingForm.getSignOutLabel()) {
			dressingForm.getSignOutLabel().setBackground(BACKGROUNDGRAY);
		}
		
		if(e.getSource() == dressingForm.getBarCodeReceiverButton()) {
			dressingForm.getBarCodeReceiverButton().setBackground(GRASSGREEN);
			dressingForm.getBarCodeReceiverButton().setBorder(new LineBorder(Color.BLACK, 1));
		}
		
		if(e.getSource() == dressingForm.getWristbandClosingButton() && dressingForm.getWristbandClosingButton().isEnabled() == true)
			dressingForm.getWristbandClosingButton().setBackground(GRASSGREEN);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == dressingForm.getMenuIconLabel()) {
			dressingForm.getMenuIconLabel().setIcon(new ImageIcon(getClass().getClassLoader().getResource("menu_icon.png")));
		}
		if(e.getSource() == dressingForm.getSignOutLabel()) {
			dressingForm.getSignOutLabel().setBackground(BEIGE);
		}
		
		if(e.getSource() == dressingForm.getBarCodeReceiverButton()) {
			dressingForm.getBarCodeReceiverButton().setBackground(ORANGE);
			dressingForm.getBarCodeReceiverButton().setBorder(new LineBorder(DARKBROWN, 2));
		}
		
		if(e.getSource() == dressingForm.getWristbandClosingButton() && dressingForm.getWristbandClosingButton().isEnabled() == true)
			dressingForm.getWristbandClosingButton().setBackground(ORANGE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dressingForm.getExceptionLabel().setText("");
		if (e.getSource() == dressingForm.getBarCodeReceiverButton()) {
			wristbandSearchingProcedure();			
		}
		if (e.getSource() == dressingForm.getWristbandClosingButton()) {
			wristbandClosingProcedure();
		}
		if (e.getSource() == dressingForm.getBarCodeReceiverTextField()) {
			wristbandSearchingProcedure();
		}
	}	
}
