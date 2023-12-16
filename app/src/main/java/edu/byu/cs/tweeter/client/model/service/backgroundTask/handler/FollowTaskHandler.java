package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.observer.GenericSuccessObserver;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;

public class FollowTaskHandler extends BackgroundTaskHandler<FollowService.FollowObserver> {
    public FollowTaskHandler(FollowService.FollowObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(FollowService.FollowObserver observer, Bundle data) {
        observer.handleFollowSuccess();
    }
}
