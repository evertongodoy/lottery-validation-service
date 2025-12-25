package com.lottery.validation.domain.exceptions;

public class UserNotFoundException extends BusinessException {
    
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(String subject) {
        super("User not found with subject: " + subject);
    }
}
