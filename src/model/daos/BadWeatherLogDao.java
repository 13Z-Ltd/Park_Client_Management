package model.daos;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.db.HibernateUtil;
import model.entities.BadWeatherLog;

public class BadWeatherLogDao {
	private Session currentSession;
	private Transaction currentTransaction;
	
	public BadWeatherLogDao() {
		
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
	public void create(BadWeatherLog entity) {
		getCurrentSession().save(entity);
	}
	
	public void update(BadWeatherLog entity) {
		getCurrentSession().update(entity);
	}
	
	public BadWeatherLog findById(long id) {
		BadWeatherLog log = (BadWeatherLog) getCurrentSession().get(BadWeatherLog.class, id);
		return log;
	}
	
	public void delete(BadWeatherLog entity) {
		getCurrentSession().delete(entity);
	}

	@SuppressWarnings("rawtypes")
	public Long getNumberOfBadWeatherEvents() {
		Query query = getCurrentSession().createQuery("select count(*) from BadWeatherLog where elapsedTime IS NOT NULL");
		return (Long)query.uniqueResult();		
	}
	
	@SuppressWarnings("deprecation")
	public Long getSumOfBadWeatherTimes() {
		long seconds = 0;
		List<BadWeatherLog> listOfBadWeatherLogs = findAll();
		for (BadWeatherLog log : listOfBadWeatherLogs) {
			if(log.getElapsedTime() != null) {
				seconds += log.getElapsedTime().getHours() * 3600 + log.getElapsedTime().getMinutes() * 60 + log.getElapsedTime().getSeconds();
			}
		}
		return seconds;
	}
	
	@SuppressWarnings("unchecked")	
	public List<BadWeatherLog> findAll() {		
		List<BadWeatherLog> logs = (List<BadWeatherLog>) getCurrentSession().createQuery("from BadWeatherLog").list();
		return logs;
	}
	
	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	private List<BadWeatherLog> findAllByDate(Date dateFrom, Date dateTo) {		
		Query query = getCurrentSession().createQuery("from BadWeatherLog where startingDateTime > :dateFrom and endingDateTime < : dateTo");
		query.setDate("dateFrom", dateFrom);
		query.setDate("dateTo", dateTo);
		List<BadWeatherLog> logs = (List<BadWeatherLog>) query.list();
		return logs;
	}
	
	@SuppressWarnings("rawtypes")
	public Long getNumberOfCanceledWristbands() {		
		Query query = getCurrentSession().createQuery("select sum(amountOfAffectedWristbands) from BadWeatherLog where elapsedTime IS NOT NULL");
		return (Long)query.uniqueResult();
	}
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public Long countByDate(Date dateFrom, Date dateTo) {		
		Query query = getCurrentSession().createQuery("select count(*) from BadWeatherLog where startingDateTime > :dateFrom and endingDateTime < : dateTo");
		query.setDate("dateFrom", dateFrom);
		query.setDate("dateTo", dateTo);
		Long count = (Long)query.uniqueResult();	
		return count;
	}

	@SuppressWarnings("deprecation")
	public Long getSumOfBadWeatherTimesByDate(Date dateFrom, Date dateTo) {
		long seconds = 0;
		List<BadWeatherLog> listOfBadWeatherLogs = findAllByDate(dateFrom, dateTo);
		for (BadWeatherLog log : listOfBadWeatherLogs) {
			if(log.getElapsedTime() != null) {
				seconds += log.getElapsedTime().getHours() * 3600 + log.getElapsedTime().getMinutes() * 60 + log.getElapsedTime().getSeconds();
			}
		}
		return seconds;
	}

	@SuppressWarnings({ "rawtypes", "deprecation" })
	public Long getNumberOfCanceledWristbandsByDate(Date dateFrom, Date dateTo) {
		Query query = getCurrentSession().createQuery("select sum(amountOfAffectedWristbands) from BadWeatherLog where startingDateTime > :dateFrom and endingDateTime < : dateTo and elapsedTime IS NOT NULL");
		query.setDate("dateFrom", dateFrom);
		query.setDate("dateTo", dateTo);
		return (Long)query.uniqueResult();
	}
}
