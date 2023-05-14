package com.poc.registration.service;

import com.poc.registration.domain.UserInfo;
import com.poc.registration.domain.UserResponse;
import reactor.core.publisher.Mono;

/**
 * Service used for all registration related operations
 * @author didel
 *
 */
public interface RegistrationService {
	public Mono<UserResponse> registerUser(UserInfo user);
}
