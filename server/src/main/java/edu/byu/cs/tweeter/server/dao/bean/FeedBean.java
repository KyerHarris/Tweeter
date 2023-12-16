package edu.byu.cs.tweeter.server.dao.bean;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class FeedBean {
    public String feedOwner;
    public String post;
    public String personWhoPosted;
    public Long timestamp;
    public List<String> urls;
    public List<String> mentions;

    public FeedBean(String post, String personWhoPosted, Long timestamp, List<String> urls, List<String> mentions, String feedOwner) {
        this.feedOwner = feedOwner;
        this.post = post;
        this.personWhoPosted = personWhoPosted;
        this.timestamp = timestamp;
        this.urls = urls;
        this.mentions = mentions;
    }

    public FeedBean() {
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("FeedOwner")
    public String getFeedOwner() {
        return feedOwner;
    }

    public void setFeedOwner(String user) {
        this.feedOwner = user;
    }

    @DynamoDbAttribute("Post")
    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
    @DynamoDbAttribute("PersonWhoPosted")
    public String getPersonWhoPosted() {
        return personWhoPosted;
    }

    public void setPersonWhoPosted(String user) {
        this.personWhoPosted = user;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("Timestamp")
    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @DynamoDbAttribute("Urls")
    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    @DynamoDbAttribute("Mentions")
    public List<String> getMentions() {
        return mentions;
    }

    public void setMentions(List<String> mentions) {
        this.mentions = mentions;
    }
}
