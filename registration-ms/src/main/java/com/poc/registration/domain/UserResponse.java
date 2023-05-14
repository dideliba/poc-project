package com.poc.registration.domain;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * Class that represents a user resource
 *
 * @author didel
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	private String id;
	private String username;
	private String email;
	private String firstname;
	private String lastname;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private Instant createdDateTime;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Instant lastModifiedDateTime;
}
