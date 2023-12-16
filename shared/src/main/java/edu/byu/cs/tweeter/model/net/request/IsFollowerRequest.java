package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class IsFollowerRequest extends AuthenticatedRequest{
    private String follower;
    private String followee;

    private IsFollowerRequest() {}
    public IsFollowerRequest(AuthToken authToken, String follower, String followee) {
        super(authToken);
        this.follower = follower;
        this.followee = followee;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getFollowee() {
        return followee;
    }

    public void setFollowee(String followee) {
        this.followee = followee;
    }
}
