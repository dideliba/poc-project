package com.poc.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Class that represents the notification microservice application
 * @author didel
 *
 */
@SpringBootApplication
@EnableKafka
public class NotificationApplication {
	public static void main(String[] args) {
		SpringApplication.run(NotificationApplication.class, args);
	}
}
