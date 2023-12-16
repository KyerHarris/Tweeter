package edu.byu.cs.tweeter.server.service;

import java.util.Random;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.server.abstractFactory.DaoFactory;
import edu.byu.cs.tweeter.server.dao.UserDaoInterface;

public class IsFollowerService extends FollowsService {
    UserDaoInterface userDao;
    public IsFollowerService(DaoFactory daoFactory){
        super(daoFactory);
        userDao = daoFactory.createUserDao();
    }
    public IsFollowerResponse isFollower(IsFollowerRequest request) {
        validateAuthtoken(request.getAuthToken(), authTokenDao.getAlias(request.getAuthToken()));

        return new IsFollowerResponse(true, followsDao.doesUserFollow(request.getFollower(), request.getFollowee()));
    }
}
