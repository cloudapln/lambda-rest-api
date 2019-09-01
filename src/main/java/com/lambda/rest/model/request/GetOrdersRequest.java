package com.lambda.rest.model.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonAutoDetect
public class GetOrdersRequest {
    private String exclusiveStartKey;
}
