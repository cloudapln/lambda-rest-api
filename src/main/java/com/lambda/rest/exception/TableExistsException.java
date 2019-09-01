package com.lambda.rest.exception;

public class TableExistsException extends IllegalStateException {
    public TableExistsException(String message) {
        super(message);
    }
}
