package com.lambda.rest.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class OrderPage {
    private final List<Order> orders;
    private final String lastEvaluatedKey;
}
