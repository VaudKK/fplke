package com.fplke.msauthentication.exceptions.handler;

import com.fplke.msauthentication.exceptions.model.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = { BadCredentialsException.class })
    protected ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        var apiError = new ApiError(status,"Invalid credentials",ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError,apiError.getHttpStatus());
    }

}
