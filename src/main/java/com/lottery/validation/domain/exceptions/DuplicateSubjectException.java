package com.lottery.validation.domain.exceptions;

public class DuplicateSubjectException extends BusinessException {
    
    public DuplicateSubjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateSubjectException(String subject) {
        super("User already exists with subject: " + subject);
    }
}
