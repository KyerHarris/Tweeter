package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.PagedTask;
import edu.byu.cs.tweeter.client.model.service.observer.ListObserver;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;

public class HasMoreTaskHandler<T> extends BackgroundTaskHandler {
    public HasMoreTaskHandler(ListObserver<T> observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(ServiceObserver observer, Bundle data) {
        List<T> list = (List<T>) data.getSerializable(PagedTask.ITEMS_KEY);
        boolean hasMore = data.getBoolean(PagedTask.MORE_PAGES_KEY);
        ((ListObserver)observer).handleSuccess(list, hasMore);
    }
}
