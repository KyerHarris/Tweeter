package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public abstract class PagedStatusRequest extends PagedRequest {
    private String alias;
    private Status lastStatus;
    protected PagedStatusRequest() {}

    public PagedStatusRequest(AuthToken authToken, String alias, int limit, Status lastStatus) {
        super(authToken, limit);
        this.alias = alias;
        this.lastStatus = lastStatus;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Status getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(Status lastStatus) {
        this.lastStatus = lastStatus;
    }
}
