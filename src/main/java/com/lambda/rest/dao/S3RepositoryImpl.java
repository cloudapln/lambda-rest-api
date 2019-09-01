package com.lambda.rest.dao;

import com.lambda.rest.dao.storage.S3Operations;

public class S3RepositoryImpl implements  S3RepositoryDao{

    private final S3Operations s3BucketOperations;

    public S3RepositoryImpl(S3Operations s3BucketOperations) {
        this.s3BucketOperations = s3BucketOperations;
    }

    @Override
    public String loadContent(String resourceName) {
       return s3BucketOperations.getObjectFromBucket("/", resourceName);
    }
}
