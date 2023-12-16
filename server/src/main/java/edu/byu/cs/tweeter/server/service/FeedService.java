package edu.byu.cs.tweeter.server.service;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.server.abstractFactory.DaoFactory;
import edu.byu.cs.tweeter.server.dao.DynamoFollowsDao;
import edu.byu.cs.tweeter.server.dao.DynamoUserDao;
import edu.byu.cs.tweeter.server.dao.FeedDaoInterface;
import edu.byu.cs.tweeter.server.dao.FollowsDaoInterface;
import edu.byu.cs.tweeter.server.dao.UserDaoInterface;
import edu.byu.cs.tweeter.util.Pair;

public class FeedService extends AuthenticatedService {
    private FeedDaoInterface feedDao;
    public FeedService(DaoFactory daoFactory){
        super(daoFactory);
        feedDao = daoFactory.createFeedDao();
    }
    public FeedResponse getFeed(FeedRequest request) {
        validateAuthtoken(request.getAuthToken(), request.getAlias());

//        UserDaoInterface userDao = new DynamoUserDao();
//        for (int i = 1; i <= 20; i++) {
//            String fakeUserName = "FakeUser" + i;
//            String fakeUserAlias = "@FakeUser" + i;
//            // Assuming createUser takes parameters like name, alias, etc.
//            userDao.create("FakeUser", Integer.toString(i), fakeUserAlias, "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png", "test");
//        }

//        FollowsDaoInterface followsDao = new DynamoFollowsDao();
//        String[] usersToFollow = {"@Kyer", "@Emma"};
//        for (int i = 1; i <= 20; i++) {
//            String fakeUserAlias = "@FakeUser" + i;
//            for (String userToFollow : usersToFollow) {
//                followsDao.create(fakeUserAlias, userToFollow, "FakeUser" + i, "Kyer Harris");
//                followsDao.create(userToFollow, fakeUserAlias, "Kyer Harris", "FakeUser" + i);
//            }
//        }


        Pair<List<Status>, Boolean> pair = feedDao.getFeed(request.getAlias(), request.getLimit(), request.getLastStatus());

        return new FeedResponse(pair.getFirst(), pair.getSecond());
    }
}