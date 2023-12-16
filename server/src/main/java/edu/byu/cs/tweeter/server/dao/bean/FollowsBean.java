package edu.byu.cs.tweeter.server.dao.bean;

import edu.byu.cs.tweeter.server.dao.DynamoFollowsDao;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class FollowsBean {
    private String followerHandle;
    private String followeeHandle;
    private String followerName;
    private String followeeName;

    public FollowsBean(String followerHandle, String followeeHandle, String followerName, String followeeName) {
        this.followerHandle = followerHandle;
        this.followeeHandle = followeeHandle;
        this.followerName = followerName;
        this.followeeName = followeeName;
    }
    public FollowsBean(String followerHandle, String followeeHandle) {
        this.followerHandle = followerHandle;
        this.followeeHandle = followeeHandle;
    }

    public FollowsBean() {}

    @DynamoDbPartitionKey
    @DynamoDbSecondarySortKey(indexNames = {DynamoFollowsDao.INDEX})
    @DynamoDbAttribute("follow_handle")
    public String getFollowerHandle() {
        return followerHandle;
    }

    public void setFollowerHandle(String followerHandle) {
        this.followerHandle = followerHandle;
    }

    @DynamoDbSortKey
    @DynamoDbSecondaryPartitionKey(indexNames = {DynamoFollowsDao.INDEX})
    @DynamoDbAttribute("followee_handle")
    public String getFolloweeHandle() {
        return followeeHandle;
    }

    public void setFolloweeHandle(String followeeHandle) {
        this.followeeHandle = followeeHandle;
    }

    @DynamoDbAttribute("follower_name")
    public String getFollowerName() {
        return followerName;
    }

    public void setFollowerName(String followerName) {
        this.followerName = followerName;
    }

    @DynamoDbAttribute("followee_name")
    public String getFolloweeName() {
        return followeeName;
    }

    public void setFolloweeName(String followeeName) {
        this.followeeName = followeeName;
    }
}

