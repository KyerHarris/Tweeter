package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.server.abstractFactory.DaoFactory;
import edu.byu.cs.tweeter.server.dao.UserDaoInterface;
import edu.byu.cs.tweeter.server.service.utils.PasswordUtils;
import java.util.UUID;
import java.time.Instant;

public class LoginService extends AuthenticateService{
    public LoginService(DaoFactory daoFactory) {
        super(daoFactory);
    }

    public LoginResponse login(LoginRequest request) {
        if (request.getUsername() == null) {
            return new LoginResponse("[Bad Request] Missing a username");
        } else if (request.getPassword() == null) {
            return new LoginResponse("[Bad Request] Missing a password");
        }

        // Fetch user from the database
        User user = userDao.get(request.getUsername());
        if (user == null) {
            return new LoginResponse("[Unauthorized] Missing User");
        }
        if (!PasswordUtils.matches(request.getPassword(), user.getPassword())) {
            return new LoginResponse("[Unauthorized] Incorrect password");
        }

        // Generate an auth token (this method should be implemented)
        AuthToken authToken = generateAuthToken(request.getUsername());

        return new LoginResponse(user, authToken);
    }
}
