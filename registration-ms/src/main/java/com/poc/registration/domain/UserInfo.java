package com.poc.registration.domain;


import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Class that represents a user request body for create user operation.
 *
 * @author didel
 */
@Data
public class UserInfo {
	@NotBlank
	private String username;
	@NotBlank
	private String email;
	private String firstname;
	private String lastname;
}
