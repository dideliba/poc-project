package com.poc.user.dao.impl;

import com.poc.user.dao.impl.mongodb.entity.UserEntity;
import com.poc.user.dao.impl.mongodb.repository.UserRepository;
import com.poc.user.dao.UserDAO;
import com.poc.user.domain.request.UserInfo;
import com.poc.user.domain.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of the DAO layer
 * The underlying store is a nosql database (mongoDB)
 * @author didel
 *
 */
@Repository("userDAO")
@Slf4j
public class UserDAOImpl implements UserDAO {

    @Autowired
    private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Mono<UserResponse> get(String id){
		return userRepository.findByIdAndActiveTrue(id).map(u->modelMapper.map(u, UserResponse.class));
	}

	@Override
	public Flux<UserResponse> getAll(List<String> ids){
		return userRepository.findAllByIdInAndActiveTrue(ids).map(u->modelMapper.map(u, UserResponse.class));
	}

	@Override
	public Mono<UserResponse> save(UserInfo user) {
		UserEntity userEntity = modelMapper.map(user, UserEntity.class);
		userEntity.setCreatedDateTime(LocalDateTime.now());
		userEntity.setActive(true);
		return userRepository.save(userEntity)
				.flatMap(savedEntity -> Mono.just(modelMapper.map(savedEntity, UserResponse.class)));
	}

	@Override
	public Mono<UserResponse> saveWithId(String id, UserInfo user) {
		return userRepository.findByIdAndActiveTrue(id)
				.switchIfEmpty(Mono.defer(() -> {
					UserEntity userEntity=modelMapper.map(user,UserEntity.class);
					userEntity.setId(id);
					userEntity.setCreatedDateTime(LocalDateTime.now());
					userEntity.setActive(true);
					return userRepository.save(userEntity);
				}))
				.flatMap(entity -> {
					BeanUtils.copyProperties(user, entity);
					entity.setLastModifiedDateTime(LocalDateTime.now());
					return userRepository.save(entity);
				})
				.map(savedEntity -> modelMapper.map(savedEntity, UserResponse.class));
	}


	@Override
	public Mono<UserResponse> delete(String id) {
		return userRepository.findByIdAndActiveTrue(id)
				.switchIfEmpty(Mono.empty())
				.flatMap(entity -> {
					entity.setActive(false);
					entity.setDeactivatedTimestamp(Instant.now());
					return userRepository.save(entity);
				})
				.map(savedEntity -> modelMapper.map(savedEntity, UserResponse.class));
	}

	@Override
	public Flux<UserResponse> deleteAll(List<String> ids) {
		return userRepository.findAllByIdInAndActiveTrue(ids)
				.switchIfEmpty(Flux.empty())
				.collectList()
				.flatMapMany(entities -> {
					entities.forEach(entity -> {
						entity.setActive(false);
						entity.setDeactivatedTimestamp(Instant.now());
					});
					return userRepository.saveAll(entities)
							.doOnNext(entity -> log.info("Entity with id: {} deleted successfully", entity.getId()));
				})
				.map(entity -> modelMapper.map(entity, UserResponse.class));
	}
}