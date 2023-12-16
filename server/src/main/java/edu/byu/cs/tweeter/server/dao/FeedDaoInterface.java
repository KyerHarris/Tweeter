package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.util.Pair;

public interface FeedDaoInterface {
    void create(Status status, String feedOwner);
    void delete(Status status);
    void post(Status status);
    Pair<List<Status>, Boolean> getFeed(String userAlias, int limit, Status lastStatus);
}
