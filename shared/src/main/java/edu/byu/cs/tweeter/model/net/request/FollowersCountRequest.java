package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class FollowersCountRequest extends CountRequest {
    private FollowersCountRequest() {}
    public FollowersCountRequest(AuthToken authToken, String user) {
        super(authToken, user);
    }
}
