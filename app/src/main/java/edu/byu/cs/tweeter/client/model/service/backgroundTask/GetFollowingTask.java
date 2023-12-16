package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.util.List;

import edu.byu.cs.tweeter.client.network.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of other users being followed by a specified user.
 */
public class GetFollowingTask extends PagedUserTask {

    public GetFollowingTask(AuthToken authToken, User targetUser, int limit, User lastFollowee,
                            Handler messageHandler) {
        super(authToken, targetUser, limit, lastFollowee, messageHandler);
    }

    @Override
    protected Pair<List<User>, Boolean> getItems() {
        FollowingRequest request;
        if (getLastItem() != null) {
            request = new FollowingRequest(getAuthToken(), getTargetUser().getAlias(), getLimit(), getLastItem().getAlias());
        } else {
            request = new FollowingRequest(getAuthToken(), getTargetUser().getAlias(), getLimit(), null);
        }
        FollowingResponse response = ServerFacade.getFollowing(request);
        if (response.isSuccess()) {
            return new Pair<>(response.getFollowees(), response.getHasMorePages());
        }
        sendFailedMessage(response.getMessage());
        return null;
    }
}
