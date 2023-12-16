package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Handler;

import edu.byu.cs.tweeter.client.model.service.CountService;

/**
 * Handles messages from the background task indicating that the task is done, by invoking
 * methods on the observer.
 */
public class GetFollowingCountHandler extends GetCountHandler {
    public GetFollowingCountHandler(CountService.GetFollowCountObserver observer) {
        super(observer);
    }

    @Override
    public void handleSuccess(int count) {
        ((CountService.GetFollowCountObserver) observer).handleFollowingSuccess(count);
    }
}