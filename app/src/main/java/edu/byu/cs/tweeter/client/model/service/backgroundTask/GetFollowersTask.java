package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.util.List;

import edu.byu.cs.tweeter.client.network.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of followers.
 */
public class GetFollowersTask extends PagedUserTask {

    public GetFollowersTask(AuthToken authToken, User targetUser, int limit, User lastFollower,
                            Handler messageHandler) {
        super(authToken, targetUser, limit, lastFollower, messageHandler);
    }

    @Override
    protected Pair<List<User>, Boolean> getItems() {
        FollowersRequest request;
        if (getLastItem() != null) {
            request = new FollowersRequest(getAuthToken(), getTargetUser().getAlias(), getLimit(), getLastItem().getAlias());
        } else {
            request = new FollowersRequest(getAuthToken(), getTargetUser().getAlias(), getLimit(), null);
        }
        FollowersResponse response = ServerFacade.getFollowers(request);
        if (response.isSuccess()) {
            return new Pair<>(response.getFollowees(), response.getHasMorePages());
        }
        sendFailedMessage(response.getMessage());
        return null;
    }
}
