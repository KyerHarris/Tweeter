package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.AuthHandler;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class AuthenticationService extends ExecuteService{
    public interface AuthenticationObserver extends ServiceObserver {
        void handleSuccess(User user, AuthToken authToken);
    }

    public void AuthenticationService(){}

    /**
     * Makes an asynchronous login request.
     *
     * @param username the user's name.
     * @param password the user's password.
     */
    public void login(String username, String password, AuthenticationObserver observer) {
        LoginTask loginTask = getLoginTask(username, password, observer);
        execute(loginTask);
    }

    /**
     * Returns an instance of {@link LoginTask}. Allows mocking of the LoginTask class for
     * testing purposes. All usages of LoginTask should get their instance from this method to
     * allow for proper mocking.
     *
     * @return the instance.
     */
    LoginTask getLoginTask(String username, String password, AuthenticationObserver observer) {
        return new LoginTask(username, password, new AuthHandler(observer));
    }

    public void register(String firstName, String lastName, String alias, String password, String imageToUpload, AuthenticationObserver observer) {
        RegisterTask task = getRegisterTask(firstName, lastName, alias, password, imageToUpload, observer);
        execute(task);
    }

    RegisterTask getRegisterTask(String firstName, String lastName, String alias, String password, String imageToUpload, AuthenticationObserver observer) {
        return new RegisterTask(firstName, lastName, alias, password, imageToUpload, new AuthHandler(observer));
    }
}
