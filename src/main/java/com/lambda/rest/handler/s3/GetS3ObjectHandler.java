/*
 * Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.lambda.rest.handler.s3;

import com.lambda.rest.config.DaggerOrderComponent;
import com.lambda.rest.config.OrderComponent;
import com.lambda.rest.dao.S3RepositoryDao;
import com.lambda.rest.exception.OrderDoesNotExistException;
import com.lambda.rest.handler.OrderRequestStreamHandler;
import com.lambda.rest.model.response.ErrorMessage;
import com.lambda.rest.model.response.GatewayResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

public class GetS3ObjectHandler implements OrderRequestStreamHandler {
    @Inject
    ObjectMapper objectMapper;
    @Inject
    S3RepositoryDao s3RepositoryDao;
    private final OrderComponent orderComponent;

    public GetS3ObjectHandler() {
        orderComponent = DaggerOrderComponent.builder().build();
        orderComponent.inject(this);
    }

    @Override
    public void handleRequest(InputStream input, OutputStream output,
                              Context context) throws IOException {
        final JsonNode event;
        try {
            event = objectMapper.readTree(input);
        } catch (JsonMappingException e) {
            writeInvalidJsonInStreamResponse(objectMapper, output, e.getMessage());
            return;
        }
        if (event == null) {
            writeInvalidJsonInStreamResponse(objectMapper, output, "event was null");
            return;
        }
        final JsonNode pathParameterMap = event.findValue("pathParameters");
        final String resourceName = Optional.ofNullable(pathParameterMap)
                .map(mapNode -> mapNode.get("resource_name"))
                .map(JsonNode::asText)
                .orElse(null);
        if (isNullOrEmpty(resourceName)) {
            objectMapper.writeValue(output,
                    new GatewayResponse<>(
                            objectMapper.writeValueAsString(ORDER_ID_WAS_NOT_SET),
                            APPLICATION_JSON, SC_BAD_REQUEST));
            return;
        }
        try {
            String s3Content = s3RepositoryDao.loadContent(resourceName);
            objectMapper.writeValue(output,
                    new GatewayResponse<>(
                            objectMapper.writeValueAsString(s3Content),
                            APPLICATION_JSON, SC_OK));
        } catch (OrderDoesNotExistException e) {
            objectMapper.writeValue(output,
                    new GatewayResponse<>(
                            objectMapper.writeValueAsString(
                                    new ErrorMessage(e.getMessage(), SC_NOT_FOUND)),
                            APPLICATION_JSON, SC_NOT_FOUND));
        }
    }
}
