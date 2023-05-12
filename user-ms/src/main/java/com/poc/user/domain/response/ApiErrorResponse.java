package com.poc.user.domain.response;

import lombok.Data;

import java.time.Instant;

@Data
public class ApiErrorResponse {
    public Instant timestamp;
    public String path;
    public int status;
    public String error;
    public String requestId;
}
