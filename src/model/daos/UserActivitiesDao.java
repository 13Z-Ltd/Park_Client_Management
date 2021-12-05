package model.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.db.HibernateUtil;
import model.entities.UserActivities;

public class UserActivitiesDao {
	private Session currentSession;
	private Transaction currentTransaction;

	public UserActivitiesDao() {
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

	public void persist(UserActivities entity) {
		getCurrentSession().save(entity);
	}

	public void update(UserActivities entity) {
		getCurrentSession().update(entity);
	}
	
	public UserActivities findById(int id) {
		UserActivities activity = (UserActivities) getCurrentSession().get(UserActivities.class, id);
		return activity;
	}
	
	public void delete(UserActivities entity) {
		getCurrentSession().delete(entity);
	}
	
	public void deleteAll() {
		List<UserActivities> listOfActivities = findAll();
		for (UserActivities activity : listOfActivities) {
			delete(activity);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<UserActivities> findAll() {		
		List<UserActivities> activities = (List<UserActivities>) getCurrentSession().createQuery("from UserActivities").list();
		return activities;
	}

}