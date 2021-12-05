package model.daos;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.db.HibernateUtil;
import model.entities.SettingsData;

public class SettingsDataDao {
	private Session currentSession;
	private Transaction currentTransaction;

	public SettingsDataDao() {
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


	public void insert(SettingsData entity) {
		getCurrentSession().save(entity);
	}

	public void update(SettingsData entity) {
		getCurrentSession().update(entity);
	}

	public SettingsData findById(int id) {
		SettingsData settingsData = (SettingsData) getCurrentSession().get(SettingsData.class, id);
		return settingsData;
	}

	public void delete(SettingsData entity) {
		getCurrentSession().delete(entity);
	}
}

