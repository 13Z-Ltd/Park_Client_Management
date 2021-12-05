package controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import model.Model;
import model.PreparedWristband;
import model.entities.ActiveWristband;
import model.entities.BadWeatherLog;
import model.entities.RegisteredWristband;
import model.entities.SettingsData;
import model.services.ActiveWristbandService;
import model.services.BadWeatherLogService;
import model.services.RegisteredWristbandService;
import model.services.SettingsDataService;
import view.View;
import view.forms.ActivationForm;

public class ActivationFormController implements ActionListener, MouseListener, MouseWheelListener {
	private View view;
	private Model model;
	private Controller controller;
	private ActivationForm activationForm;
	
	private final int REFRESHING_INTERVAL = 20000; //20sec
	private Timer clockTimer;
	private Timer eraseTimer;
	private int soldWristbandCounter;
	private String color;

	private RegisteredWristbandService registeredWristbandService;
	private ActiveWristbandService activeWristbandService;
	private SettingsDataService settingsDataService;
	private BadWeatherLogService badWeatherLogService;
	
	private SettingsData currentSettings;
	private boolean badWeather;
	private boolean showActiveLabels;
	
	private MouseListener buttonMouseListener;
	
	Color DARKBROWN = new Color(55, 45, 40);
	Color ORANGE = new Color(251, 182, 0);
	Color GRASSGREEN = new Color(150, 169, 56);	
	Color LIGHTGRAY = new Color(235, 235, 235);
	Color BACKGROUNDGRAY = new Color(200, 200, 200);
	Color BEIGE = new Color(239, 231, 219);
	Color PASTELRED = new Color(200, 78, 56);
	
	Font normalFont = new Font("Verdana", Font.PLAIN, 20);
	Font activeFont = new Font("Verdana", Font.BOLD, 21);

