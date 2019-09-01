package com.lambda.rest.handler;

import com.amazonaws.config.DaggerOrderTestComponent;
import com.lambda.rest.config.OrderTestComponent;
import org.junit.After;
import org.junit.Before;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;

import javax.inject.Inject;

/**
 * This class serves as the base class for Integration tests. do not include I T in
 * the class name so that it does not get picked up by failsafe.
 */
public abstract class OrderHandlerTestBase {
    private static final String TABLE_NAME = "order_table";

    private final OrderTestComponent orderComponent;

    @Inject
    DynamoDbClient dynamoDb;

    public OrderHandlerTestBase() {
        orderComponent = DaggerOrderTestComponent.builder().build();
        orderComponent.inject(this);
    }

    @Before
    public void setup() {
        dynamoDb.createTable(CreateTableRequest.builder()
                .tableName(TABLE_NAME)
                .keySchema(KeySchemaElement.builder()
                        .keyType(KeyType.HASH)
                        .attributeName("orderId")
                        .build())
                .attributeDefinitions(
                        AttributeDefinition.builder()
                                .attributeName("orderId")
                                .attributeType(ScalarAttributeType.S)
                                .build())
                .provisionedThroughput(
                        ProvisionedThroughput.builder()
                                .readCapacityUnits(1L)
                                .writeCapacityUnits(1L)
                                .build())
                .build());

    }

    @After
    public void teardown() {
        dynamoDb.deleteTable(DeleteTableRequest.builder().tableName(TABLE_NAME).build());
    }
}
