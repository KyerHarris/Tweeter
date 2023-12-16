package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.LogoutService;
import edu.byu.cs.tweeter.client.model.service.observer.GenericSuccessObserver;

public class LogoutHandler extends BackgroundTaskHandler<LogoutService.LogoutObserver> {
    public LogoutHandler(LogoutService.LogoutObserver observer) {
        super(observer);
    }
    @Override
    protected void handleSuccessMessage(LogoutService.LogoutObserver observer, Bundle data) {
        observer.handleLogoutSuccess();
    }
}
