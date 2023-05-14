package com.poc.notification.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.notification.domain.User;
import com.poc.notification.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private EmailService emailService;


    @KafkaListener(topics = "${topics.user_account_created.name}")
    public void consumeMessage(String message) throws JsonProcessingException {
        log.info("message consumed {}", message);
        User user = objectMapper.readValue(message, User.class);
        emailService.sendUserEmail(user);
    }
}