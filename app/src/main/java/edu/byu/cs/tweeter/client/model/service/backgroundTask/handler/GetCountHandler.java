package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.CountService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetCountTask;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;

public abstract class GetCountHandler extends BackgroundTaskHandler {
    public GetCountHandler(ServiceObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(ServiceObserver observer, Bundle data) {
        handleSuccess(data.getInt(GetCountTask.COUNT_KEY));
    }

    public abstract void handleSuccess(int count);
}
