package org.misspenalty.hangry.web;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableJpaRepositories("org.misspenalty")
public class RestaurantController {
	private static SessionFactory sessionFactory;
	
	@Autowired
	private DishDao dishDao;
	
	static public Session createConnection() {
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
			e.printStackTrace();
			StandardServiceRegistryBuilder.destroy(registry);
		}
		// create a couple of events...
		Session session = sessionFactory.openSession();
		return session;
	}

    @RequestMapping(value="/restaurant", method = RequestMethod.GET)
    public @ResponseBody List<Dish> restaurant(@RequestParam(value="dish") String dish) {
    	List<Dish> dishes = new LinkedList<Dish>();
    	for (Dish dish1 : dishDao.findAll()) {
    		dishes.add(dish1);
    		System.out.println(dish1);
    	}
    	return dishes;
    	
//    	Session session = createConnection();
//    	String hql = "Select name FROM Dish, Restaurant";
//    	Query query = session.createQuery(hql);
//    	List results = query.list();
//    	return results;
    	//return new LinkedList<Restaurant>();
    }
 
  
}
