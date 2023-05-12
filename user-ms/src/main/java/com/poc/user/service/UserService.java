package com.poc.user.service;

import com.poc.user.domain.request.UserInfo;
import com.poc.user.domain.request.ParticularUserInfo;
import com.poc.user.domain.response.UserResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Service used for all game related operations (e.g. creation, move executions etc.)
 * @author didel
 *
 */
public interface UserService {

	public Mono<UserResponse> readUser(String id);

	public Flux<UserResponse> readUsers(List<String> ids);

	/**
	 * if particular user exists updates his info, otherwise creates a new user with id provided
	 */
	public Mono<UserResponse> upsertUser(String id, UserInfo user);


	/**
	 * if particular users exist update their info, otherwise creates new users with ids provided
	 */
	public Flux<UserResponse> upsertUsers(List<ParticularUserInfo> users);

	/**
	 * soft deletes a user with provided id
	 */
	public Mono<UserResponse> deleteUser(String id);

	/**
	 * soft deletes users with provided ids
	 */
	public Flux<UserResponse> deleteUsers(List<String> ids);

	public Mono<UserResponse> createUser(UserInfo user);
}
