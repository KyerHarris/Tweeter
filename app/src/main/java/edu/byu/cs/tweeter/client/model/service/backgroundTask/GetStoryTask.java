package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import java.util.List;

import edu.byu.cs.tweeter.client.network.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of statuses from a user's story.
 */
public class GetStoryTask extends PagedStatusTask {

    public GetStoryTask(AuthToken authToken, User targetUser, int limit, Status lastStatus,
                        Handler messageHandler) {
        super(authToken, targetUser, limit, lastStatus, messageHandler);
    }

    @Override
    protected Pair<List<Status>, Boolean> getItems() {
        StoryRequest request;
        if (getLastItem() != null) {
            request = new StoryRequest(getAuthToken(), getTargetUser().getAlias(), getLimit(), getLastItem());
        } else {
            request = new StoryRequest(getAuthToken(), getTargetUser().getAlias(), getLimit(), null);
        }
        StoryResponse response = ServerFacade.getStory(request);
        if (response.isSuccess()) {
            return new Pair<>(response.getStatuses(), response.getHasMorePages());
        }
        sendFailedMessage(response.getMessage());
        return null;
    }
}
