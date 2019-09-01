package com.lambda.rest.model.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * POJO containing response object for API Gateway.
 */
@Getter
@JsonAutoDetect
public class GatewayResponse<T> {

    private final T body;
    private final Map<String, String> headers;
    private final int statusCode;

    /**
     * Creates a GatewayResponse object.
     * @param body body of the response
     * @param headers headers of the response
     * @param statusCode status code of the response
     */
    public GatewayResponse(final T body, final Map<String, String> headers, final int statusCode) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = Collections.unmodifiableMap(new HashMap<>(headers));
    }
}
