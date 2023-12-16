package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginResponse extends AuthenticateResponse {
    public LoginResponse(String message) {
        super(message);
    }

    public LoginResponse(User user, AuthToken authToken) {
        super(user, authToken);
    }
}
