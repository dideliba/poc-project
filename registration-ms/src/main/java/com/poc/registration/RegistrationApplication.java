package com.poc.registration;


//import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * Class that represents the registration microservice application
 * @author didel
 *
 */
@SpringBootApplication
/*@OpenAPIDefinition(info =
@Info(title = "Registration API", version = "1.0", description = "Registration User API v1.0"))
*/
@OpenAPIDefinition
public class RegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistrationApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(
			@Value("${openapi.service.title}") String serviceTitle,
			@Value("${openapi.service.version}") String serviceVersion,
			@Value("${openapi.service.url}") String url) {
		return new OpenAPI()
				.servers(List.of(new Server().url(url)))
				.info(new Info().title(serviceTitle).version(serviceVersion));
	}
}
