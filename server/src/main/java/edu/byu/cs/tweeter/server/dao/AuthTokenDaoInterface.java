package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public interface AuthTokenDaoInterface {
    void create(AuthToken authToken, String alias);
    boolean validate(AuthToken authToken, String alias);
    String getAlias(AuthToken authToken);
    void deleteTokensByAlias(String alias);
    void deleteToken(AuthToken authToken);
}
