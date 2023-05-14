/**
 * 
 */
package com.poc.user.exception;

/**
 * Exception thrown when requested user does not exist
 * @author didel
 *
 */
public class UserNotFoundException extends InvalidUserException {
	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
	}
	public UserNotFoundException(String message) {
		super(message);
	}

}
