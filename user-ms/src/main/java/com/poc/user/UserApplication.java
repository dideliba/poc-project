package com.poc.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * Class that represents the user microservice application
 * @author didel
 *
 */
@EnableReactiveMongoRepositories
@OpenAPIDefinition(info =
@Info(title = "UserResponse API", version = "1.0", description = "Documentation UserResponse API v1.0"))
@SpringBootApplication
public class UserApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}
}
