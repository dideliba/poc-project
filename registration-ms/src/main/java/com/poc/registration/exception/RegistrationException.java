/**
 * 
 */
package com.poc.registration.exception;

import com.poc.registration.domain.ApiErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

/**
 * Generic Registration exception
 * @author didel
 *
 */

@Data
@AllArgsConstructor
public class RegistrationException extends RuntimeException {
	Mono<ApiErrorResponse> apiExceptionMono;
	HttpStatus httpStatus;
}