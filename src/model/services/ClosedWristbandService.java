package model.services;

import java.util.Date;

import model.daos.ClosedWristbandDao;
import model.entities.ClosedWristband;

public class ClosedWristbandService {
	
	private static ClosedWristbandDao closedWristbandDao;

	public ClosedWristbandService() {
		closedWristbandDao = new ClosedWristbandDao();
	}
	
	public void insert(ClosedWristband entity) {
		closedWristbandDao.openCurrentSessionWithTrabsaction();
		closedWristbandDao.create(entity);
		closedWristbandDao.closeCurrentSessionWithTrabsaction();
	}
	
	public void update(ClosedWristband entity) {
		closedWristbandDao.openCurrentSessionWithTrabsaction();
		closedWristbandDao.update(entity);
		closedWristbandDao.closeCurrentSessionWithTrabsaction();
	}
	
	public ClosedWristband findById(long id) {
		closedWristbandDao.openCurrentSession();
		ClosedWristband wristband = closedWristbandDao.findById(id);
		closedWristbandDao.closeCurrentSession();
		return wristband;
	}
	
	public ClosedWristband findByGroupOfIds(long id, int amount) {
		ClosedWristband checkWristband = null;
		closedWristbandDao.openCurrentSession();
		while(checkWristband == null && amount > 0) {
			checkWristband = closedWristbandDao.findById(id);
			System.out.println("kereset id:" + id);
			
			id++;
			amount--;			
		}
		closedWristbandDao.closeCurrentSession();
		return checkWristband;
	}	
	
	public void delete(long id) {
		closedWristbandDao.openCurrentSessionWithTrabsaction();
		ClosedWristband wristband = closedWristbandDao.findById(id);
		closedWristbandDao.delete(wristband);
		closedWristbandDao.closeCurrentSessionWithTrabsaction();
	}
	
	public Long countByColor(String color) {
		closedWristbandDao.openCurrentSession();
		Long count = closedWristbandDao.countWristbandByColor(color);
		closedWristbandDao.closeCurrentSession();
		return count;
	}
	
	public Long countByColorIfItUnder5Hours(String color) {
		closedWristbandDao.openCurrentSession();
		Long count = closedWristbandDao.countByColorIfItUnder5Hours(color);
		closedWristbandDao.closeCurrentSession();
		return count;
	}
		
	public Long countOverTimeByColor(String color) {
		closedWristbandDao.openCurrentSession();
		Long count = closedWristbandDao.countOverTimeByColor(color);
		closedWristbandDao.closeCurrentSession();
		return count;
	}
	
	public Long countByPercentAndColor(int percent, String color) {
		closedWristbandDao.openCurrentSession();
		Long count = closedWristbandDao.countByPercentAndColor(percent, color);
		closedWristbandDao.closeCurrentSession();
		return count;
	}
	
	public Long countByDateAndColor(Date dateFrom, Date dateTo, String color) {
		closedWristbandDao.openCurrentSession();
		Long count = closedWristbandDao.countByDateAndColor(dateFrom, dateTo, color);
		closedWristbandDao.closeCurrentSession();
		return count;
	}
	
	public Long sumSpentTime(String color) {
		closedWristbandDao.openCurrentSession();
		long seconds = closedWristbandDao.sumSpentTime(color);
		closedWristbandDao.closeCurrentSession();
		return seconds;
	}	
}
