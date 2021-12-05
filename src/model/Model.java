package model;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;

import model.db.PBEStringEncryptor;
import model.entities.AppUser;
import model.entities.UserActivities;
import model.services.AppUserService;
import model.services.UserActivityService;

public class Model {
	private static AppUser currentUser;
	private static UserActivities loggedInUserActivity;
		
	private static AppUserService appUserService;
	private static UserActivityService userActivityService;
	private ListOfPreparedWristbands listOfPreparedWristbands;

	public Model() {
		currentUser = null;
		
		appUserService = new AppUserService();
		userActivityService = new UserActivityService();
		
		listOfPreparedWristbands = new ListOfPreparedWristbands();
	}
	
	public void createNewListOfPreparedWristbands() {
		listOfPreparedWristbands.createNewList();
	}	
	
	public int authenticateUser(String userName, String pass) {		
		currentUser = appUserService.findByUserName(userName, pass);
		return appUserService.getLoginInfo();
	}
	
	public void createLoggedInUserActivity(String string) {
		loggedInUserActivity = (new UserActivities(getCurrentUser(), "REGISTER"));
	}
	
	public void setUserActivitySoldNumber(int soldWristbands) {
		if(loggedInUserActivity != null) {
			loggedInUserActivity.setSoldWristbands(soldWristbands);  
		}
	}
	
	public void setUserActivityClosedNumber(int closedWristbands) {
		if(loggedInUserActivity != null) {
			loggedInUserActivity.setClosedWristbands(closedWristbands);  
		}
	}
	
	@SuppressWarnings("deprecation")
	public void persistLoggedInUserActivity() {		
		loggedInUserActivity.setEndOfActivity(LocalDateTime.now());		
		//setSpentTime
		Duration duration = Duration.between(loggedInUserActivity.getStartOfActivity(), loggedInUserActivity.getEndOfActivity());
		int hours = (int)(duration.getSeconds() / (60*60));
		int minutes = (int) (duration.getSeconds() % (60*60) / 60);
		int seconds = (int) (duration.getSeconds() % 60);				
		loggedInUserActivity.setElapsedTime(new Time(hours, minutes, seconds));		
		//set sold!!!		
		userActivityService.persist(loggedInUserActivity);			
	}
	
	public boolean searchForExistingUser(String userName) {
		AppUser user = appUserService.findByUserName(userName);
		if(user != null)
			return true;
		else
			return false;
	}

	public void createNewUser(String userName, String pass, String firstName, String lastName, String role) {
		String encryptedPass = PBEStringEncryptor.getEncryptedString(pass);
		
		AppUser user = new AppUser(firstName, lastName, userName, encryptedPass, role);
		appUserService.insert(user);
	}
	
	public void clearCurrentUser() {
		currentUser = null;
	}
	
	public AppUser getCurrentUser() {
		return currentUser;
	}
	
	public static void setCurrentUser(AppUser currentUser) {
		Model.currentUser = currentUser;
	}

	public static UserActivities getLoggedInUser() {
		return loggedInUserActivity;
	}

	public static void setLoggedInUser(UserActivities loggedInUser) {
		Model.loggedInUserActivity = loggedInUser;
	}

	public ListOfPreparedWristbands getListOfPreparedWristbands() {
		return listOfPreparedWristbands;
	}	
}
