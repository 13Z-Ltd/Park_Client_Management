package model.services;

import model.daos.SettingsDataDao;
import model.entities.SettingsData;

public class SettingsDataService {
	private static SettingsDataDao settingsDataDao;

	public SettingsDataService() {
		settingsDataDao = new SettingsDataDao();
	}
	
	public void insert(SettingsData entity) {
		settingsDataDao.openCurrentSessionWithTrabsaction();
		settingsDataDao.insert(entity);
		settingsDataDao.closeCurrentSessionWithTrabsaction();
	}
	
	public void update(SettingsData entity) {
		settingsDataDao.openCurrentSessionWithTrabsaction();
		settingsDataDao.update(entity);
		settingsDataDao.closeCurrentSessionWithTrabsaction();
	}
	
	public SettingsData findById(int id) {
		settingsDataDao.openCurrentSession();
		SettingsData settingsData = settingsDataDao.findById(id);
		settingsDataDao.closeCurrentSession();
		return settingsData;
	}
}
