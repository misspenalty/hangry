package org.misspenalty.hangry.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
		return dishDao.findByNameContainingOrderByRestaurantNameAscNameAsc(dish);
	}

}
