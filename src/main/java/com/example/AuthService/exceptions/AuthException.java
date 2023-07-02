package com.example.AuthService.exceptions;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Getter
@Slf4j
public class AuthException extends RuntimeException {
    private final HttpStatus status;

    public AuthException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        log.info("Message: {}\nStatus: {}", message, status.name());
    }

    public AuthException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }
}
