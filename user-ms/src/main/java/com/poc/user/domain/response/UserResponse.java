package com.poc.user.domain.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

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
	private Instant createdDateTime;
	private Instant lastModifiedDateTime;
}
