package edu.byu.cs.tweeter.server.dao.bean;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class StatusBean {
    public String post;
    public String user;
    public Long timestamp;
    public List<String> urls;
    public List<String> mentions;

    public StatusBean() {
    }

    public StatusBean(String post, String user, Long timestamp, List<String> urls, List<String> mentions) {
        this.post = post;
        this.user = user;
        this.timestamp = timestamp;
        this.urls = urls;
        this.mentions = mentions;
    }

    @DynamoDbAttribute("Post")
    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
    @DynamoDbPartitionKey
    @DynamoDbAttribute("User")
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
