package org.misspenalty.hangry;


import javax.persistence.*;

@Entity 
@Table (name="Dish")
public class Dish {
	private String name;
	private String description;
	@Id
	@GeneratedValue(generator="increment")
	private Integer id;
	@ManyToOne
    @JoinColumn(name="restaurant_id")
	private Restaurant restaurant;
	public Dish(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public String toString() {
		return "Dish [name=" + name + ", ingredients=" + description + "]";
	}
}
