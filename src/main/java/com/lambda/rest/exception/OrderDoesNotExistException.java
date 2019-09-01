package com.lambda.rest.exception;

public class OrderDoesNotExistException extends IllegalArgumentException {

    public OrderDoesNotExistException(String message) {
        super(message);
    }
}
