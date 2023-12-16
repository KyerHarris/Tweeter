package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.IsFollowerService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;

public class IsFollowerTaskHandler extends BackgroundTaskHandler<IsFollowerService.IsFollowerObserver> {
    public IsFollowerTaskHandler(IsFollowerService.IsFollowerObserver observer) {
        super(observer);
    }
    @Override
    protected void handleSuccessMessage(IsFollowerService.IsFollowerObserver observer, Bundle data) {
        observer.handleIsFollowerSuccess(data.getBoolean(IsFollowerTask.IS_FOLLOWER_KEY));
    }
}
