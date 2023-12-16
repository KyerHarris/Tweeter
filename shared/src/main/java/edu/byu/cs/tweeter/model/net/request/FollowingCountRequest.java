package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class FollowingCountRequest extends CountRequest {
    private FollowingCountRequest() {}
    public FollowingCountRequest(AuthToken authToken, String user) {
        super(authToken, user);
    }
}
