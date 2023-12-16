package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.FollowingCountRequest;
import edu.byu.cs.tweeter.model.net.response.FollowingCountResponse;
import edu.byu.cs.tweeter.server.abstractFactory.DaoFactory;

public class FollowingCountService extends FollowsService {
    public FollowingCountService(DaoFactory daoFactory){
        super(daoFactory);
    }
    public FollowingCountResponse getFollowingCount(FollowingCountRequest request) {
        validateAuthtoken(request.getAuthToken(), authTokenDao.getAlias(request.getAuthToken()));

        return new FollowingCountResponse(true, followsDao.countFollowees(request.getUser()));
    }
}