package com.poc.registration.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

/**
 * Class that represents a user resource
 *
 * @author didel
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private String id;
	private String username;
	private String email;
	private String firstname;
	private String lastname;
	private boolean active;

	@JsonProperty(access = READ_ONLY)
	private LocalDateTime createdDateTime;
	@JsonProperty(access = READ_ONLY)
	private LocalDateTime updatedDateTime;
}
