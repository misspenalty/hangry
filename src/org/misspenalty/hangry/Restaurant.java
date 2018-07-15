package org.misspenalty.hangry;

import java.util.List;

public class Restaurant {
	private String name;
	private String restaurantPage;
	private List<Dish> dishes;
	
	public Restaurant(String name, String restaurantPage) {
		this.name = name;
		this.restaurantPage = restaurantPage;
	}
	
	public void setDishes(List<Dish> dishes) {
		this.dishes = dishes;
	}

}
