package model.daos;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.db.HibernateUtil;
import model.entities.ActiveWristband;

public class ActiveWristbandDao {
	private Session currentSession;
	private Transaction currentTransaction;
	
	public ActiveWristbandDao() {		
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
	public void create(ActiveWristband entity) {
		getCurrentSession().save(entity);
	}
	
	public void update(ActiveWristband entity) {
		getCurrentSession().update(entity);
	}
	
	public ActiveWristband findById(long id) {
		ActiveWristband wristband = (ActiveWristband) getCurrentSession().get(ActiveWristband.class, id);
		return wristband;
	}
	
	public void delete(ActiveWristband entity) {
		getCurrentSession().delete(entity);
	}
	
	@SuppressWarnings("unchecked")
	public List<ActiveWristband> findAll() {		
		List<ActiveWristband> wristbands = (List<ActiveWristband>) getCurrentSession().createQuery("from ActiveWristband").list();
		return wristbands;
	}
	
	@SuppressWarnings("rawtypes")
	public Long countActive() {		
		Query query = getCurrentSession().createQuery("select count(*) from ActiveWristband where activating_date_time IS NOT NULL");
		Long count = (Long) query.uniqueResult();
		return count;
	}
	
	@SuppressWarnings("rawtypes")
	public Long countDressing() {		
		Query query = getCurrentSession().createQuery("select count(*) from ActiveWristband where activating_date_time IS NULL");
		Long count = (Long) query.uniqueResult();
		return count;
	}
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public Long countMoreThen(int hours, boolean isWhite) {
		Query query;
		
		Timestamp startDate = new Timestamp(System.currentTimeMillis());
		startDate.setHours(startDate.getHours() - hours);
		if(isWhite) {
			query = getCurrentSession().createQuery(
					"select count(*) from ActiveWristband where activating_date_time IS NOT NULL and activating_date_time < :startDate and color='WHITE'"); //and activating_date_time < :startDate and color='WHITE'"); activating_date_time IS NOT NULL and
		}else {
			query = getCurrentSession().createQuery(
					"select count(*) from ActiveWristband where activating_date_time < :startDate and color<>'WHITE'");
		}
		query.setTimestamp("startDate", startDate);
		Long count = (Long)query.uniqueResult();		 
		return count;
	}
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public Long countWristbandByColor(String specifiedColor) {
		Query query = getCurrentSession().createQuery("select count(*) from ActiveWristband where color = :specifiedColor and activating_date_time IS NOT NULL");
		query.setString("specifiedColor", specifiedColor);
		Long count = (Long)query.uniqueResult();	
		return count;
	}
	
	@SuppressWarnings("deprecation")
	public void updateAllBadWeatherElapsedTime(Time elapsedTime) {
		List<ActiveWristband> listOfActiveWristbands = findAll();
		for (ActiveWristband wristband : listOfActiveWristbands) {
			if(wristband.getActivatingDateTime() != null) {
				if(wristband.getBadWeatherElapsedTime() == null) {
					wristband.setBadWeatherElapsedTime(elapsedTime);
					update(wristband);
				}else {
					if(elapsedTime != null) {						
						int timeInsec = (wristband.getBadWeatherElapsedTime().getHours() * 3600 + wristband.getBadWeatherElapsedTime().getMinutes() * 60 + wristband.getBadWeatherElapsedTime().getSeconds())
											+ (elapsedTime.getHours() * 3600 + elapsedTime.getMinutes() * 60 + elapsedTime.getSeconds());
						
						int hours = (int)(timeInsec / (60*60));
						int minutes = (int) (timeInsec % (60*60) / 60);
						int seconds = (int) (timeInsec % 60);
						wristband.setBadWeatherElapsedTime(new Time(hours, minutes, seconds));
						update(wristband);
					}	
				}
			}
		}
	}
	
	public void deleteAll() {
		List<ActiveWristband> listOfActiveWristbands = findAll();
		for (ActiveWristband wristband : listOfActiveWristbands) {
			delete(wristband);
		}
	}
}
