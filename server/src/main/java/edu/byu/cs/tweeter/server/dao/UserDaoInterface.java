package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public interface UserDaoInterface {
    void create(String firstName, String lastName, String alias, String imageUrl, String password);
    User get(String id);
    void update(User user);
    void delete(String id);
    void addUserBatch(List<User> users);
}
