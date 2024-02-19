package com.kuroko.heathyapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.kuroko.heathyapi.exception.business.ResourceAlreadyExistsException;
import com.kuroko.heathyapi.exception.business.ResourceNotFoundException;
import com.kuroko.heathyapi.exception.business.ValidationFailedException;
import com.kuroko.heathyapi.exception.integration.FileStorageException;
import com.kuroko.heathyapi.exception.integration.IntegrationException;
import com.kuroko.heathyapi.exception.security.AccessDeniedException;
import com.kuroko.heathyapi.exception.security.AuthenticationException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e,
            WebRequest request) {
        return ResponseEntity.status(404).body(new ErrorResponse(e.getMessage() + " " + e.getClass().getName()));
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExistsException(ResourceAlreadyExistsException e,
            WebRequest request) {
        return ResponseEntity.status(409).body(new ErrorResponse(e.getMessage() + " " + e.getClass().getName()));
    }

    @ExceptionHandler(ValidationFailedException.class)
    public ResponseEntity<ErrorResponse> handleValidationFailedException(ValidationFailedException e,
            WebRequest request) {
        return ResponseEntity.status(400).body(new ErrorResponse(e.getMessage() + " " + e.getClass().getName()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e,
            WebRequest request) {
        return ResponseEntity.status(403).body(new ErrorResponse(e.getMessage() + " " + e.getClass().getName()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e,
            WebRequest request) {
        return ResponseEntity.status(401).body(new ErrorResponse(e.getMessage() + " " + e.getClass().getName()));
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ErrorResponse> handleFileStorageException(FileStorageException e,
            WebRequest request) {
        return ResponseEntity.status(500).body(new ErrorResponse(e.getMessage() + " " + e.getClass().getName()));
    }

    @ExceptionHandler(IntegrationException.class)
    public ResponseEntity<ErrorResponse> handleIntegrationException(IntegrationException e,
            WebRequest request) {
        return ResponseEntity.status(500).body(new ErrorResponse(e.getMessage() + " " + e.getClass().getName()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity.status(500).body(new ErrorResponse(e.getMessage() + " " + e.getClass().getName()));
    }
}
