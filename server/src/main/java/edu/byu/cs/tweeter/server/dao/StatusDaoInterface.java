package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.util.Pair;

public interface StatusDaoInterface {
    void create(Status status);
    void delete(Status status);
    Pair<List<Status>, Boolean> getStory(String userAlias, int limit, Status lastStatus);
}
