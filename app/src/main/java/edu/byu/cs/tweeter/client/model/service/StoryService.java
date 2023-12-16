package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.HasMoreTaskHandler;
import edu.byu.cs.tweeter.client.model.service.observer.ListObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryService extends ExecuteService {
    public StoryService(){};

    public void getStory(AuthToken authToken, User user, int pageSize, Status lastStatus, ListObserver<Status> observer){
        GetStoryTask task = new GetStoryTask(authToken, user, pageSize, lastStatus, new HasMoreTaskHandler<Status>(observer));
        execute(task);
    }
}
