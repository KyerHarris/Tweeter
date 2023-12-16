package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.GetUserHandler;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class UserService extends ExecuteService {

    public interface GetUserObserver extends ServiceObserver {
        void handleSuccess(User user);
    }

    public void getUser(GetUserObserver observer, String alias){
        GetUserTask userTask = getGetUserTask(Cache.getInstance().getCurrUserAuthToken(), alias, observer);
        execute(userTask);
    }
    public GetUserTask getGetUserTask(AuthToken authToken, String alias, GetUserObserver observer){
        return new GetUserTask(authToken, alias, new GetUserHandler(observer));
    }
}
