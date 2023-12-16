package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.GetFollowersCountHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.GetFollowingCountHandler;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class CountService extends ExecuteService{
    public interface GetFollowCountObserver extends ServiceObserver {
        public void handleFollowersSuccess(int count);

        public void handleFollowingSuccess(int count);
    }
    public void getFollowersCount(AuthToken authToken, User targetUser, GetFollowCountObserver observer){
        GetFollowersCountTask task = getGetFollowersCount(authToken, targetUser, observer);
        execute(task);
    }

    public GetFollowersCountTask getGetFollowersCount(AuthToken authToken, User targetUser, GetFollowCountObserver observer){
        return new GetFollowersCountTask(authToken, targetUser, new GetFollowersCountHandler(observer));
    }

    public void getFollowingCount(AuthToken authToken, User targetUser, GetFollowCountObserver observer){
        GetFollowingCountTask task = getGetFollowingCount(authToken, targetUser, observer);
        execute(task);
    }

    public GetFollowingCountTask getGetFollowingCount(AuthToken authToken, User targetUser, GetFollowCountObserver observer){
        return new GetFollowingCountTask(authToken, targetUser, new GetFollowingCountHandler(observer));
    }

}
