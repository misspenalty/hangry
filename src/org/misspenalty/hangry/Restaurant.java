package org.misspenalty.hangry;

import java.util.List;

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
	private String restaurantPage;
	@OneToMany(mappedBy = "restaurant")
	private List<Dish> dishes;

	public Restaurant(String name, String restaurantPage) {
		this.name = name;
		this.restaurantPage = restaurantPage;
	}

	public void setDishes(List<Dish> dishes) {
		this.dishes = dishes;
	}

}
