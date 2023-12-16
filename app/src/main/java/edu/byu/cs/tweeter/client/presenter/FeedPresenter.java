package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FeedService;
import edu.byu.cs.tweeter.client.model.service.observer.ListObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter extends ListPresenter<Status> implements ListObserver<Status> {
    private static final String LOG_TAG = "FeedPresenter";

    public FeedPresenter(ListView<Status> view){
        super(view);
    }

    @Override
    public void getItems(AuthToken authToken, User targetUser){
        getFeed(Cache.getInstance().getCurrUserAuthToken(), targetUser, PAGE_SIZE, last);
    }

    public void getFeed(AuthToken authToken, User targetUser, int limit, Status last){
        new FeedService().getFeed(authToken, targetUser, limit, last, this);
    }
}