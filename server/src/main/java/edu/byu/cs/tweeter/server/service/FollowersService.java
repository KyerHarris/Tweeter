package edu.byu.cs.tweeter.server.service;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.server.abstractFactory.DaoFactory;
import edu.byu.cs.tweeter.util.Pair;

public class FollowersService extends FollowsService {
    public FollowersService(DaoFactory daoFactory){
        super(daoFactory);
    }
    public FollowersResponse getFollowers(FollowersRequest request) {
        validateAuthtoken(request.getAuthToken(), authTokenDao.getAlias(request.getAuthToken()));

        Pair<List<User>, Boolean> pair = followsDao.getFollowers(request.getFolloweeAlias(), request.getLimit(), request.getLastFollowerAlias());

        return new FollowersResponse(pair.getFirst(), pair.getSecond());
    }
}