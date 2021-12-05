package model.services;

import model.daos.RegisteredWristbandDao;
import model.entities.AppUser;
import model.entities.RegisteredWristband;

public class RegisteredWristbandService {
	
	private static RegisteredWristbandDao registeredWristbandDao;
	
	public RegisteredWristbandService() {
		registeredWristbandDao = new RegisteredWristbandDao();
	}
	
	public void insert(RegisteredWristband entity) {
		registeredWristbandDao.openCurrentSessionWithTrabsaction();
		registeredWristbandDao.create(entity);
		registeredWristbandDao.closeCurrentSessionWithTrabsaction();
	}
	
	public void insertGroupOfWristbands(Long barCode, int amount, String color, AppUser currentUser) {
		registeredWristbandDao.openCurrentSessionWithTrabsaction();		
		for (int j = 0; j < amount; j++) {
			registeredWristbandDao.create(new RegisteredWristband(barCode, color, currentUser));
			barCode++;			
		}
		registeredWristbandDao.closeCurrentSessionWithTrabsaction();
	}
	
	public void update(RegisteredWristband entity) {
		registeredWristbandDao.openCurrentSessionWithTrabsaction();
		registeredWristbandDao.update(entity);
		registeredWristbandDao.closeCurrentSessionWithTrabsaction();
	}
	
	public RegisteredWristband findById(long id) {
		registeredWristbandDao.openCurrentSession();
		RegisteredWristband wristband = registeredWristbandDao.findById(id);
		registeredWristbandDao.closeCurrentSession();
		return wristband;
	}
	
	public RegisteredWristband findByGroupOfIds(long id, int amount) {
		RegisteredWristband checkWristband = null;
		registeredWristbandDao.openCurrentSession();
		while(checkWristband == null && amount > 0) {
			checkWristband = registeredWristbandDao.findById(id);
			
			id++;
			amount--;
		}
		registeredWristbandDao.closeCurrentSession();
		return checkWristband;
	}
	
	public long countByColor(String color) {
		registeredWristbandDao.openCurrentSession();
		long count = registeredWristbandDao.countByColor(color);
		registeredWristbandDao.closeCurrentSession();
		return count;
	}
	
	public void delete(long id) {
		registeredWristbandDao.openCurrentSessionWithTrabsaction();
		RegisteredWristband wristband = registeredWristbandDao.findById(id);
		registeredWristbandDao.delete(wristband);
		registeredWristbandDao.closeCurrentSessionWithTrabsaction();
	}
}
