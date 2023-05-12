package com.poc.user.dao;

import com.poc.user.domain.request.ParticularUserInfo;
import com.poc.user.domain.request.UserInfo;
import com.poc.user.domain.response.UserResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Interface for performing CRUD operations on user repositories
 * @author didel
 *
 */
public interface UserDAO {

	public Mono<UserResponse> get(String id);

	public Flux<UserResponse> getAll(List<String> ids);

	public Mono<UserResponse> save(UserInfo user);

	public Mono<UserResponse> saveWithId(String id, UserInfo user);

	public Mono<UserResponse> delete(String id);

	public Flux<UserResponse> deleteAll(List<String> ids);
}
