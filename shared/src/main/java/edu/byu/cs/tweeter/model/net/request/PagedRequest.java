package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public abstract class PagedRequest extends AuthenticatedRequest {
    private int limit;
    protected PagedRequest() {}
    public PagedRequest(AuthToken authToken, int limit) {
        super(authToken);
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
