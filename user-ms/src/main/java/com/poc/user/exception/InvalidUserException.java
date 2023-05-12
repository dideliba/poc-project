/**
 * 
 */
package com.poc.user.exception;

/**
 * Generic Registration exception
 * @author didel
 *
 */
public class InvalidUserException extends RuntimeException {


	private static final long serialVersionUID = 1L;

	public InvalidUserException() {
	}
	
	public InvalidUserException(String message) {
		super(message);
	}

}
