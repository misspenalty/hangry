package org.misspenalty.hangry.web;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories("org.misspenalty")
public class Application extends SpringBootServletInitializer  {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
//	
//	@Bean
//	public CommandLineRunner demo(DishDao repository) {
//		return (args) -> {
//			for (Dish customer : repository.findAll()) {
//				System.out.println(customer);
//			}
//		
//		};
//	}
}