package com.lambda.rest.handler;

import com.lambda.rest.services.lambda.runtime.TestContext;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class UpdateOrderHandlerTest {
    private UpdateOrderHandler sut = new UpdateOrderHandler();

    @Test
    public void handleRequest_whenUpdateOrderInputStreamEmpty_puts400InOutputStream() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        sut.handleRequest(new ByteArrayInputStream(new byte[0]), os, TestContext.builder().build());
        assertTrue(os.toString().contains("Invalid JSON"));
        assertTrue(os.toString().contains("400"));
    }

    @Test
    public void handleRequest_whenUpdateOrderInputStreamHasNoMappedOrderIdPathParam_puts400InOutputStream() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String input = "{\"pathParameters\": { }}";
        sut.handleRequest(new ByteArrayInputStream(input.getBytes()), os, TestContext.builder().build());
        assertTrue(os.toString().contains("order_id was not set"));
        assertTrue(os.toString().contains("400"));
    }

    @Test
    public void handleRequest_whenUpdateOrderInputStreamHasNoBody_puts400InOutputStream() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String input = "{\"pathParameters\": { \"order_id\" : \"a\" }}";
        sut.handleRequest(new ByteArrayInputStream(input.getBytes()), os, TestContext.builder().build());
        assertTrue(os.toString().contains("Body was null"));
        assertTrue(os.toString().contains("400"));
    }

    @Test
    public void handleRequest_whenUpdateOrderInputStreamHasNullBody_puts400InOutputStream() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String input = "{\"pathParameters\": { \"order_id\" : \"a\" }, \"body\": \"null\"}";
        sut.handleRequest(new ByteArrayInputStream(input.getBytes()), os, TestContext.builder().build());
        assertTrue(os.toString().contains("Request was null"));
        assertTrue(os.toString().contains("400"));
    }

    @Test
    public void handleRequest_whenUpdateOrderInputStreamHasWrongTypeForBody_puts400InOutputStream() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String input = "{\"pathParameters\": { \"order_id\" : \"a\" }, \"body\": \"1\"}";
        sut.handleRequest(new ByteArrayInputStream(input.getBytes()), os, TestContext.builder().build());
        assertTrue(os.toString().contains("Invalid JSON"));
        assertTrue(os.toString().contains("400"));
    }

    @Test
    public void handleRequest_whenUpdateOrderInputStreamHasEmptyBodyDict_puts400InOutputStream() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String input = "{\"pathParameters\": { \"order_id\" : \"a\" }, \"body\": \"{}\"}";
        sut.handleRequest(new ByteArrayInputStream(input.getBytes()), os, TestContext.builder().build());
        assertTrue(os.toString().contains("customerId was null"));
        assertTrue(os.toString().contains("400"));
    }

    @Test
    public void handleRequest_whenUpdateOrderInputStreamOnlyHasCustomer_puts400InOutputStream() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String input = "{\"pathParameters\": { \"order_id\" : \"a\" }, \"body\": \"{\\\"customerId\\\": \\\"customer\\\"}\"}";
        sut.handleRequest(new ByteArrayInputStream(input.getBytes()), os, TestContext.builder().build());
        assertTrue(os.toString().contains("preTaxAmount was null"));
        assertTrue(os.toString().contains("400"));
    }

    @Test
    public void handleRequest_whenUpdateOrderInputStreamDoesNotHavePostTaxAmount_puts400InOutputStream() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String input = "{\"pathParameters\": { \"order_id\" : \"a\" }, \"body\": \"{\\\"customerId\\\": \\\"customer\\\", \\\"preTaxAmount\\\": 1}\"}";
        sut.handleRequest(new ByteArrayInputStream(input.getBytes()), os, TestContext.builder().build());
        assertTrue(os.toString().contains("postTaxAmount was null"));
        assertTrue(os.toString().contains("400"));
    }
}
