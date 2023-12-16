package edu.byu.cs.tweeter.client.model.service.observer;

import java.util.List;

import edu.byu.cs.tweeter.client.presenter.StoryPresenter;
import edu.byu.cs.tweeter.model.domain.Status;

public interface ListObserver<T> extends ServiceObserver{
    void handleSuccess(List<T> items, boolean hasMorePages);
}
