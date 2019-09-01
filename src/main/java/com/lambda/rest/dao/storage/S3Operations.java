package com.lambda.rest.dao.storage;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.nio.charset.StandardCharsets;

public class S3Operations {
    public static final String PATH_DELIM = "/";
    private final S3Client s3Client;
    private final String bucketName;

    public S3Operations(S3Client s3Client, String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    public String getObjectFromBucket(final String resourcePath, final String fileName) {
        String bucketName = resourcePath.indexOf(PATH_DELIM) > -1 ? resourcePath.substring(0, resourcePath.indexOf(PATH_DELIM)) : resourcePath;

        return s3Client.getObjectAsBytes(GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build()).asString(StandardCharsets.UTF_8);
    }


}
