package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.server.abstractFactory.DaoFactory;

public abstract class AuthenticatedService extends Service{
    protected AuthenticatedService(DaoFactory daoFactory){
        this.authTokenDao = daoFactory.createAuthTokenDao();
    }
    protected void validateAuthtoken(AuthToken authToken, String alias) {
        if (authToken == null) {
            throw new RuntimeException("[Bad Request] no authtoken");
        }
        if (!authTokenDao.validate(authToken, alias)) {
            throw new RuntimeException("[Bad Request] invalid authtoken");
        }
        // check if authtoken is expired
        // following getuser??
    }
}
