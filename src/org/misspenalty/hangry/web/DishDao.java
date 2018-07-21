package org.misspenalty.hangry.web;
import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface DishDao extends CrudRepository<Dish, Integer> {
	
	List<Dish> findByNameLike(String dishName);

}
