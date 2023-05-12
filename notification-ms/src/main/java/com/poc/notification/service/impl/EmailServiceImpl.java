/**
 * 
 */
package com.poc.notification.service.impl;

import com.poc.notification.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.text.StrSubstitutor;
import java.util.HashMap;
import java.util.Map;

import com.poc.notification.service.EmailService;

/**
 * Implementation of {@link EmailService EmailService.class}
 * @author didel
 *
 */
@Slf4j
@Service("emailService")
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendUserEmail(User user) {

        String subject = "Successfull Registration to POC platform!";
        String body = "Welcome abroard ${firstName} ${lastName}!\n\n"
                + "You have successfully registered to! Have fun\n\n"
                + "Best regards,\n"
                + "The POC Team";

        Map<String, Object> params = new HashMap<>();
        params.put("firstName", user.getFirstname());
        params.put("lastName", user.getLastname());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject(subject);
        message.setText(StrSubstitutor.replace(body, params, "${", "}"));

        mailSender.send(message);
    }
}