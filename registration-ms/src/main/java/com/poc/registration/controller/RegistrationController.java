package com.poc.registration.controller;

import com.poc.registration.documentation.openapi.OpenApiRegisterUser;
import com.poc.registration.domain.User;
import com.poc.registration.service.RegistrationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


/**
 * The REST controller implements the REST API for registration related and CRUD operations
 * @author didel
 *
 */
@RestController
@RequestMapping("/v1/registrations/")
@Tag(name = "Registration actions API", description = "registration related actions")
public class RegistrationController {

		@Autowired
		private RegistrationService registrationService;

		@ResponseStatus(HttpStatus.CREATED)
		@PostMapping("/user.register")
	    @OpenApiRegisterUser
		public Mono<User> registerUser(@RequestBody User user) {
			return registrationService.registerUser(user);
	    }
}
