package model.services;

import java.util.Date;

import model.daos.BadWeatherLogDao;
import model.entities.BadWeatherLog;

public class BadWeatherLogService {
	private static BadWeatherLogDao badWeatherLogDao;
	
	public BadWeatherLogService() {
		badWeatherLogDao = new BadWeatherLogDao();
	}	
	
	public void insert(BadWeatherLog entity) {
		badWeatherLogDao.openCurrentSessionWithTrabsaction();
		badWeatherLogDao.create(entity);
		badWeatherLogDao.closeCurrentSessionWithTrabsaction();
	}	
	
	public void update(BadWeatherLog entity) {
		badWeatherLogDao.openCurrentSessionWithTrabsaction();
		badWeatherLogDao.update(entity);
		badWeatherLogDao.closeCurrentSessionWithTrabsaction();
	}
	
	public BadWeatherLog findById(long id) {
		badWeatherLogDao.openCurrentSession();
		BadWeatherLog log = badWeatherLogDao.findById(id);
		badWeatherLogDao.closeCurrentSession();
		return log;
	}
	
	public void delete(long id) {
		badWeatherLogDao.openCurrentSessionWithTrabsaction();
		BadWeatherLog log = badWeatherLogDao.findById(id);
		badWeatherLogDao.delete(log);
		badWeatherLogDao.closeCurrentSessionWithTrabsaction();
	}
	
	public Long getNumberOfBadWeatherEvents() {
		badWeatherLogDao.openCurrentSession();
		Long amount = badWeatherLogDao.getNumberOfBadWeatherEvents();
		badWeatherLogDao.closeCurrentSession();
		return amount;
	}
	
	public Long getSumOfBadWeatherTimes() {
		badWeatherLogDao.openCurrentSession();
		Long seconds = badWeatherLogDao.getSumOfBadWeatherTimes();
		badWeatherLogDao.closeCurrentSession();
		return seconds;
	}
	
	public Long getNumberOfCanceledWristbands() {
		badWeatherLogDao.openCurrentSession();
		Long amount = badWeatherLogDao.getNumberOfCanceledWristbands();
		badWeatherLogDao.closeCurrentSession();
		return amount;
	}	
	
	 public Long countByDate(Date dateFrom, Date dateTo) {
		badWeatherLogDao.openCurrentSession();
		Long count = badWeatherLogDao.countByDate(dateFrom, dateTo);
		badWeatherLogDao.closeCurrentSession();
		return count;
	}
	 
	 public Long getSumOfBadWeatherTimesByDate(Date dateFrom, Date dateTo) {
		badWeatherLogDao.openCurrentSession();
		Long seconds = badWeatherLogDao.getSumOfBadWeatherTimesByDate(dateFrom, dateTo);
		badWeatherLogDao.closeCurrentSession();
		return seconds;
	}
	 
	 public Long getNumberOfCanceledWristbandsByDate(Date dateFrom, Date dateTo) {
		badWeatherLogDao.openCurrentSession();
		Long amount = badWeatherLogDao.getNumberOfCanceledWristbandsByDate(dateFrom, dateTo);
		badWeatherLogDao.closeCurrentSession();
		return amount;
	}	
	 
}
