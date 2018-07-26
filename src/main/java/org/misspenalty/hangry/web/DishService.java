package org.misspenalty.hangry.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DishService {
	
	@Autowired
	private DishRepository dishRepository;
	
	public List<Dish> getDishesContainingName(String dishName) {
		if (dishName.length() <= 2) {
			throw new IllegalArgumentException("Search query must contain at least 3 characters.");
		}
		return dishRepository.findByNameContainingOrderByRestaurantNameAscNameAsc(dishName);
	}
	
}
