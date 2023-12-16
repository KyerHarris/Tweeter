package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.util.List;

import edu.byu.cs.tweeter.client.network.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of statuses from a user's feed.
 */
public class GetFeedTask extends PagedStatusTask {

    public GetFeedTask(AuthToken authToken, User targetUser, int limit, Status lastStatus,
                       Handler messageHandler) {
        super(authToken, targetUser, limit, lastStatus, messageHandler);
    }

    @Override
    protected Pair<List<Status>, Boolean> getItems() {
        FeedRequest request;
        if (getLastItem() != null) {
            request = new FeedRequest(getAuthToken(), getTargetUser().getAlias(), getLimit(), getLastItem());
        } else {
            request = new FeedRequest(getAuthToken(), getTargetUser().getAlias(), getLimit(), null);
        }
        FeedResponse response = ServerFacade.getFeed(request);
        if (response.isSuccess()) {
            return new Pair<>(response.getStatuses(), response.getHasMorePages());
        }
        return null;
    }
}
