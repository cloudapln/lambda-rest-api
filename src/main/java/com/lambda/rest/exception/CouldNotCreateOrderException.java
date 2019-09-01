package com.lambda.rest.exception;

public class CouldNotCreateOrderException extends IllegalStateException {
    public CouldNotCreateOrderException(String message) {
        super(message);
    }
}
