package org.misspenalty.hangry;

import java.util.List;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hello world!
 * 
 */
public class App {
	private static SessionFactory sessionFactory;

	public static void main(String[] args) {

		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure() // configures settings from hibernate.cfg.xml
				.build();
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata()
					.buildSessionFactory();
		} catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had
			// trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy(registry);
		}
		// create a couple of events...
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(new Dish("Our very first event!"));
		session.save(new Dish("A follow up event"));
		session.getTransaction().commit();
		session.close();

		// now lets pull events from the database and list them
		// session = sessionFactory.openSession();
		// session.beginTransaction();
		// List result = session.createQuery("from Event").list();
		// for (Event event : (List<Event>) result) {
		// System.out.println("Event (" + event.getDate() + ") : "
		// + event.getTitle());
		// }
		// session.getTransaction().commit();
		// session.close();
	
		if (sessionFactory != null) {
			sessionFactory.close();
		}
	}

}