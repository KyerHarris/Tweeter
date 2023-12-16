package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.PostStatusService;
import edu.byu.cs.tweeter.client.model.service.observer.GenericSuccessObserver;

public class PostStatusTaskHandler extends BackgroundTaskHandler<PostStatusService.PostStatusObserver> {
    public PostStatusTaskHandler(PostStatusService.PostStatusObserver observer) {
        super(observer);
    }
    @Override
    protected void handleSuccessMessage(PostStatusService.PostStatusObserver observer, Bundle data) {
        observer.handlePostSuccess();
    }
}
