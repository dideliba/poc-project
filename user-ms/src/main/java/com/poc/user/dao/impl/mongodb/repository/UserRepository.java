package com.poc.user.dao.impl.mongodb.repository;

import com.poc.user.dao.impl.mongodb.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Spring data JPA repository for mongoDB
 * 
 * @author didel
 *
 */
/**
 * {@inheritDoc}
 */
@Repository
public interface UserRepository extends ReactiveCrudRepository<UserEntity, String> {
    Mono<UserEntity> findByIdAndActiveTrue(String id);
    Flux<UserEntity> findAllByIdInAndActiveTrue(List<String> ids);
}
