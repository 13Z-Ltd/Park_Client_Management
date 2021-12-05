package model.services;

import java.util.List;

import model.daos.UserActivitiesDao;
import model.entities.UserActivities;

public class UserActivityService {

	private static UserActivitiesDao userActivitiesDao;
	
	public UserActivityService() {
		userActivitiesDao = new UserActivitiesDao();		
	}
	
	public void persist(UserActivities entity) {
		userActivitiesDao.openCurrentSessionWithTrabsaction();
		userActivitiesDao.persist(entity);
		userActivitiesDao.closeCurrentSessionWithTrabsaction();
	}
	
	public void update(UserActivities entity) {
		userActivitiesDao.openCurrentSessionWithTrabsaction();
		userActivitiesDao.update(entity);
		userActivitiesDao.closeCurrentSessionWithTrabsaction();
	}
	
	public UserActivities findById(int id) {
		userActivitiesDao.openCurrentSession();
		UserActivities activity = userActivitiesDao.findById(id);
		userActivitiesDao.closeCurrentSession();
		return activity;
	}
	
	public List<UserActivities> findAll() {
		userActivitiesDao.openCurrentSession();
		List<UserActivities> appUsers = userActivitiesDao.findAll();
		userActivitiesDao.closeCurrentSession();
		return appUsers;
	}
	
	public void delete(int id) {
		userActivitiesDao.closeCurrentSessionWithTrabsaction();
		UserActivities activity = userActivitiesDao.findById(id);
		
			userActivitiesDao.delete(activity);
				
			userActivitiesDao.closeCurrentSessionWithTrabsaction();
	}
	
	public void deleteAll() {
		userActivitiesDao.closeCurrentSessionWithTrabsaction();
		userActivitiesDao.deleteAll();
		userActivitiesDao.closeCurrentSessionWithTrabsaction();
	}
	
	public UserActivitiesDao userActivitiesDao() {
		return userActivitiesDao;
	}	
}
