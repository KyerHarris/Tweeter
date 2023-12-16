package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public abstract class CountRequest extends AuthenticatedRequest {
    private String user;

    protected CountRequest() {}

    public CountRequest(AuthToken authToken, String user) {
        super(authToken);
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
