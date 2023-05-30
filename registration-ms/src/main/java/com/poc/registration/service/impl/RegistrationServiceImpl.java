/**
 * 
 */
package com.poc.registration.service.impl;

import com.poc.registration.domain.ApiErrorResponse;
import com.poc.registration.domain.UserInfo;
import com.poc.registration.domain.UserResponse;
import com.poc.registration.exception.RegistrationException;
import com.poc.registration.kafka.producer.Producer;
import com.poc.registration.service.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Implementation of {@link RegistrationService RegistrationService.class}
 * @author didel
 *
 */
@Slf4j
@Service("registrationService")
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private Producer producer;

    @Value("${external.service.user.url}")
    private String userServiceUrl;


    WebClient userWebclient = WebClient.create();

    @Override
    public Mono<UserResponse> registerUser(UserInfo user) {
        return  userWebclient.post()
                .uri(userServiceUrl+"/v1/users")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(user), UserInfo.class)
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.CREATED)) {
                        try {
                            log.info("User {} registered with info {}", user,response.bodyToMono(UserResponse.class));
                            producer.sendMessage(user);
                            return response.bodyToMono(UserResponse.class);
                        } catch (Exception e) {
                            /* disclaimer: if something went wrong when trying to sent event to kafka a solution that
                            could be adopted on a production system will be to persist the event body into a db table
                            e.g. failed_notifications(body,number_of_retries,status) which will be read by a scheduled
                            job (running for example every 5 minutes) and to retry sending notification event to kafka
                            so that an email can eventually be sent
                             */
                            log.error("Exception occur after successful registration: ", e);
                            return response.bodyToMono(UserResponse.class); //we consider the registration successful though
                        }
                    }  else {
                        log.error("User service error status: {} , body: {}", response.statusCode(),
                                response.bodyToMono(ApiErrorResponse.class));
                        throw new RegistrationException(response.bodyToMono(ApiErrorResponse.class),response.statusCode());
                    }
                });
    }
}