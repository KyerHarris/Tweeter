package edu.byu.cs.tweeter.client.presenter;

import android.util.Log;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.GetFollowService;
import edu.byu.cs.tweeter.client.model.service.observer.ListObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowersPresenter extends ListPresenter<User> implements ListObserver<User> {

    private static final String LOG_TAG = "FollowersPresenter";
    public FollowersPresenter(ListView<User> view){
        super(view);
    }

    /**
     * Returns an instance of {@link GetFollowService}. Allows mocking of the FollowService class
     * for testing purposes. All usages of FollowService should get their FollowService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    public GetFollowService getFollowersService() {
        return new GetFollowService();
    }

    @Override
    public void getItems(AuthToken authToken, User targetUser) {
        getFollowersService().getFollowers(authToken, targetUser, PAGE_SIZE, getLast(), this);
    }
}
