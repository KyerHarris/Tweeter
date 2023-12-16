package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.PostStatusTaskHandler;
import edu.byu.cs.tweeter.client.model.service.observer.GenericSuccessObserver;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class PostStatusService extends ExecuteService{
    public interface PostStatusObserver extends ServiceObserver {
        void handlePostSuccess();
    }

    public PostStatusService(){}
    public void postStatus(AuthToken authtoken, Status status, PostStatusObserver observer){
        PostStatusTask task = new PostStatusTask(authtoken, status, new PostStatusTaskHandler(observer));
        execute(task);
    }
}
