package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.UserRequest;
import edu.byu.cs.tweeter.model.net.response.UserResponse;
import edu.byu.cs.tweeter.server.abstractFactory.DaoFactory;
import edu.byu.cs.tweeter.server.dao.UserDaoInterface;

public class GetUserService extends AuthenticatedService {
    private UserDaoInterface userDao;
    public GetUserService(DaoFactory daoFactory) {
        super(daoFactory);
        userDao = daoFactory.createUserDao();
    }
    public UserResponse getUser(UserRequest request) {
        validateAuthtoken(request.getAuthToken(), authTokenDao.getAlias(request.getAuthToken()));

        return new UserResponse(true, userDao.get(request.getAlias()));
    }
}