package edu.byu.cs.tweeter.server.service;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.server.abstractFactory.DaoFactory;
import edu.byu.cs.tweeter.server.dao.DynamoFollowsDao;
import edu.byu.cs.tweeter.server.dao.FollowsDaoInterface;
import edu.byu.cs.tweeter.util.Pair;

public class PostStatusService extends StatusService {
    private FollowsDaoInterface followsDao = new DynamoFollowsDao();
    public PostStatusService(DaoFactory daoFactory) {
        super(daoFactory);
    }
    public PostStatusResponse postStatus(PostStatusRequest request) {
        String currUserAlias = authTokenDao.getAlias(request.getAuthToken());
        validateAuthtoken(request.getAuthToken(), currUserAlias);
        statusDao.create(request.getStatus());
        feedDao.post(request.getStatus());
        return new PostStatusResponse(true);
    }
}