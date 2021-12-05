package model.daos;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.db.HibernateUtil;
import model.entities.ClosedWristband;

public class ClosedWristbandDao {
	private Session currentSession;
	private Transaction currentTransaction;
	
	public ClosedWristbandDao() {		
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
	public void create(ClosedWristband entity) {
		getCurrentSession().save(entity);
	}
	
	public void update(ClosedWristband entity) {
		getCurrentSession().update(entity);
	}
	
	public ClosedWristband findById(long id) {
		ClosedWristband wristband = (ClosedWristband) getCurrentSession().get(ClosedWristband.class, id);
		return wristband;
	}
	
	public void delete(ClosedWristband entity) {
		getCurrentSession().delete(entity);
	}
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public Long countWristbandByColor(String specifiedColor) {		
		Query query = getCurrentSession().createQuery("select count(*) from ClosedWristband where color = :specifiedColor");
		query.setString("specifiedColor", specifiedColor);
		Long count = (Long)query.uniqueResult();	
		return count;
	}
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public Long countByColorIfItUnder5Hours(String specifiedColor) {
		Query query = getCurrentSession().createQuery("select count(*) from ClosedWristband where color = :specifiedColor and overpaymentAmount < 3600");
		query.setString("specifiedColor", specifiedColor);
		Long count = (Long)query.uniqueResult();	
		return count;
	}
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public Long countOverTimeByColor(String specifiedColor) {
		Query query = getCurrentSession().createQuery("select count(*) from ClosedWristband where color = :specifiedColor and overpaymentRequired  = true");
		query.setString("specifiedColor", specifiedColor);
		Long count = (Long)query.uniqueResult();	
		return count;
	}	
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public Long countByPercentAndColor(int percent, String specifiedColor) {
		Query query = getCurrentSession().createQuery("select count(*) from ClosedWristband where discountPercent =:percent and color = :specifiedColor" );
		query.setInteger("percent", percent);
		query.setString("specifiedColor", specifiedColor);
		Long count = (Long)query.uniqueResult();	
		return count;
	}
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public Long countByDateAndColor(Date dateFrom, Date dateTo, String specifiedColor) {
		Query query = getCurrentSession().createQuery("select count(*) from ClosedWristband where color = :specifiedColor and closeingDateTime > :dateFrom and closeingDateTime < : dateTo");
		query.setString("specifiedColor", specifiedColor);
		query.setDate("dateFrom", dateFrom);
		query.setDate("dateTo", dateTo);
		Long count = (Long)query.uniqueResult();	
		return count;
	}
	
	@SuppressWarnings("deprecation")
	public Long sumSpentTime(String specifiedColor) {
		long seconds = 0;
		List<ClosedWristband> listOfClosedWristbands = findAll(specifiedColor);
		for (ClosedWristband wristband : listOfClosedWristbands) {
				seconds += wristband.getTimeSpent().getHours() * 3600 + wristband.getTimeSpent().getMinutes() * 60 + wristband.getTimeSpent().getSeconds();			
		}
		return seconds;
	}	
	
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public List<ClosedWristband> findAll(String specifiedColor) {
		Query query = getCurrentSession().createQuery("from ClosedWristband where color = :specifiedColor and overpaymentAmount < 3600");		
		query.setString("specifiedColor", specifiedColor);
		
		return (List<ClosedWristband>) query.list();		
	}
}
