package com.poc.notification.service;

import com.poc.notification.domain.User;

/**
 * Service used for all email interactions
 * @author didel
 *
 */
public interface EmailService {

	public void sendUserEmail(User user);
}
