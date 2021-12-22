package com.lms.tutor.exception;

import java.util.Date;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.lms.tutor.model.ErrorMessage;


@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.NOT_FOUND.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false));
    return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
  }
  
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorMessage> authenticationException(AuthenticationException ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.UNAUTHORIZED.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false));
    return new ResponseEntity<ErrorMessage>(message, HttpStatus.UNAUTHORIZED);
  }
  
  
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorMessage> globalAccessDeniedExceptionHandler(Exception ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.FORBIDDEN.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false));
    
    return new ResponseEntity<ErrorMessage>(message, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false));
    
    return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

