/**
 * 
 */
package com.poc.user.service.impl;


import com.poc.user.dao.UserDAO;
import com.poc.user.domain.request.ParticularUserInfo;
import com.poc.user.domain.request.UserInfo;
import com.poc.user.domain.response.UserResponse;
import com.poc.user.exception.UserNotFoundException;
import com.poc.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link UserService UserService.class}
 * @author didel
 *
 */
@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public Mono<UserResponse> readUser(String id) {
        return userDAO.get(id).switchIfEmpty(Mono.error(new UserNotFoundException("Could not find specific user")));
    }

    @Override
    public Flux<UserResponse> readUsers(List<String> ids){
       /* List<UserResponse> employeeList = Arrays.asList(
                new UserResponse("1","1","1","1","1",null,null),
                new UserResponse("2","1","1","1","1",null,null),
                new UserResponse("3","1","1","1","1",null,null)
        );
        Flux<UserResponse> employeeFlux = Flux.fromIterable(employeeList).delayElements(Duration.ofSeconds(3));*/
        return userDAO.getAll(ids);
    }


    @Override
    public Mono<UserResponse> createUser(UserInfo user) {
        return userDAO.save(user);
    }

    public Mono<UserResponse> upsertUser(String id, UserInfo user){
        //if resource with specified id does not exist, a new resource will be created
        return userDAO.saveWithId(id, user);
    }

    public Flux<UserResponse> upsertUsers(List<ParticularUserInfo> users) {
        return Flux.fromIterable(users)
                .filter(Objects::nonNull)
                .flatMap(u -> userDAO.saveWithId(u.getId(), (UserInfo) u));
    }

    public Mono<UserResponse> deleteUser(String id){
        return userDAO.delete(id).
                switchIfEmpty(Mono.error(new UserNotFoundException("Could not find user to deleted")));
    }

    public Flux<UserResponse> deleteUsers(List<String> ids){
        return userDAO.deleteAll(ids).
                switchIfEmpty(Mono.error(new UserNotFoundException("Could not find user to delete")));
    }
}