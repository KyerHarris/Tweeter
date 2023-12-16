package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class UserPresenter extends Presenter implements UserService.GetUserObserver {

    @Override
    public void handleSuccess(User user) {
        ((UserView)view).displayUser(user);
    }

    public interface UserView extends View {
        void displayUser(User user);
    }
    public UserPresenter(UserView view){
        super(view);
    }

    public void getUser(String alias){
        view.displayErrorMessage("Getting user");
        new UserService().getUser(this, alias);
    }
}
