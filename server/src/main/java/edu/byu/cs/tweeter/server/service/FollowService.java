package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.server.abstractFactory.DaoFactory;
import edu.byu.cs.tweeter.server.dao.UserDaoInterface;

public class FollowService extends FollowsService {
    UserDaoInterface userDao;
    public FollowService(DaoFactory daoFactory){
        super(daoFactory);
        userDao = daoFactory.createUserDao();
    }
    public FollowResponse follow(FollowRequest request) {
        User currUser = userDao.get(authTokenDao.getAlias(request.getAuthToken()));
        validateAuthtoken(request.getAuthToken(), currUser.getAlias());
        User followee = userDao.get(request.getFollowee());

        followsDao.create(currUser.getAlias(), followee.getAlias(), currUser.getFirstName() + " " + currUser.getLastName(), followee.getFirstName() + " " + followee.getLastName());
        return new FollowResponse(true);
    }
}