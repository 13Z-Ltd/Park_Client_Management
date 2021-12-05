package model.db;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import model.entities.ActiveWristband;
import model.entities.AppUser;
import model.entities.BadWeatherLog;
import model.entities.ClosedWristband;
import model.entities.RegisteredWristband;
import model.entities.SettingsData;
import model.entities.UserActivities;

public class HibernateUtil {
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public boolean testConnection() {
		return sessionFactory != null;
	}

	public boolean makeConnection() {
		// loads configuration and mappings
		Configuration configuration = new Configuration().configure();
		
		// Add annotated classes
		configuration.addAnnotatedClass(AppUser.class);
		configuration.addAnnotatedClass(UserActivities.class);
		configuration.addAnnotatedClass(RegisteredWristband.class);
		configuration.addAnnotatedClass(ActiveWristband.class);
		configuration.addAnnotatedClass(ClosedWristband.class);
		configuration.addAnnotatedClass(SettingsData.class);
		configuration.addAnnotatedClass(BadWeatherLog.class);

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();

		// builds a session factory from the service registry
		try {
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Exception e) {
			sessionFactory = null;
			return false;
		}
		
		return true;
	}

	public boolean makeConnectionToNewIp(String url) {
		// loads configuration and mappings
		Configuration configuration = new Configuration().configure();
		configuration.setProperty("hibernate.connection.url", url);

		// Add annotated classes
		configuration.addAnnotatedClass(AppUser.class);
		configuration.addAnnotatedClass(UserActivities.class);
		configuration.addAnnotatedClass(RegisteredWristband.class);
		configuration.addAnnotatedClass(ActiveWristband.class);
		configuration.addAnnotatedClass(ClosedWristband.class);
		configuration.addAnnotatedClass(SettingsData.class);
		configuration.addAnnotatedClass(BadWeatherLog.class);

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();

		// builds a session factory from the service registry
		try {
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Exception e) {
			sessionFactory = null;
			return false;
		}
		return true;
	}

	/*
	 * public class HibernateUtil { private static SessionFactory sessionFactory;
	 * 
	 * public static SessionFactory getSessionFactory() { if (sessionFactory ==
	 * null) { // loads configuration and mappings Configuration configuration = new
	 * Configuration().configure(); // Add annotated classes
	 * configuration.addAnnotatedClass(AppUser.class);
	 * configuration.addAnnotatedClass(UserActivities.class);
	 * configuration.addAnnotatedClass(RegisteredWristband.class);
	 * configuration.addAnnotatedClass(ActiveWristband.class);
	 * configuration.addAnnotatedClass(ClosedWristband.class);
	 * configuration.addAnnotatedClass(SettingsData.class);
	 * configuration.addAnnotatedClass(BadWeatherLog.class);
	 * 
	 * ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
	 * .applySettings(configuration.getProperties()).build();
	 * 
	 * // builds a session factory from the service registry try { sessionFactory =
	 * configuration.buildSessionFactory(serviceRegistry); } catch (Exception e) {
	 * throw new ExceptionInInitializerError(e); } }
	 * 
	 * return sessionFactory; }
	 * 
	 * public boolean testConnection() { boolean acceptConnection = true;
	 * 
	 * // loads configuration and mappings Configuration configuration = new
	 * Configuration().configure(); // Add annotated classes
	 * configuration.addAnnotatedClass(AppUser.class);
	 * configuration.addAnnotatedClass(UserActivities.class);
	 * configuration.addAnnotatedClass(RegisteredWristband.class);
	 * configuration.addAnnotatedClass(ActiveWristband.class);
	 * configuration.addAnnotatedClass(ClosedWristband.class);
	 * configuration.addAnnotatedClass(SettingsData.class);
	 * configuration.addAnnotatedClass(BadWeatherLog.class);
	 * 
	 * ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
	 * .applySettings(configuration.getProperties()).build();
	 * 
	 * // builds a session factory from the service registry try { sessionFactory =
	 * configuration.buildSessionFactory(serviceRegistry);
	 * 
	 * } catch (Exception e) { acceptConnection = false; } sessionFactory = null;
	 * 
	 * return acceptConnection; }
	 * 
	 * public boolean makeConnection(String url) { boolean acceptConnection = true;
	 * 
	 * // loads configuration and mappings Configuration configuration = new
	 * Configuration().configure();
	 * 
	 * configuration.setProperty("hibernate.connection.url", url);
	 * 
	 * // Add annotated classes configuration.addAnnotatedClass(AppUser.class);
	 * configuration.addAnnotatedClass(UserActivities.class);
	 * configuration.addAnnotatedClass(RegisteredWristband.class);
	 * configuration.addAnnotatedClass(ActiveWristband.class);
	 * configuration.addAnnotatedClass(ClosedWristband.class);
	 * configuration.addAnnotatedClass(SettingsData.class);
	 * configuration.addAnnotatedClass(BadWeatherLog.class);
	 * 
	 * ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
	 * .applySettings(configuration.getProperties()).build();
	 * 
	 * // builds a session factory from the service registry try { sessionFactory =
	 * configuration.buildSessionFactory(serviceRegistry);
	 * 
	 * } catch (Exception e) { acceptConnection = false; } sessionFactory = null;
	 * 
	 * return acceptConnection; }
	 */
}
