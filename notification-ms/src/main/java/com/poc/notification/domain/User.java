package com.poc.notification.domain;

import lombok.Data;

/**
 * Class that represents a user resource
 * 
 * @author didel
 *
 */
@Data
public class User {
	private String username;
	private String email;
	private String firstname;
	private String lastname;
}
