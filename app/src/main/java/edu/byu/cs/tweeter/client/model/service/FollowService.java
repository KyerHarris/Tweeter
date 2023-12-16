package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.UnfollowTaskHandler;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.FollowTaskHandler;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowService extends ExecuteService{
    public interface FollowObserver extends ServiceObserver{
        void handleFollowSuccess();
        void handleUnfollowSuccess();
    }
    public void follow(AuthToken authToken, User followee, FollowObserver observer){
        FollowTask task = new FollowTask(authToken, followee, new FollowTaskHandler(observer));
        execute(task);
    }
    public void unfollow(AuthToken authToken, User followee, FollowObserver observer){
        UnfollowTask task = new UnfollowTask(authToken, followee, new UnfollowTaskHandler(observer));
        execute(task);
    }
}
