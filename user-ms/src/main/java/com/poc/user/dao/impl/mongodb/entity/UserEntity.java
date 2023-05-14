package com.poc.user.dao.impl.mongodb.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;

import lombok.Data;
/**
 * Class that represents a user on mongoDB
 * 
 * @author didel
 *
 */
@Document("user")
@Data
public class UserEntity {
	@Id
	private String id;
	private String username;
	private String email;
	private String firstname;
	private String lastname;
	private boolean active;
	private LocalDateTime createdDateTime;
	private LocalDateTime lastModifiedDateTime;
	private Instant deactivatedTimestamp;
}