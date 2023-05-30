package com.poc.registration.controller;

import com.poc.registration.documentation.openapi.OpenApiRegisterUser;
import com.poc.registration.domain.UserInfo;
import com.poc.registration.domain.UserResponse;
import com.poc.registration.service.RegistrationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;


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
		public Mono<UserResponse> registerUser(@RequestBody @Valid UserInfo user) {
			return registrationService.registerUser(user);
	    }
}