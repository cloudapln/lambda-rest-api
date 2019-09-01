package com.lambda.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private String orderId;
    private String customerId;
    private BigDecimal preTaxAmount;
    private BigDecimal postTaxAmount;
    private Long version;
}
