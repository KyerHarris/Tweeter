package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StoryService;
import edu.byu.cs.tweeter.client.model.service.observer.ListObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter extends ListPresenter<Status> implements ListObserver<Status> {
    /**
     * Creates an instance.
     *
     * @param view      the view for which this class is the presenter.
     */
    public StoryPresenter(ListView<Status> view) {
        super(view);
    }

    @Override
    public void getItems(AuthToken authToken, User targetUser){
        getStory(Cache.getInstance().getCurrUserAuthToken(), targetUser, PAGE_SIZE, last);
    }

    /**
     * Requests the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned for a previous request. This is an asynchronous
     * operation.
     *
     * @param authToken    the session auth token.
     * @param targetUser   the user for whom followees are being retrieved.
     * @param limit        the maximum number of followees to return.
     * @param lastStatus the last followee returned in the previous request (can be null).
     */
    public void getStory(AuthToken authToken, User targetUser, int limit, Status lastStatus) {
        getStoryService().getStory(authToken, targetUser, limit, lastStatus, this);
    }

    /**
     * Returns an instance of {@link StoryService}. Allows mocking of the FollowService class
     * for testing purposes. All usages of FollowService should get their FollowService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    public StoryService getStoryService() {
        return new StoryService();
    }

}
