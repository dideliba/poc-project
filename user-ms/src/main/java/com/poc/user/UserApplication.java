package com.poc.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.text.DateFormat;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * Class that represents the user microservice application
 * @author didel
 *
 */
@EnableReactiveMongoRepositories
@EnableTransactionManagement

@OpenAPIDefinition(info =
@Info(title = "UserResponse API", version = "1.0", description = "Documentation UserResponse API v1.0"))
@SpringBootApplication
public class UserApplication {

	//DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
/*
	@Bean
	public DateTimeFormatter iso8601DateTimeFormatter() {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
				.withZone(ZoneOffset.UTC);
	}
*/
	/*@Bean
	public WebFluxConfigurer webFluxConfigurer(DateTimeFormatter dateTimeFormatter) {
		return new WebFluxConfigurer() {
			@Override
			public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
				ObjectMapper objectMapper = new ObjectMapper()
						.registerModule(new JavaTimeModule())
						.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
						.setTimeZone(TimeZone.getTimeZone("UTC"));
				configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
			}
		};
	}*/

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}
}
