package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class UnfollowRequest extends FollowRequest {
    private UnfollowRequest() {}
    public UnfollowRequest(AuthToken authToken, String followee) {
        super(authToken, followee);
    }
}
