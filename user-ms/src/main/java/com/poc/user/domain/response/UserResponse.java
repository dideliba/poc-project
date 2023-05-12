package com.poc.user.domain.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

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
public class UserResponse {
	private String id;
	private String username;
	private String email;
	private String firstname;
	private String lastname;
	private LocalDateTime createdDateTime;
	private LocalDateTime lastModifiedDateTime;
}
