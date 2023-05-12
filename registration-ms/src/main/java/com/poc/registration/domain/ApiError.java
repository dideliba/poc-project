package com.poc.registration.domain;

import lombok.Data;
import java.time.Instant;
@Data
public class ApiError{
    public Instant timestamp;
    public String path;
    public int status;
    public String error;
    public String requestId;
}
