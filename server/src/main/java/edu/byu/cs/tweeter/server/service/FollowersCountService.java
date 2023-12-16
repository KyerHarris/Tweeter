package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.FollowersCountRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersCountResponse;
import edu.byu.cs.tweeter.server.abstractFactory.DaoFactory;

public class FollowersCountService extends FollowsService {
    public FollowersCountService(DaoFactory daoFactory) {
        super(daoFactory);
    }
    public FollowersCountResponse getFollowersCount(FollowersCountRequest request) {
        validateAuthtoken(request.getAuthToken(), authTokenDao.getAlias(request.getAuthToken()));

        return new FollowersCountResponse(true, followsDao.countFollowers(request.getUser()));
    }
}