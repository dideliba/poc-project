/**
 * 
 */
package com.poc.registration.exception;

import com.poc.registration.domain.ApiError;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

/**
 * Generic Registration exception
 * @author didel
 *
 */

@Data
@AllArgsConstructor
public class RegistrationException extends RuntimeException {

	Mono<ApiError> apiExceptionMono;

	HttpStatus httpStatus;

}
