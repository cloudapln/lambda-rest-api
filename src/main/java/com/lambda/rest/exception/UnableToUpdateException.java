package com.lambda.rest.exception;

public class UnableToUpdateException extends IllegalStateException {
    public UnableToUpdateException(String message) {
        super(message);
    }
}
