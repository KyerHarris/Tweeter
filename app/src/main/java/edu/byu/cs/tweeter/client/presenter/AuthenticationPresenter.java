package edu.byu.cs.tweeter.client.presenter;

import android.util.Log;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.AuthenticationService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class AuthenticationPresenter extends Presenter implements AuthenticationService.AuthenticationObserver {
    private static final String LOG_TAG = "AuthPresenter";

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface AuthView extends View {
        void success(User user, AuthToken authToken);
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public AuthenticationPresenter(AuthView view) {
        super(view);
    }

    public void register(String firstName, String lastName, String alias, String password, String imageToUpload){
        try{
            validateRegistration(firstName, lastName, alias, password, imageToUpload);
        }catch (Exception e){
            view.displayErrorMessage(e.getMessage());
            return;
        }
        getService().register(firstName, lastName, alias, password, imageToUpload, this);
    }

    private void validateRegistration(String firstName, String lastName, String alias, String password, String imageToUpload) {
        if (firstName.length() == 0) {
            throw new IllegalArgumentException("First Name cannot be empty.");
        }
        if (lastName.length() == 0) {
            throw new IllegalArgumentException("Last Name cannot be empty.");
        }
        if (alias.length() == 0) {
            throw new IllegalArgumentException("Alias cannot be empty.");
        }
        if (alias.charAt(0) != '@') {
            throw new IllegalArgumentException("Alias must begin with @.");
        }
        if (alias.length() < 2) {
            throw new IllegalArgumentException("Alias must contain 1 or more characters after the @.");
        }
        if (password.length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        if (imageToUpload == null) {
            throw new IllegalArgumentException("Profile image must be uploaded.");
        }
    }

    /**
     * Initiates the login process.
     *
     * @param username the user's username.
     * @param password the user's password.
     */
    public void initiateLogin(String username, String password) {
        try{
            validateLogin(username, password);
        }
        catch (IllegalArgumentException e){
            view.displayErrorMessage(e.getMessage());
            return;
        }
        getService().login(username, password, this);
    }
    public void validateLogin(String alias, String password) {
        if (alias.length() > 0 && alias.charAt(0) != '@') {
            throw new IllegalArgumentException("Alias must begin with @.");
        }
        if (alias.length() < 2) {
            throw new IllegalArgumentException("Alias must contain 1 or more characters after the @.");
        }
        if (password.length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
    }

    private AuthenticationService getService(){
        return new AuthenticationService();
    }

    /**
     * Invoked when the login request completes if the login was successful. Notifies the view of
     * the successful login.
     *
     * @param user the logged-in user.
     * @param authToken the session auth token.
     */
    @Override
    public void handleSuccess(User user, AuthToken authToken) {
        // Cache user session information
        Cache.getInstance().setCurrUser(user);
        Cache.getInstance().setCurrUserAuthToken(authToken);

        ((AuthView)view).success(user, authToken);
    }
}