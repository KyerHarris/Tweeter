package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.HasMoreTaskHandler;
import edu.byu.cs.tweeter.client.model.service.observer.ListObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedService extends ExecuteService{
    public FeedService(){};

    public void getFeed(AuthToken authToken, User user, int pageSize, Status lastStatus, ListObserver<Status> observer){
        GetFeedTask getFeedTask = new GetFeedTask(authToken, user, pageSize, lastStatus, new HasMoreTaskHandler<Status>(observer));
        execute(getFeedTask);
    }
}
