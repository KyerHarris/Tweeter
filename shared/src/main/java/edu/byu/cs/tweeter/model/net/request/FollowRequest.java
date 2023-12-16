package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class FollowRequest extends AuthenticatedRequest {
    private String followee;
    protected FollowRequest() {}
    public FollowRequest(AuthToken authToken, String followee) {
        super(authToken);
        this.followee = followee;
    }

    public String getFollowee() {
        return followee;
    }

    public void setFollowee(String followee) {
        this.followee = followee;
    }
}
