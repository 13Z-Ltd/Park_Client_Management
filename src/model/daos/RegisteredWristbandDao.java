package model.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.db.HibernateUtil;
import model.entities.RegisteredWristband;

public class RegisteredWristbandDao {
	private Session currentSession;
	private Transaction currentTransaction;
	
	public RegisteredWristbandDao() {		
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
	
	//
	public void create(RegisteredWristband entity) {
		getCurrentSession().save(entity);
	}
	
	public void update(RegisteredWristband entity) {
		getCurrentSession().update(entity);
	}
	
	public RegisteredWristband findById(long id) {
		RegisteredWristband wristband = (RegisteredWristband) getCurrentSession().get(RegisteredWristband.class, id);
		return wristband;
	}
	
	public void delete(RegisteredWristband entity) {
		getCurrentSession().delete(entity);
	}
	
	@SuppressWarnings("unchecked")
	public List<RegisteredWristband> findAll() {		
		List<RegisteredWristband> wristbands = (List<RegisteredWristband>) getCurrentSession().createQuery("from RegisteredWristband").list();
		return wristbands;
	}

	@SuppressWarnings({ "rawtypes", "deprecation" })
	public long countByColor(String color) {		
		Query query = getCurrentSession().createQuery(
				"select count(*) from RegisteredWristband where color = :color");
		query.setString("color", color);
		Long count = (Long)query.uniqueResult();		 
		return count;
	}	
}
