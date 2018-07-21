package org.misspenalty.hangry.web;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Restaurant")
public class Restaurant {
	private String name;
	@Id
	@GeneratedValue(generator="increment")
	private Integer id;
	@Column(name="restaurantPage")
	private String restaurantPage;
	@OneToMany(mappedBy = "restaurant")
	private List<Dish> dishes;
	
	public Restaurant() {
		
	}
	public Restaurant(String name, String restaurantPage) {
		this.name = name;
		this.restaurantPage = restaurantPage;
	}

	public void setDishes(List<Dish> dishes) {
		this.dishes = dishes;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getRestaurantPage() {
		return this.restaurantPage;
	}

}
