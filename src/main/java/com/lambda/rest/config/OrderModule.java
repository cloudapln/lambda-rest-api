

package com.lambda.rest.config;

import com.lambda.rest.dao.OrderDao;
import com.lambda.rest.dao.S3RepositoryImpl;
import com.lambda.rest.dao.storage.S3Operations;
import com.lambda.rest.dao.S3RepositoryDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Module;
import dagger.Provides;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;
import software.amazon.awssdk.services.s3.S3Client;

import javax.inject.Named;
import javax.inject.Singleton;
import java.net.URI;
import java.util.Optional;

@Module
public class OrderModule {
    @Singleton
    @Provides
    @Named("tableName")
    String tableName() {
        return Optional.ofNullable(System.getenv("TABLE_NAME")).orElse("order_table");
    }

    @Singleton
    @Provides
    @Named("bucketName")
    String bucketName() {
        return Optional.ofNullable(System.getenv("BUCKET_NAME")).orElse("lambda-rest-api-order-bucket");
    }

    @Singleton
    @Provides
    DynamoDbClient dynamoDb() {
        final String endpoint = System.getenv("ENDPOINT_OVERRIDE");

        DynamoDbClientBuilder builder = DynamoDbClient.builder();
        builder.httpClient(ApacheHttpClient.builder().build());
        if (endpoint != null && !endpoint.isEmpty()) {
            builder.endpointOverride(URI.create(endpoint));
        }

        return builder.build();
    }

    @Singleton
    @Provides
    public S3Operations s3Operations(@Named("bucketName") String bucketName) {
        S3Client s3Client = S3Client.builder()
                .httpClient(ApacheHttpClient.builder().build())
                .region(Region.EU_WEST_2)
                .build();
        return new S3Operations(s3Client, bucketName);
    }

    @Singleton
    @Provides
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Singleton
    @Provides
    public OrderDao orderDao(DynamoDbClient dynamoDb, @Named("tableName") String tableName) {
        return new OrderDao(dynamoDb, tableName, 10);
    }

    @Singleton
    @Provides
    public S3RepositoryDao s3RepositoryDao(S3Operations s3BucketOperations) {
        return new S3RepositoryImpl(s3BucketOperations);
    }
}
