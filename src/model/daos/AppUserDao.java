package model.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.db.HibernateUtil;
import model.entities.AppUser;

public class AppUserDao {
	private Session currentSession;
	private Transaction currentTransaction;

	public AppUserDao() {
	}

	public Session openCurrentSession() {
		currentSession = HibernateUtil.getSessionFactory().openSession();
		return currentSession;
	}

	public Session openCurrentSessionWithTrabsaction() {
		currentSession = HibernateUtil.getSessionFactory().openSession();
		currentTransaction = currentSession.beginTransaction();
		return currentSession;
	}

	public void closeCurrentSession() {
		currentSession.close();
	}

	public void closeCurrentSessionWithTrabsaction() {
		currentTransaction.commit();
		currentSession.close();
	}

	public Session getCurrentSession() {
		return currentSession;
	}

	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}

	public Transaction getCurrentTransaction() {
		return currentTransaction;
	}

	public void setCurrentTransaction(Transaction currentTransaction) {
		this.currentTransaction = currentTransaction;
	}		

	public void insert(AppUser entity) {
		getCurrentSession().save(entity);
	}

	public void update(AppUser entity) {
		getCurrentSession().update(entity);
	}

	public AppUser findById(int id) {
		AppUser appUser = (AppUser) getCurrentSession().get(AppUser.class, id);
		return appUser;
	}

	@SuppressWarnings("unchecked")
	public List<AppUser> findByUserName(String name) {
		String hql = "FROM AppUser a WHERE a.userName = '" + name + "'";		
		List<AppUser> users = getCurrentSession().createQuery(hql).list();
		return users;
	}

	@SuppressWarnings("unchecked")
	public List<AppUser> findAll() {
		List<AppUser> users = (List<AppUser>) getCurrentSession().createQuery("from AppUser").list();
		return users;
	}

	public void delete(AppUser entity) {
		getCurrentSession().delete(entity);
	}

	public void deleteAll() {
		List<AppUser> listOfUsers = findAll();
		for (AppUser appUser : listOfUsers) {
			delete(appUser);
		}
	}
}