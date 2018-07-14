package site;

public class Dish {
	String name;
	String ingredients;
	
	public Dish(String name) {
		this.name = name;
	}
	
	public void addIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public String toString() {
		return "Dish [name=" + name + ", ingredients=" + ingredients + "]";
	}
}
