package org.misspenalty.hangry.scraper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import org.misspenalty.hangry.Dish;
import org.misspenalty.hangry.Restaurant;
import org.openqa.selenium.NoSuchElementException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ScrapeData {
	private static SessionFactory sessionFactory;

	public static void main(String[] args) throws IOException,
			ParserConfigurationException, SAXException,
			XPathExpressionException {
		String siteMap = run("https://www.foodora.at/sitemap.xml");
		// System.out.println(siteMap);

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document xmlDocument = builder.parse(new ByteArrayInputStream(siteMap
				.getBytes(StandardCharsets.UTF_8)));
		XPath xPath = XPathFactory.newInstance().newXPath();
		String expression = "/urlset/url/loc";
		NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(
				xmlDocument, XPathConstants.NODESET);

		List<String> restaurants = new LinkedList<String>();

		for (int i = 340; i < nodeList.getLength(); i++) {
			String link = nodeList.item(i).getTextContent();
			if (link.contains(".at/chain/") || link.contains(".at/restaurant/")) {
				System.out.println(link);
				restaurants.add(link);
			}
		}

		// Document document = new SAXReader().read(new StringReader(siteMap));
		// String status = document.valueOf("/urlset/url/loc");
		// System.out.println(status);
		System.setProperty("webdriver.gecko.driver",
				"/home/misspenalty/Selenium/geckodriver");
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setHeadless(true);
		//firefoxOptions
		WebDriver driver = new FirefoxDriver();

		Session session = createConnection();
		for (String restaurantPage : restaurants) {
			String restaurantName = scrapeNameFromRestaurantPage(
					restaurantPage, driver);
			if (restaurantName == null) {
				System.out.println("Failed scraping: " + restaurantPage);
				continue;
			}
			Restaurant restaurant = new Restaurant(restaurantName,
					restaurantPage);
			session.beginTransaction();
			session.save(restaurant);
			LinkedList<Dish> foundDishes = scrapeDishesFromRestaurantPage(
					restaurantPage, driver);
			for (Dish fd : foundDishes) {
				fd.setRestaurant(restaurant);
			}
			restaurant.setDishes(foundDishes);

			for (Dish foundDish : foundDishes) {
				session.save(foundDish);
			}
			session.getTransaction().commit();

		}
		session.close();
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
			e.printStackTrace();
			StandardServiceRegistryBuilder.destroy(registry);
		}
		// create a couple of events...
		Session session = sessionFactory.openSession();
		return session;
	}

	static public String run(String url) throws IOException {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(url).build();

		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	static public LinkedList<Dish> scrapeDishesFromRestaurantPage(
			String restaurantPage, WebDriver driver) {
		driver.get(restaurantPage);

		LinkedList<Dish> foundDishes = new LinkedList<Dish>();
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

	static public String scrapeNameFromRestaurantPage(String restaurantPage,
			WebDriver driver) {

		driver.get(restaurantPage);
		System.out.println(driver.getPageSource());
		try {
		 return driver.findElement(
				By.cssSelector("div.vendor-info-main-headline.item")).getText();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
}