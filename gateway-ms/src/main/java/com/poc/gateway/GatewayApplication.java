package com.poc.gateway;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import java.util.List;

@SpringBootApplication
@OpenAPIDefinition
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Autowired
	RouteDefinitionLocator locator;

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