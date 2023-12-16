package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class Presenter implements ServiceObserver {
    View view;
    protected boolean isLoading = false;
    protected User user;



    public Presenter(View view){
        if(view == null) {
            throw new NullPointerException();
        }
        this.view = view;
    }

    @Override
    public void handleFailure(String message) {
        view.displayErrorMessage("Error: " + message);
    }

    @Override
    public void handleException(Exception exception) {
        view.displayErrorMessage("Exception: " + exception.getMessage());
    }
}
