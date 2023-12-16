package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class StoryRequest extends PagedStatusRequest {
    protected StoryRequest() {}
    public StoryRequest(AuthToken authToken, String alias, int limit, Status lastStatus) {
        super(authToken, alias, limit, lastStatus);
    }
}
