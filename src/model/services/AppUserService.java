package model.services;

import java.util.List;

import model.daos.AppUserDao;
import model.db.PBEStringEncryptor;
import model.entities.AppUser;

public class AppUserService {

	private static AppUserDao appUserDao;
	private int loginInfo;
	
	public AppUserService() {
		appUserDao = new AppUserDao();
	}
	
	public void insert(AppUser entity) {
		appUserDao.openCurrentSessionWithTrabsaction();
		appUserDao.insert(entity);
		appUserDao.closeCurrentSessionWithTrabsaction();
	}
	
	public void update(AppUser entity) {
		appUserDao.openCurrentSessionWithTrabsaction();
		appUserDao.update(entity);
		appUserDao.closeCurrentSessionWithTrabsaction();
	}
	
	public AppUser findById(int id) {
		appUserDao.openCurrentSession();
		AppUser appUser = appUserDao.findById(id);
		appUserDao.closeCurrentSession();
		return appUser;
	}
	
	public AppUser findByUserName(String name) {
		appUserDao.openCurrentSession();
		List<AppUser> usersList = appUserDao.findByUserName(name);
		appUserDao.closeCurrentSession();
		
		if (usersList.size() != 0) {
			return (AppUser)usersList.get(0);
		}
		else {
			return null;
		}
	}	
	
	public AppUser findByUserName(String name, String pass) {
		appUserDao.openCurrentSession();
		List<AppUser> usersList = appUserDao.findByUserName(name);
		appUserDao.closeCurrentSession();
		
		//??
		loginInfo = 0;
		if (usersList.size() == 0) {
			return null;
		}
		else {
			AppUser user = usersList.get(0);
			if (pass.equals(PBEStringEncryptor.getDecryptedString(user.getPassword()))) {
				if(user.getRole().equals("REGISTER"))
					loginInfo = 10;
				if(user.getRole().equals("DRESSING"))
					loginInfo = 11;
				if(user.getRole().equals("ADMIN"))
					loginInfo = 111;
				return user;
			}
			else {
				loginInfo = 1;
				return null;
			}	
		}
	}
	
	public List<AppUser> findAll() {
		appUserDao.openCurrentSession();
		List<AppUser> appUsers = appUserDao.findAll();
		appUserDao.closeCurrentSession();
		return appUsers;
	}
	
	public void delete(String name) {
		appUserDao.openCurrentSessionWithTrabsaction();
		List<AppUser> appUsers = appUserDao.findByUserName(name);
		for (AppUser appUser : appUsers) {
			appUserDao.delete(appUser);
		}		
		appUserDao.closeCurrentSessionWithTrabsaction();
	}
	
	public void deleteAll() {
		appUserDao.closeCurrentSessionWithTrabsaction();
		appUserDao.deleteAll();
		appUserDao.closeCurrentSessionWithTrabsaction();
	}
	
	public AppUserDao appUserDao() {
		return appUserDao;
	}

	public int getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(int loginInfo) {
		this.loginInfo = loginInfo;
	}		
}
