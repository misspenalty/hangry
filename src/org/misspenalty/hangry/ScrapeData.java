package org.misspenalty.hangry;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
public class ScrapeData {

	public static void main(String[] args) {
		  // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
		System.setProperty("webdriver.gecko.driver", "/home/misspenalty/Selenium/geckodriver");
		 FirefoxOptions firefoxOptions = new FirefoxOptions();
		 firefoxOptions.setHeadless(true);
		WebDriver driver = new FirefoxDriver(firefoxOptions);

        // And now use this to visit Google
        driver.get("https://www.foodora.at/restaurant/s9og/salpicon");
        // Alternatively the same thing can be done like this
        //driver.navigate().to("http://www.google.com");

        // Find the Denvycom search input element by its name
        //WebElement element = driver.findElement(By.id("s"));

        // Enter something to search for
        //element.sendKeys("research");

        // Now submit the form. WebDriver will find the form for us from the element
        //element.submit();

        // Check the title of the page
        System.out.println("Page title is: " + driver.getTitle());
        // Should see: "All Articles on Denvycom related to the Keyword "Research""

        //Get the title of all posts
        LinkedList<Dish> foundDishes = new LinkedList<>();
        List<WebElement> dishes = driver.findElements(By.cssSelector("div.dish-info"));
        for (WebElement dish : dishes) {
        	String dishName = dish.findElement(By.cssSelector("h3.dish-name.fn.p-name")).getText();
        	Dish foundDish = new Dish(dishName);
        	if (!dish.findElements(By.cssSelector("p.dish-description.e-description")).isEmpty()) {
        		String dishIngredients = dish.findElement(By.cssSelector("p.dish-description.e-description")).getText();
        		foundDish.addIngredients(dishIngredients);
        	}
        	foundDishes.add(foundDish);
        }
        for (Dish fd : foundDishes) {
        	System.out.println(fd);
        }
        //List<WebElement> titles = driver.findElements(By.cssSelector("h3.dish-name.fn.p-name"));
        //List<WebElement> dates = driver.findElements(By.cssSelector("p.dish-description.e-description"));
        //System.out.println(" =============== Denvycom Articles on Research ================= ");
		//for (int j = 0; j < titles.size(); j++) {
		//	System.out.println("\t - " + titles.get(j).getText() ) ;
		//}

        //Close the browser
        driver.quit();

	}

}