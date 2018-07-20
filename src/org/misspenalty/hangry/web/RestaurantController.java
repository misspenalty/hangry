package org.misspenalty.hangry.web;

import java.util.LinkedList;
import java.util.List;

import org.misspenalty.hangry.Restaurant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {

    @RequestMapping(value="/restaurant", method = RequestMethod.GET)
    public @ResponseBody List<Restaurant> restaurant(@RequestParam(value="dish") String dish) {
    	return new LinkedList<Restaurant>();
    }
 
  
}
