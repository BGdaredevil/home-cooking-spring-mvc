package com.cooking.home_recipes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;

import java.util.Properties;

//@SpringBootApplication
@SpringBootApplication(exclude = {JacksonAutoConfiguration.class})
public class HomeRecipesApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(HomeRecipesApplication.class);
        Properties config = new Properties();
        config.put("server.port", "3030");
        config.put("spring.http.converters.preferred-json-mapper", "gson");
        app.setDefaultProperties(config);

        app.run(args);
//		SpringApplication.run(HomeRecipesApplication.class, args);
    }

}
