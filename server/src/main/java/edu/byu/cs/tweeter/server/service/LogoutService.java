package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;
import edu.byu.cs.tweeter.server.abstractFactory.DaoFactory;

public class LogoutService extends AuthenticatedService {
    public LogoutService(DaoFactory daoFactory) {
        super(daoFactory);
    }
    public LogoutResponse logout(LogoutRequest request) {
        validateAuthtoken(request.getAuthToken(), authTokenDao.getAlias(request.getAuthToken()));
        authTokenDao.deleteToken(request.getAuthToken());
        return new LogoutResponse(true);
    }
}
