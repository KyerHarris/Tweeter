package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.LogoutHandler;
import edu.byu.cs.tweeter.client.model.service.observer.GenericSuccessObserver;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;

public class LogoutService extends ExecuteService {
    public interface LogoutObserver extends ServiceObserver {
        void handleLogoutSuccess();
    }
    public LogoutService(){}
    public void logout(AuthToken authToken, LogoutObserver observer){
        LogoutTask task = new LogoutTask(authToken, new LogoutHandler(observer));
        execute(task);
    }
}
