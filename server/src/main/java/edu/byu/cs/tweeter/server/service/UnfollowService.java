package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.net.response.UnfollowResponse;
import edu.byu.cs.tweeter.server.abstractFactory.DaoFactory;

public class UnfollowService extends FollowsService {
    public UnfollowService(DaoFactory daoFactory){
        super(daoFactory);
    }
    public UnfollowResponse unfollow(UnfollowRequest request) {
        String currUserAlias = authTokenDao.getAlias(request.getAuthToken());
        validateAuthtoken(request.getAuthToken(), currUserAlias);
        followsDao.delete(currUserAlias, request.getFollowee());
        return new UnfollowResponse(true);
    }
}