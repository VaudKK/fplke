package com.fplke.msauthentication.exceptions.handler;

import com.fplke.msauthentication.client.dto.response.FplEntryResponse;
import com.fplke.msauthentication.exceptions.EmailExistsException;
import com.fplke.msauthentication.exceptions.TeamIdException;
import com.fplke.msauthentication.exceptions.TeamIdExistsException;
import com.fplke.msauthentication.exceptions.TeamIdNotFoundException;
import com.fplke.msauthentication.exceptions.model.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = { BadCredentialsException.class })
    protected ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        var apiError = new ApiError(status,"Invalid credentials",ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(value = { EmailExistsException.class })
    protected ResponseEntity<Object> handleEmailExistsException(EmailExistsException ex) {
        var apiError = new ApiError(HttpStatus.BAD_REQUEST,"User already exists",ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(value = { TeamIdExistsException.class })
    protected ResponseEntity<Object> handleTeamIdExistsException(TeamIdExistsException ex) {
        var apiError = new ApiError(HttpStatus.BAD_REQUEST,"Team Id already exists",ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(value = { TeamIdNotFoundException.class })
    protected ResponseEntity<Object> handleTeamIdNotFoundException(TeamIdNotFoundException ex) {
        var apiError = new ApiError(HttpStatus.BAD_REQUEST,"Invalid team Id",ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(value = { TeamIdException.class })
    protected ResponseEntity<Object> handleTeamIdException(TeamIdException ex) {
        var apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,"Could not verify team Id",ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError,apiError.getHttpStatus());
    }

}
