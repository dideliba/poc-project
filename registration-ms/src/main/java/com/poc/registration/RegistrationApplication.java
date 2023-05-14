package com.poc.registration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * Class that represents the registration microservice application
 * @author didel
 *
 */
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Registration API", version = "1.0", description = "Documentation Registration User API v1.0"))
public class RegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistrationApplication.class, args);
	}
}
