package com.fplke.msauthentication.exceptions.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private HttpStatus httpStatus;
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;

    public ApiError(HttpStatus httpStatus, String message, String debugMessage) {
        this.httpStatus = httpStatus;
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.debugMessage = debugMessage;
    }
}
