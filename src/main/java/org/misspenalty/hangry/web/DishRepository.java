package org.misspenalty.hangry.web;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface DishRepository extends CrudRepository<Dish, Integer> {

	List<Dish> findByNameContainingOrderByRestaurantNameAscNameAsc(String dishName);

}
