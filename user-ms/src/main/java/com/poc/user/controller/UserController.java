package com.poc.user.controller;

import com.poc.user.documentation.openapi.*;
import com.poc.user.domain.request.ParticularUserInfo;
import com.poc.user.domain.request.UserInfo;
import com.poc.user.domain.request.UserInfoList;
import com.poc.user.domain.response.UserResponse;
import com.poc.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * The REST controller implements the REST API for user related and CRUD operations
 * @author didel
 *
 */
@RestController
@RequestMapping("/v1/users")
@Tag(name = "User Resource CRUD REST API", description = "CRUD REST APIs - Create UserResponse, " +
		"Update User(s), Get User(s), (soft) Delete User(s)")
@Slf4j
public class UserController {

		@Autowired
		private UserService userService;

		@ResponseStatus(HttpStatus.CREATED)
	    @RequestMapping(method = RequestMethod.POST)
		@OpenApiCreateUser
		public Mono<UserResponse> createUser(@Valid @RequestBody UserInfo user) {
			return userService.createUser(user);
	    }

	    @ResponseStatus(HttpStatus.OK)
		@GetMapping("/{id}")
		@OpenApiReadUser
	    public Mono<UserResponse> readUser(@PathVariable String id) {
			return userService.readUser(id);
	    }

		@ResponseStatus(HttpStatus.OK)
		@GetMapping()
		@OpenApiReadUsers
		public Flux<UserResponse> readUsers(@RequestParam(name="id") List<String> ids) {
			return userService.readUsers(ids);
		}

		@ResponseStatus(HttpStatus.OK)
		@DeleteMapping("/{id}")
		@OpenApiDeleteUser
		public Mono<UserResponse> deleteUser(@PathVariable String id){
				return userService.deleteUser(id);
		}

		@ResponseStatus(HttpStatus.OK)
		@DeleteMapping()
		@OpenApiDeleteUsers
		public Flux<UserResponse> deleteUsers(@RequestParam(name="id") List<String> ids){
			return userService.deleteUsers(ids);
		}

		@ResponseStatus(HttpStatus.OK)
		@PutMapping("/{id}")
		@OpenApiEditUser
		public Mono<UserResponse> upsertUser(@PathVariable String id, @Valid @RequestBody UserInfo user) {
			return userService.upsertUser(id, user);
		}

		@ResponseStatus(HttpStatus.OK)
		@PutMapping()
		@OpenApiEditUsers
		public Flux<UserResponse> upsertUsers(@RequestBody @NotEmpty(message = "User info list cannot be empty.")
													  @Valid UserInfoList<ParticularUserInfo> users) {
			return userService.upsertUsers(users);
		}
}