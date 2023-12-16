package edu.byu.cs.tweeter.server.dao.bean;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

@DynamoDbBean
public class AuthTokenBean {
    private String token;
    private String alias;
    private long timeStamp;

    public AuthTokenBean() {
    }

    // Constructor for all fields
    public AuthTokenBean(String token, String alias, long timeStamp) {
        this.token = token;
        this.alias = alias;
        this.timeStamp = timeStamp;
    }

    // Getters and setters
    @DynamoDbPartitionKey
    @DynamoDbAttribute("Token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = {"Alias-index"})
    @DynamoDbAttribute("Alias")
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @DynamoDbAttribute("TimeStamp")
    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
