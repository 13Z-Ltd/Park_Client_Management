package model.services;

import java.sql.Time;

import model.daos.ActiveWristbandDao;
import model.entities.ActiveWristband;

public class ActiveWristbandService {
	
	private static ActiveWristbandDao activeWristbandDao;
	
	public ActiveWristbandService() {
		activeWristbandDao = new ActiveWristbandDao();
	}
	
	public void insert(ActiveWristband entity) {
		activeWristbandDao.openCurrentSessionWithTrabsaction();
		activeWristbandDao.create(entity);
		activeWristbandDao.closeCurrentSessionWithTrabsaction();
	}
	
	public void update(ActiveWristband entity) {
		activeWristbandDao.openCurrentSessionWithTrabsaction();
		activeWristbandDao.update(entity);
		activeWristbandDao.closeCurrentSessionWithTrabsaction();
	}
	
	public ActiveWristband findById(long id) {
		activeWristbandDao.openCurrentSession();
		ActiveWristband wristband = activeWristbandDao.findById(id);
		activeWristbandDao.closeCurrentSession();
		return wristband;
	}
	
	public void delete(long id) {
		activeWristbandDao.openCurrentSessionWithTrabsaction();
		ActiveWristband wristband = activeWristbandDao.findById(id);
		activeWristbandDao.delete(wristband);
		activeWristbandDao.closeCurrentSessionWithTrabsaction();
	}
	
	public Long countActive() {
		activeWristbandDao.openCurrentSession();
		Long count = activeWristbandDao.countActive();
		activeWristbandDao.closeCurrentSession();
		return count;
	}
	
	public Long countDressing() {
		activeWristbandDao.openCurrentSession();
		Long count = activeWristbandDao.countDressing();
		activeWristbandDao.closeCurrentSession();
		return count;
	}
	
	public Long countMoreThen(int hours, boolean isWhite) {
		activeWristbandDao.openCurrentSession();
		Long count = activeWristbandDao.countMoreThen(hours, isWhite);
		activeWristbandDao.closeCurrentSession();
		return count;
	}
	
	public Long countByColor(String color) {
		activeWristbandDao.openCurrentSession();
		Long count = activeWristbandDao.countWristbandByColor(color);
		activeWristbandDao.closeCurrentSession();
		return count;
	}
	
	public void updateAllBadWeatherElapsedTime(Time elapsedTime) {
		activeWristbandDao.openCurrentSessionWithTrabsaction();
		activeWristbandDao.updateAllBadWeatherElapsedTime(elapsedTime);
		activeWristbandDao.closeCurrentSessionWithTrabsaction();
	}	
	
	public void deleteAll() {
		activeWristbandDao.openCurrentSessionWithTrabsaction();
		activeWristbandDao.deleteAll();
		activeWristbandDao.closeCurrentSessionWithTrabsaction();
	}	
}
