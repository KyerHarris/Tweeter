package edu.byu.cs.tweeter.server.abstractFactory;

import edu.byu.cs.tweeter.server.dao.AuthTokenDaoInterface;
import edu.byu.cs.tweeter.server.dao.FeedDaoInterface;
import edu.byu.cs.tweeter.server.dao.FollowsDaoInterface;
import edu.byu.cs.tweeter.server.dao.S3DaoInterface;
import edu.byu.cs.tweeter.server.dao.StatusDaoInterface;
import edu.byu.cs.tweeter.server.dao.UserDaoInterface;

public interface DaoFactory {
    DaoFactory instance = new DynamoDbDaoFactory();
    static DaoFactory getInstance() {
        return instance;
    }
    UserDaoInterface createUserDao();
    AuthTokenDaoInterface createAuthTokenDao();
    FollowsDaoInterface createFollowsDao();
    FeedDaoInterface createFeedDao();
    StatusDaoInterface createStatusDao();
    S3DaoInterface createS3Dao();
}
