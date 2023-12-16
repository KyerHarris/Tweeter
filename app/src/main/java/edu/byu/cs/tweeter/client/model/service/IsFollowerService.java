package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.IsFollowerTaskHandler;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class IsFollowerService extends ExecuteService{
    public interface IsFollowerObserver extends ServiceObserver{
        void handleIsFollowerSuccess(Boolean bool);
    }
    public void isFollower(AuthToken authToken, User follower, User followee, IsFollowerObserver observer){
        IsFollowerTask task = new IsFollowerTask(authToken, follower, followee, new IsFollowerTaskHandler(observer));
        execute(task);
    }
}
