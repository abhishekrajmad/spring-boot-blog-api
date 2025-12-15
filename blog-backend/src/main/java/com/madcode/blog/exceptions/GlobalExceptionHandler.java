package com.madcode.blog.exceptions;

import com.madcode.blog.domain.dtos.ApiErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(
                IllegalArgumentException ex,
                HttpServletRequest request) {

            return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
        }

        @ExceptionHandler(IllegalStateException.class)
        public ResponseEntity<ApiErrorResponse> handleIllegalStateException(
                IllegalStateException ex,
                HttpServletRequest request) {

            return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), request);
        }

        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<ApiErrorResponse> handleBadCredentialsException(
                BadCredentialsException ex,
                HttpServletRequest request) {

            return buildResponse(
                    HttpStatus.UNAUTHORIZED,
                    "Incorrect username or password",
                    request
            );
        }

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<ApiErrorResponse> handleEntityNotFoundException(
                EntityNotFoundException ex,
                HttpServletRequest request) {

            return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
        }

        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<ApiErrorResponse> handleAccessDenied(
                AccessDeniedException ex,
                HttpServletRequest request) {

            return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage(), request);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiErrorResponse> handleException(
                Exception ex,
                HttpServletRequest request) {

            log.error("Unhandled exception: {}", ex.getMessage());

            return buildResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "An unexpected error occurred",
                    request
            );
        }

        private ResponseEntity<ApiErrorResponse> buildResponse(
                HttpStatus status,
                String message,
                HttpServletRequest request) {

            ApiErrorResponse error = ApiErrorResponse.builder()
                    .timestamp(LocalDateTime.now())
                    .status(status.value())
                    .error(status.name())
                    .message(message)
                    .path(request.getRequestURI())
                    .build();

            return new ResponseEntity<>(error, status);
        }

}
