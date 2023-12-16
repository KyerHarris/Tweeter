package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.bean.FollowsBean;
import edu.byu.cs.tweeter.util.Pair;

public interface FollowsDaoInterface {
    void create(String followerHandle, String followeeHandle, String followerName, String followeeName);
    void create(String followerHandle, String followeeHandle);
    Pair<List<User>, Boolean> getFollowers(String targetUserAlias, int pageSize, String lastUserAlias);
    Pair<List<User>, Boolean> getFollowees(String targetUserAlias, int pageSize, String lastUserAlias);
    int countFollowers(String followeeHandle);
    int countFollowees(String followerHandle);
    void update(String followerHandle, String followerName, String followeeHandle, String followeeName);
    void delete(String followerHandle, String followeeHandle);
    boolean doesUserFollow(String follower, String followee);
    void addFollowersBatch(List<String> followerAliases, String followeeAlias);
}
