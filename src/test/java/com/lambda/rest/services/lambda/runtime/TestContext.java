package com.lambda.rest.services.lambda.runtime;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TestContext implements Context {
    private final String awsRequestId;
    private final String logGroupName;
    private final String logStreamName;
    private final String functionName;
    private final String functionVersion;
    private final String invokedFunctionArn;
    private final CognitoIdentity identity;
    private final ClientContext clientContext;
    private final int remainingTimeInMillis;
    private final int memoryLimitInMB;
    private final LambdaLogger logger;
}
