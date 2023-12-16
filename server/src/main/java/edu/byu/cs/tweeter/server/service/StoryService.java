package edu.byu.cs.tweeter.server.service;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.server.abstractFactory.DaoFactory;
import edu.byu.cs.tweeter.server.dao.StatusDaoInterface;
import edu.byu.cs.tweeter.util.Pair;

public class StoryService extends StatusService {
    public StoryService(DaoFactory daoFactory){
        super(daoFactory);
    }
    public StoryResponse getStory(StoryRequest request) {
        validateAuthtoken(request.getAuthToken(), authTokenDao.getAlias(request.getAuthToken()));

        Pair<List<Status>, Boolean> pair = statusDao.getStory(request.getAlias(), request.getLimit(), request.getLastStatus());

        return new StoryResponse(pair.getFirst(), pair.getSecond());
    }
}