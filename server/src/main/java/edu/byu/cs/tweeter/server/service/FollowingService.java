package edu.byu.cs.tweeter.server.service;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.server.abstractFactory.DaoFactory;
import edu.byu.cs.tweeter.util.Pair;

public class FollowingService extends FollowsService{
    public FollowingService(DaoFactory daoFactory) {
        super(daoFactory);
    }
    public FollowingResponse getFollowing(FollowingRequest request) {
        validateAuthtoken(request.getAuthToken(), authTokenDao.getAlias(request.getAuthToken()));

        Pair<List<User>, Boolean> pair = followsDao.getFollowees(request.getFolloweeAlias(), request.getLimit(), request.getLastFolloweeAlias());

        return new FollowingResponse(pair.getFirst(), pair.getSecond());
    }
}
