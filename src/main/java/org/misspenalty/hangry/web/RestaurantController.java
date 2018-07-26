package org.misspenalty.hangry.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableJpaRepositories("org.misspenalty")
public class RestaurantController {

	@Autowired
	private DishDao dishDao;

	@RequestMapping(value = "/restaurant", method = RequestMethod.GET)
	public @ResponseBody List<Dish> restaurant(@RequestParam(value = "dish") String dish) {
		if (dish.length() <= 2) {
			throw new IllegalArgumentException();
		}
		return dishDao.findByNameContainingOrderByRestaurantNameAscNameAsc(dish);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	void handleBadRequests(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value(), "Search query must contain at least 3 characters.");
	}

}
