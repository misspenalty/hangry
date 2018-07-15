package org.misspenalty.hangry;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class ScrapeData {
	private static SessionFactory sessionFactory;

	public static void main(String[] args) {
		System.setProperty("webdriver.gecko.driver",
				"/home/misspenalty/Selenium/geckodriver");
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setHeadless(true);
		WebDriver driver = new FirefoxDriver(firefoxOptions);

		LinkedList<String> restaurantPages = new LinkedList<>();
		restaurantPages
				.add("https://www.foodora.at/restaurant/s1ch/dhaka-indisches-bistro");

		for (String restaurantPage : restaurantPages) {
			LinkedList<Dish> foundDishes = scrapeRestaurantPage(restaurantPage,
					driver);
			Session session = createConnection();
			session.beginTransaction();
			for (Dish foundDish : foundDishes) {
				session.save(foundDish);
			}
			session.getTransaction().commit();
			session.close();
		}

		driver.quit();

	}

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
			StandardServiceRegistryBuilder.destroy(registry);
		}
		// create a couple of events...
		Session session = sessionFactory.openSession();
		return session;
	}

	static public LinkedList<Dish> scrapeRestaurantPage(String restaurantPage,
			WebDriver driver) {
		driver.get(restaurantPage);

		LinkedList<Dish> foundDishes = new LinkedList<>();
		List<WebElement> dishes = driver.findElements(By
				.cssSelector("div.dish-info"));
		for (WebElement dish : dishes) {
			String dishName = dish.findElement(
					By.cssSelector("h3.dish-name.fn.p-name")).getText();
			Dish foundDish = new Dish(dishName);
			if (!dish.findElements(
					By.cssSelector("p.dish-description.e-description"))
					.isEmpty()) {
				String dishDescription = dish.findElement(
						By.cssSelector("p.dish-description.e-description"))
						.getText();
				foundDish.setDescription(dishDescription);
			}
			foundDishes.add(foundDish);
		}
		for (Dish fd : foundDishes) {
			System.out.println(fd);
		}

		return foundDishes;
	}

}