package com.lambda.rest.model.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect
public class UpdateOrderRequest {
    private String orderId;
    private String customerId;
    private BigDecimal preTaxAmount;
    private BigDecimal postTaxAmount;
    private Long version;
}
