package com.poc.registration.service;

import com.poc.registration.domain.User;
import reactor.core.publisher.Mono;

/**
 * Service used for all registration related operations
 * @author didel
 *
 */
public interface RegistrationService {

	public Mono<User> registerUser(User user);
}
