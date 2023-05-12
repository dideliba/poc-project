package com.poc.user.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes{

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> map = super.getErrorAttributes(request, options);

        if (getError(request) instanceof UserNotFoundException) {
            map.put("status", HttpStatus.NOT_FOUND.value());
            map.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        } else if (getError(request) instanceof WebExchangeBindException){
            map.put("status", HttpStatus.BAD_REQUEST.value());
            map.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
        map.put("message", getError(request).getMessage());
        return map;
    }
}