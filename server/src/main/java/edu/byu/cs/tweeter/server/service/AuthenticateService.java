package edu.byu.cs.tweeter.server.service;

import java.time.Instant;
import java.util.UUID;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.server.abstractFactory.DaoFactory;
import edu.byu.cs.tweeter.server.dao.AuthTokenDaoInterface;
import edu.byu.cs.tweeter.server.dao.UserDaoInterface;

public abstract class AuthenticateService extends Service {
    protected UserDaoInterface userDao;
    protected AuthenticateService(DaoFactory daoFactory) {
        this.userDao = daoFactory.createUserDao();
        this.authTokenDao = daoFactory.createAuthTokenDao();
    }


    protected AuthToken generateAuthToken(String alias) {
        authTokenDao.deleteTokensByAlias(alias);
        AuthToken authToken = new AuthToken(UUID.randomUUID().toString(), Instant.now().toEpochMilli());
        authTokenDao.create(authToken, alias);
        return authToken;
    }
}
