package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.FollowService;

public class UnfollowTaskHandler extends BackgroundTaskHandler<FollowService.FollowObserver> {
    public UnfollowTaskHandler(FollowService.FollowObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(FollowService.FollowObserver observer, Bundle data) {
        observer.handleUnfollowSuccess();
    }
}
