package com.lambda.rest.model.response;

import com.lambda.rest.model.Order;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@JsonAutoDetect
public class GetOrdersResponse {
    private final String lastEvaluatedKey;
    private final List<Order> orders;
}
