package com.lambda.rest.exception;

public class UnableToDeleteException extends IllegalStateException {

    public UnableToDeleteException(String message) {
        super(message);
    }
}