	public ActivationFormController(View getView, Model getModel, Controller controller) {
		this.view = getView;
		this.model = getModel;
		this.controller = controller;
		activationForm = (ActivationForm) view.getCurrentView();
		
		registeredWristbandService = new RegisteredWristbandService();
		activeWristbandService = new ActiveWristbandService();
		settingsDataService = new SettingsDataService();
		badWeatherLogService = new BadWeatherLogService();
		
		//checking saved setting weather condition
		currentSettings = settingsDataService.findById(1);
		if(currentSettings.getBadWeatherStartTime() != null) {
			badWeather = true;
			activationForm.getWeatherSwitchButtonLabel().setIcon(new ImageIcon(getClass().getClassLoader().getResource("bad_weather.png")));
		}else {
			badWeather = false;
		}
		
		soldWristbandCounter = 0;
		
		model.createNewListOfPreparedWristbands();

		activationForm.getWhiteWristImageLabel().addMouseListener(this);
		activationForm.getYellowWristImageLabel().addMouseListener(this);
		activationForm.getRedWristImageLabel().addMouseListener(this);
		activationForm.getWhiteWristImageLabel().addMouseWheelListener(this);
		activationForm.getYellowWristImageLabel().addMouseWheelListener(this);
		activationForm.getRedWristImageLabel().addMouseWheelListener(this);
		
		activationForm.getMenuIconLabel().addMouseListener(this);
		activationForm.getSignOutLabel().addMouseListener(this);

		activationForm.getWhiteCounterLabel().addMouseWheelListener(this);
		activationForm.getYellowCounterLabel().addMouseWheelListener(this);
		activationForm.getRedCounterLabel().addMouseWheelListener(this);

		activationForm.getBtnAddBarCodeButton().addActionListener(this);
		activationForm.getBtnAcceptButton().addActionListener(this);
		
		activationForm.getClosingTimeLabel().addMouseListener(this);
		activationForm.getClosingTimeLabel().addMouseWheelListener(this);
		
		activationForm.getWeatherSwitchButtonLabel().addMouseListener(this);
		activationForm.getSingingOrActiveSwitchLabel().addMouseListener(this);
		
		activationForm.getBarCodeAdderTextField().addKeyListener(getBarCodeReceiverKeyListener());
		
		buttonMouseListener = getActivationFormMouseListener();
		activationForm.getBtnAcceptButton().addMouseListener(buttonMouseListener);
		activationForm.getBtnAddBarCodeButton().addMouseListener(buttonMouseListener);		

		refreshNumbers();
		
		dataUpdate();
		
		startTimer();
		
		scanClosingTime();
		
		activationForm.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if(view.showExitDialog() == JOptionPane.YES_OPTION) {
					logoutProcedure();
					System.exit(0);
				}
		    }
		});
	}

	public void refreshNumbers() {
		activationForm.getWhiteCounterLabel().setText(Integer.toString(model.getListOfPreparedWristbands().getNumberOfWhites()));
		activationForm.getYellowCounterLabel().setText(Integer.toString(model.getListOfPreparedWristbands().getNumberOfYellows()));
		activationForm.getRedCounterLabel().setText(Integer.toString(model.getListOfPreparedWristbands().getNumberOfReds()));

		// wristband calculations
		if (model.getListOfPreparedWristbands().getNumberOfWhites() > 0)
			activationForm.getWhiteCalculationLabel()
					.setText("( " + model.getListOfPreparedWristbands().getNumberOfWhites() + " * 3200 ) = "
							+ model.getListOfPreparedWristbands().getNumberOfWhites() * 3200);
		else
			activationForm.getWhiteCalculationLabel().setText("");

		if (model.getListOfPreparedWristbands().getNumberOfYellows() > 0)
			activationForm.getYellowCalculationLabel()
					.setText("( " + model.getListOfPreparedWristbands().getNumberOfYellows() + " * 4000 ) = "
							+ model.getListOfPreparedWristbands().getNumberOfYellows() * 4000);
		else
			activationForm.getYellowCalculationLabel().setText("");

		if (model.getListOfPreparedWristbands().getNumberOfReds() > 0)
			activationForm.getRedCalculationLabel().setText("( " + model.getListOfPreparedWristbands().getNumberOfReds()
					+ " * 5200 ) = " + model.getListOfPreparedWristbands().getNumberOfReds() * 5200);
		else
			activationForm.getRedCalculationLabel().setText("");

		// discount
		setDiscountTime();
		
		if (model.getListOfPreparedWristbands().getDiscount() > 0) {			
			activationForm.getDiscountSubtitleLabel().setText("Kedvezmény ("+ model.getListOfPreparedWristbands().getDiscount() + "%)");
			activationForm.getDiscountSubtitleLabel().setVisible(true);
			activationForm.getDiscountAmountLabel().setText((model.getListOfPreparedWristbands().getAmontOfDiscount() + " Ft"));
		} else {
			activationForm.getDiscountSubtitleLabel().setVisible(false);
			activationForm.getDiscountAmountLabel().setText("");
		}

		// Taxes of total
		if (model.getListOfPreparedWristbands().getTotal() > 0) {
			double taxBase = (model.getListOfPreparedWristbands().getTotal() / 1.27);
			BigDecimal bd = new BigDecimal(taxBase).setScale(2, RoundingMode.HALF_UP);
			taxBase = bd.doubleValue();
			activationForm.getTaxBaseLabel().setText(Double.toString(taxBase));
			taxBase = model.getListOfPreparedWristbands().getTotal() - taxBase;
			bd = new BigDecimal(taxBase).setScale(2, RoundingMode.HALF_UP);
			taxBase = bd.doubleValue();
			activationForm.getTaxLabel().setText(Double.toString(taxBase));
		} else {
			activationForm.getTaxBaseLabel().setText("");
			activationForm.getTaxLabel().setText("");
		}
		activationForm.getLblSumTotalLabel().setText(model.getListOfPreparedWristbands().getTotal() + " Ft");

		refreshButtons();

		activationForm.refreshWristPanel();
	}

	private void refreshButtons() {
		if (model.getListOfPreparedWristbands().getNumberOfEmptyWrists() > 0) {						
			activationForm.getBarCodeAdderTextField().setEnabled(true);
			activationForm.getBarCodeAdderTextField().setEditable(true);
			activationForm.getBarCodeAdderTextField().requestFocus();
			
			//activationForm.getBarCodeAdderTextField().setText("");			
			//activationForm.getBarCodeAdderTextField().setFocusable(true);
			//activationForm.getBarCodeAdderTextField().getCaret().setVisible(true);
			activationForm.getBtnAddBarCodeButton().setEnabled(true);
			activationForm.getBtnAddBarCodeButton().setBorder(new LineBorder(Color.WHITE, 2));
			activationForm.getBtnAcceptButton().setEnabled(false);
			activationForm.getBtnAcceptButton().setBorder(new LineBorder(Color.GRAY, 2));
		} else {
			//activationForm.getBarCodeAdderTextField().setEditable(false);
			activationForm.getBarCodeAdderTextField().setEnabled(false);
			activationForm.getBarCodeAdderTextField().getCaret().setVisible(false);
			activationForm.getBtnAddBarCodeButton().setEnabled(false);
			activationForm.getBtnAddBarCodeButton().setBackground(ORANGE);
			activationForm.getBtnAddBarCodeButton().setBorder(new LineBorder(Color.GRAY, 2));
			if (model.getListOfPreparedWristbands().getNumberOfAllWrists() > 0) {
				activationForm.getBtnAcceptButton().setEnabled(true);
				activationForm.getBtnAcceptButton().setBorder(new LineBorder(Color.WHITE, 2));;
			}				
		}
		if(model.getListOfPreparedWristbands().getNumberOfAllWrists() == 0) {
			activationForm.getBtnAcceptButton().setEnabled(false);
			activationForm.getBtnAcceptButton().setBackground(DARKBROWN);
			activationForm.getBtnAcceptButton().setBorder(new LineBorder(Color.GRAY, 2));
		}
	}

	private void searchAvailableWrist(RegisteredWristband wristband) {
		activationForm.getExceptionLabel().setText("");
		
		boolean successfulSearch = false;
		if (wristband.getColor().equals("WHITE")) {
			for (PreparedWristband searchWrist : model.getListOfPreparedWristbands().getWhiteWristbands()) {
				if (!searchWrist.isBarCodeOn()) {
					searchWrist.setBarCode(wristband.getBarCode());
					searchWrist.setBarCodeOn(true);
					successfulSearch = true;
					break;
				}
			}
		}
		if (wristband.getColor().equals("YELLOW")) {
			for (PreparedWristband searchWrist : model.getListOfPreparedWristbands().getYellowWristbands()) {
				if (!searchWrist.isBarCodeOn()) {
					searchWrist.setBarCode(wristband.getBarCode());
					searchWrist.setBarCodeOn(true);
					successfulSearch = true;
					break;
				}
			}
		}
		if (wristband.getColor().equals("RED")) {
			for (PreparedWristband searchWrist : model.getListOfPreparedWristbands().getRedWristbands()) {
				if (!searchWrist.isBarCodeOn()) {
					searchWrist.setBarCode(wristband.getBarCode());
					searchWrist.setBarCodeOn(true);
					successfulSearch = true;
					break;
				}
			}
		}
		
		if(!successfulSearch) {
			activationForm.getExceptionLabel().setText("Nem ilyen típusú szalagot várunk !  \"" + activationForm.getBarCodeAdderTextField().getText() + "\"");
			startEraseTimer();
		}else {
			//activationForm.getBarCodeAdderTextField().setText("");
		}
	}

	private void wristbandActivationProcedure() {
		int currentDiscount = model.getListOfPreparedWristbands().getDiscount();
		for (PreparedWristband preparedWristband : model.getListOfPreparedWristbands().getWhiteWristbands()) {
			RegisteredWristband registeredWristband = registeredWristbandService.findById(preparedWristband.getBarCode());
			ActiveWristband newWristband = new ActiveWristband(
					registeredWristband.getBarCode(),
					model.getCurrentUser(),
					3200 - ((3200 / 100) * currentDiscount),
					currentDiscount, registeredWristband.getColor(),
					registeredWristband.getRegistrationDateTime(),
					registeredWristband.getResponsibleUserId()
			);
			//Actív karszalagokhoz hozzáadás
			activeWristbandService.insert(newWristband);
			
			//registeredbõl töröl
			registeredWristbandService.delete(preparedWristband.getBarCode());
		}
		
		for (PreparedWristband preparedWristband : model.getListOfPreparedWristbands().getYellowWristbands()) {
			RegisteredWristband registeredWristband = registeredWristbandService.findById(preparedWristband.getBarCode());
			ActiveWristband newWristband = new ActiveWristband(
					registeredWristband.getBarCode(),
					model.getCurrentUser(),
					4000 - ((4000 / 100) * currentDiscount),
					currentDiscount, registeredWristband.getColor(),
					registeredWristband.getRegistrationDateTime(),
					registeredWristband.getResponsibleUserId()
			);			
			//Actív karszalagokhoz hozzáad
			activeWristbandService.insert(newWristband);
			
			//registeredbõl töröl
			registeredWristbandService.delete(preparedWristband.getBarCode());
		}
		
		for (PreparedWristband preparedWristband : model.getListOfPreparedWristbands().getRedWristbands()) {
			RegisteredWristband registeredWristband = registeredWristbandService.findById(preparedWristband.getBarCode());
			ActiveWristband newWristband = new ActiveWristband(
					registeredWristband.getBarCode(),
					model.getCurrentUser(),
					5200 - ((5200 / 100) * currentDiscount),
					currentDiscount, registeredWristband.getColor(),
					registeredWristband.getRegistrationDateTime(),
					registeredWristband.getResponsibleUserId()
			);
			//Actív karszalagokhoz hozzáad
			activeWristbandService.insert(newWristband);
			
			//registeredbõl töröl
			registeredWristbandService.delete(preparedWristband.getBarCode());
		}
		
		//activatedWristbandCounter
		soldWristbandCounter += model.getListOfPreparedWristbands().getWhiteWristbands().size();
		soldWristbandCounter += model.getListOfPreparedWristbands().getYellowWristbands().size();
		soldWristbandCounter += model.getListOfPreparedWristbands().getRedWristbands().size();
		

		model.getListOfPreparedWristbands().createNewList();
		refreshNumbers();
		refreshButtons();
		
		dataUpdate();
	}
	
	private void startTimer() {
		final DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		Date date = new Date();
		activationForm.getCurrentTimeLabel().setText(timeFormat.format(date));
				
		clockTimer = new Timer(REFRESHING_INTERVAL, new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				Date date = new Date();
				activationForm.getCurrentTimeLabel().setText(timeFormat.format(date));

				dataUpdate();
			}					
		});		
		clockTimer.start();
	}
	
	private void startEraseTimer() {				
		eraseTimer = new Timer(REFRESHING_INTERVAL * 2, new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				activationForm.getExceptionLabel().setText("");
				eraseTimer.stop();
			}					
		});		
		eraseTimer.start();
	}
	
	private void scanClosingTime() {	
		activationForm.getClosingTimeLabel().setText(currentSettings.getClosingHour() + ":00");		
		//kell-e kedvezmény!?
		setDiscountTime();
	}
	
	private void dataUpdate() {
		Long white = registeredWristbandService.countByColor("WHITE");
		Long yellow = registeredWristbandService.countByColor("YELLOW");
		Long red = registeredWristbandService.countByColor("RED");
		
		if(white > 1000) {
			activationForm.getWhiteRegisteredNumberLabel().setText((white/1000) + "000+");
		}else if(white > 100) {
			activationForm.getWhiteRegisteredNumberLabel().setText((white/100) + "00+");
		}else {
			activationForm.getWhiteRegisteredNumberLabel().setText(white + "");
		}
		
		if(yellow > 1000) {
			activationForm.getYellowRegisteredNumberLabel().setText((yellow/1000) + "000+");
		}else if(yellow > 100) {
			activationForm.getYellowRegisteredNumberLabel().setText((yellow/100) + "00+");
		}else {
			activationForm.getYellowRegisteredNumberLabel().setText(yellow + "");
		}
		
		if(red > 1000) {
			activationForm.getRedRegisteredNumberLabel().setText((red/1000) + "000+");
		}else if(red > 100) {
			activationForm.getRedRegisteredNumberLabel().setText((red/100) + "00+");
		}else {
			activationForm.getRedRegisteredNumberLabel().setText(red + "");
		}
		
		white = activeWristbandService.countByColor("WHITE");
		yellow = activeWristbandService.countByColor("YELLOW");
		red = activeWristbandService.countByColor("RED");
		
		currentSettings = settingsDataService.findById(1);
		activationForm.getAvailableChildSingingNumbersLabel().setText(Long.toString((currentSettings.getChildSinging() - currentSettings.getReservedChildSinging()) - white));
		activationForm.getAvailableSeniorSingingNumbersLabel().setText(Long.toString((currentSettings.getSeniorSinging() - currentSettings.getReservedSeniorSinging()) - (yellow + red)));
		
		if(currentSettings.getReservedChildSinging() > 0) {
			activationForm.getSingingStickyLabel().setToolTipText("Gyerek beülõk száma (" + currentSettings.getReservedChildSinging() + "db fenntartva)");
		}else {
			activationForm.getSingingStickyLabel().setToolTipText("Gyerek beülõk száma");
		}
		if(currentSettings.getReservedSeniorSinging() > 0) {
			activationForm.getSingingStickyLabel2().setToolTipText("Felnõt beülõk száma (" + currentSettings.getReservedSeniorSinging() + "db fenntartva)");
		}else {
			activationForm.getSingingStickyLabel2().setToolTipText("Felnõt beülõk száma");
		}
		
		activationForm.getActiveWhiteNumbersLabel().setText(Long.toString(white));
		activationForm.getActiveYellowNumbersLabel().setText(Long.toString(yellow));
		activationForm.getActiveRedNumbersLabel().setText(Long.toString(red));
	}
	
	private void changeClosingTime(int i) {
		if(i > 0 && currentSettings.getClosingHour() < 22) {
			currentSettings.setClosingHour(currentSettings.getClosingHour() + 1);
			model.getListOfPreparedWristbands().createNewList();
			refreshNumbers();
			refreshButtons();
		}
		
		if(i < 0 && currentSettings.getClosingHour() > 16) {
			currentSettings.setClosingHour(currentSettings.getClosingHour() - 1);
			model.getListOfPreparedWristbands().createNewList();
			refreshNumbers();
			refreshButtons();
		}
		
		settingsDataService.update(currentSettings);
		activationForm.getClosingTimeLabel().setText(currentSettings.getClosingHour() + ":00");
		activationForm.getDiscountSubtitleLabel().setVisible(false);
		setDiscountTime();
	}
	
	@SuppressWarnings("deprecation")
	private void setDiscountTime() {
		Date date = new Date();
		if(date.getHours() >= currentSettings.getClosingHour() - 2) {
			model.getListOfPreparedWristbands().setDiscountTime(true);
		}else {
			model.getListOfPreparedWristbands().setDiscountTime(false);
		}
	}
	
	private int showWeatherChangeDialog(String dialog) {
		Object[] options = {"Igen", "Nem"};
		return (JOptionPane.showOptionDialog(activationForm,
				dialog, "Idõjárás kapcsoló!",
			    JOptionPane.YES_NO_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    new ImageIcon(getClass().getClassLoader().getResource("bird_question2.png")),     //do not use a custom Icon
			    options,  //the titles of buttons
			    options[0]));	
	}
	
	@SuppressWarnings("deprecation")
	private void logoutProcedure() {
		model.setUserActivitySoldNumber(soldWristbandCounter);
		model.persistLoggedInUserActivity();		
		
		if(badWeather) {
			LocalDateTime todayClosingTime = LocalDate.now().atTime(currentSettings.getClosingHour(), 0);
			
			Duration timeToClosing = Duration.between(currentSettings.getBadWeatherStartTime(), todayClosingTime);
			int hoursToClosing = (int)(timeToClosing.getSeconds() / (60*60));
			int minutesToClosing = (int) (timeToClosing.getSeconds() % (60*60) / 60);
			int secondsToClosing = (int) (timeToClosing.getSeconds() % 60);			
			activeWristbandService.updateAllBadWeatherElapsedTime(new Time(hoursToClosing, minutesToClosing, secondsToClosing));
			
			//logging Bad Weather
			Long amountOfAffectedWristbands =  activeWristbandService.countActive();
			badWeatherLogService.insert(new BadWeatherLog(currentSettings.getBadWeatherStartTime(), todayClosingTime, model.getCurrentUser(), amountOfAffectedWristbands));
		}
		
		currentSettings.setBadWeatherStartTime(null); 
		settingsDataService.update(currentSettings);
		
		clockTimer.stop();
	}
	
	private void addingBarcode() {		
		if (activationForm.getBarCodeAdderTextField().getText().length() == 13) {
			long barCode = 0;
			boolean correctBarCode = true;
			try {
				barCode = Long.parseLong(activationForm.getBarCodeAdderTextField().getText());
			} catch (Exception ex) {
				activationForm.getExceptionLabel().setText("Nem vonalkódnak megfelelõ karaktersor lett megadva !  \"" + activationForm.getBarCodeAdderTextField().getText() + "\"");			
				correctBarCode = false;
				startEraseTimer();
			}
			if(correctBarCode) {
				RegisteredWristband wristband = registeredWristbandService.findById(barCode);
				if (wristband != null) {
					searchAvailableWrist(wristband);
					activationForm.getWristListPanel().repaint();
				} else {
					activationForm.getExceptionLabel().setText("A \"" + activationForm.getBarCodeAdderTextField().getText() + "\" számú szalag nincs a regisztráltak között !");
					startEraseTimer();
				}
				activationForm.getBarCodeAdderTextField().setText("");
			}
		} else {
			activationForm.getExceptionLabel().setText("13 karakterbõl álló vonalkódot kell megadni !  \"" + activationForm.getBarCodeAdderTextField().getText() + "\"");
			startEraseTimer();
		}
		
		refreshButtons();		
	}
	
	private MouseListener getActivationFormMouseListener() {
		return new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent e) {			}			
			@Override
			public void mousePressed(MouseEvent e) {			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(e.getSource() == activationForm.getBtnAcceptButton() && activationForm.getBtnAcceptButton().isEnabled()) {
					activationForm.getBtnAcceptButton().setBackground(DARKBROWN);
				}
				if(e.getSource() == activationForm.getBtnAddBarCodeButton() && activationForm.getBtnAddBarCodeButton().isEnabled()) {
					activationForm.getBtnAddBarCodeButton().setBackground(ORANGE);
				}
			}			
			@Override
			public void mouseEntered(MouseEvent e) {
				if(e.getSource() == activationForm.getBtnAcceptButton() && activationForm.getBtnAcceptButton().isEnabled())
					activationForm.getBtnAcceptButton().setBackground(GRASSGREEN);
				if(e.getSource() == activationForm.getBtnAddBarCodeButton() && activationForm.getBtnAddBarCodeButton().isEnabled())
					activationForm.getBtnAddBarCodeButton().setBackground(GRASSGREEN);
			}			
			@Override
			public void mouseClicked(MouseEvent e) {			}
		};
	}
	
	private KeyAdapter getBarCodeReceiverKeyListener() {		
		return new KeyAdapter() {		
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
					if(e.getSource() == activationForm.getBarCodeAdderTextField()) { // && activationForm.getBarCodeAdderTextField().isEnabled()) {
						addingBarcode();
					}
				}			
			}
		};
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource() == activationForm.getWhiteWristImageLabel() || e.getSource() == activationForm.getYellowWristImageLabel() || e.getSource() == activationForm.getRedWristImageLabel()) {
			if (e.getSource() == activationForm.getWhiteWristImageLabel())
				color = "WHITE";
			if (e.getSource() == activationForm.getYellowWristImageLabel())
				color = "YELLOW";
			if (e.getSource() == activationForm.getRedWristImageLabel())
				color = "RED";
	
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (model.getListOfPreparedWristbands().increaseWristNumber(color) == 1) {
					refreshNumbers();
				}
			}
			if (e.getButton() == MouseEvent.BUTTON3) {
				if (model.getListOfPreparedWristbands().decreaseWristNumber(color) == 1)
					refreshNumbers();
			}
		}
		
		if (e.getSource() == activationForm.getMenuIconLabel()) {
			activationForm.getSignOutLabel().setVisible(!activationForm.getSignOutLabel().isVisible());
		}
		else {
			activationForm.getSignOutLabel().setVisible(false);
		}
		
		if (e.getSource() == activationForm.getSignOutLabel()) {
			logoutProcedure();
			
			controller.initailize();
		}
		
		if(e.getSource() == activationForm.getClosingTimeLabel()) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				changeClosingTime(1);
			}
			if(e.getButton() == MouseEvent.BUTTON3) {
				changeClosingTime(-1);
			}			
			//setDiscountTime();
		}
		
		if(e.getSource() == activationForm.getWeatherSwitchButtonLabel()) {
			if(badWeather) {					
				if(showWeatherChangeDialog("Biztosan jó idõre szeretne váltani?") == JOptionPane.OK_OPTION) {
					activationForm.getWeatherSwitchButtonLabel().setIcon(new ImageIcon(getClass().getClassLoader().getResource("fine_weather.png")));
					
					//logging Bad Weather
					Long amountOfAffectedWristbands =  activeWristbandService.countActive();
					badWeatherLogService.insert(new BadWeatherLog(currentSettings.getBadWeatherStartTime(), LocalDateTime.now(), model.getCurrentUser(), amountOfAffectedWristbands));					
					
					activeWristbandService.updateAllBadWeatherElapsedTime(currentSettings.getBadWeatherElapsedTime());				
					currentSettings.setBadWeatherStartTime(null);				
					settingsDataService.update(currentSettings);
					badWeather = false;
				}
			}else {		
				if(showWeatherChangeDialog("Biztosan rossz idõre szeretne váltani?") == JOptionPane.OK_OPTION) {
					activationForm.getWeatherSwitchButtonLabel().setIcon(new ImageIcon(getClass().getClassLoader().getResource("bad_weather.png")));
					
					currentSettings.setBadWeatherStartTime(LocalDateTime.now());
					settingsDataService.update(currentSettings);								
					badWeather = true;
				}
			}
		}
		
		if(e.getSource() == activationForm.getSingingOrActiveSwitchLabel()) {
			if(showActiveLabels) {
				activationForm.getSingingOrActiveSwitchLabel().setIcon(new ImageIcon(getClass().getClassLoader().getResource("switch_left.png")));
				activationForm.getSingingOrActiveSwitchLabel().setToolTipText("Aktív szalagok megjelenítése");
				
				activationForm.getSingingStickyLabel().setVisible(true);
				activationForm.getSingingStickyLabel2().setVisible(true);
				activationForm.getChildSingingImageLabel().setVisible(true);
				activationForm.getSeniorSingingImageLabel().setVisible(true);
				activationForm.getAvailableChildSingingNumbersLabel().setVisible(true);
				activationForm.getAvailableSeniorSingingNumbersLabel().setVisible(true);
				
				activationForm.getWhiteStickyLabel().setVisible(false);
				activationForm.getYellowStickyLabel().setVisible(false);
				activationForm.getRedStickyLabel().setVisible(false);
				activationForm.getLblstatisticInfoLabel3_2().setVisible(false);
				activationForm.getLblstatisticInfoLabel3_3().setVisible(false);
				activationForm.getLblstatisticInfoLabel3_4().setVisible(false);
				activationForm.getActiveWhiteNumbersLabel().setVisible(false);
				activationForm.getActiveYellowNumbersLabel().setVisible(false);
				activationForm.getActiveRedNumbersLabel().setVisible(false);
				
				showActiveLabels = false;
			}else {
				activationForm.getSingingOrActiveSwitchLabel().setIcon(new ImageIcon(getClass().getClassLoader().getResource("switch_right.png")));
				activationForm.getSingingOrActiveSwitchLabel().setToolTipText("Beülõk számának megjelenítése");
				
				activationForm.getSingingStickyLabel().setVisible(false);
				activationForm.getSingingStickyLabel2().setVisible(false);
				activationForm.getChildSingingImageLabel().setVisible(false);
				activationForm.getSeniorSingingImageLabel().setVisible(false);
				activationForm.getAvailableChildSingingNumbersLabel().setVisible(false);
				activationForm.getAvailableSeniorSingingNumbersLabel().setVisible(false);
				
				activationForm.getWhiteStickyLabel().setVisible(true);
				activationForm.getYellowStickyLabel().setVisible(true);
				activationForm.getRedStickyLabel().setVisible(true);
				activationForm.getLblstatisticInfoLabel3_2().setVisible(true);
				activationForm.getLblstatisticInfoLabel3_3().setVisible(true);
				activationForm.getLblstatisticInfoLabel3_4().setVisible(true);
				activationForm.getActiveWhiteNumbersLabel().setVisible(true);
				activationForm.getActiveYellowNumbersLabel().setVisible(true);
				activationForm.getActiveRedNumbersLabel().setVisible(true);
				
				showActiveLabels = true;
			}
		}
	}	

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getSource() == activationForm.getWhiteCounterLabel() ||
				e.getSource() == activationForm.getYellowCounterLabel() ||
					e.getSource() == activationForm.getRedCounterLabel()){
			
			if (e.getSource() == activationForm.getWhiteCounterLabel())
				color = "WHITE";
			if (e.getSource() == activationForm.getYellowCounterLabel())
				color = "YELLOW";
			if (e.getSource() == activationForm.getRedCounterLabel())
				color = "RED";
	
			int notches = e.getWheelRotation();
			if (notches > 0) {
				if (model.getListOfPreparedWristbands().decreaseWristNumber(color) == 1)
					refreshNumbers();
			} else {
				if (model.getListOfPreparedWristbands().increaseWristNumber(color) == 1)
					refreshNumbers();
			}
		}
		
		if(e.getSource() == activationForm.getWhiteWristImageLabel() ||
				e.getSource() == activationForm.getYellowWristImageLabel() ||
					e.getSource() == activationForm.getRedWristImageLabel()) {
			
			if (e.getSource() == activationForm.getWhiteWristImageLabel())
				color = "WHITE";
			if (e.getSource() == activationForm.getYellowWristImageLabel())
				color = "YELLOW";
			if (e.getSource() == activationForm.getRedWristImageLabel())
				color = "RED";
			
			int notches = e.getWheelRotation();
			if (notches > 0) {
				if (model.getListOfPreparedWristbands().decreaseWristNumber(color) == 1)
					refreshNumbers();
			} else {
				if (model.getListOfPreparedWristbands().increaseWristNumber(color) == 1)
					refreshNumbers();
			}
		}
		
		if(e.getSource() == activationForm.getClosingTimeLabel()) {
			int wheelRotation = e.getWheelRotation();
			if(wheelRotation > 0)
				changeClosingTime(-1);
			else
				changeClosingTime(1);			
		}
		// refreshNumbers();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == activationForm.getMenuIconLabel()) {
			activationForm.getMenuIconLabel().setIcon(new ImageIcon(getClass().getClassLoader().getResource("menu_gray.png")));
		}
		if(e.getSource() == activationForm.getSignOutLabel()) {
			activationForm.getSignOutLabel().setBackground(BACKGROUNDGRAY);			
		}
		if(e.getSource() == activationForm.getWhiteWristImageLabel() ||  e.getSource() == activationForm.getYellowWristImageLabel() ||  e.getSource() == activationForm.getRedWristImageLabel()) {
			activationForm.changeIconSize((JLabel)e.getSource());
		}
		
		if(e.getSource() == activationForm.getClosingTimeLabel()) {
			activationForm.getClosingTimeLabel().setForeground(LIGHTGRAY);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == activationForm.getMenuIconLabel()) {
			activationForm.getMenuIconLabel().setIcon(new ImageIcon(getClass().getClassLoader().getResource("menu_icon.png")));
		}
		if(e.getSource() == activationForm.getSignOutLabel()) {
			activationForm.getSignOutLabel().setBackground(BEIGE);
		}
		if(e.getSource() == activationForm.getWhiteWristImageLabel() || e.getSource() == activationForm.getYellowWristImageLabel() || e.getSource() == activationForm.getRedWristImageLabel()) {
			activationForm.changeIconSize((JLabel)e.getSource());
		}
		if(e.getSource() == activationForm.getClosingTimeLabel()) {
			activationForm.getClosingTimeLabel().setForeground(PASTELRED);
			//activationForm.getClosingTimeLabel().setForeground(Color.WHITE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == activationForm.getMenuIconLabel()) {
			activationForm.getSignOutLabel().setVisible(!activationForm.getSignOutLabel().isVisible());
		}
		
		if (e.getSource() == activationForm.getBtnAddBarCodeButton()) {
			addingBarcode();
		}
		
		if (e.getSource() == activationForm.getBtnAcceptButton()) {
			wristbandActivationProcedure();
		}
	}	
}
