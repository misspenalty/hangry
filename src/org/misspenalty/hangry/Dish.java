package org.misspenalty.hangry;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table (name="Dish")
public class Dish {
	private String name;
	private String description;
	@Id
	@GeneratedValue(generator="increment")
	private Integer id;
	
	public Dish(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Dish [name=" + name + ", ingredients=" + description + "]";
	}
}
