package edu.byu.cs.tweeter.server.abstractFactory;

import edu.byu.cs.tweeter.server.dao.AuthTokenDaoInterface;
import edu.byu.cs.tweeter.server.dao.DyanmoAuthTokenDao;
import edu.byu.cs.tweeter.server.dao.DynamoFeedDao;
import edu.byu.cs.tweeter.server.dao.DynamoFollowsDao;
import edu.byu.cs.tweeter.server.dao.DynamoStatusDao;
import edu.byu.cs.tweeter.server.dao.FeedDaoInterface;
import edu.byu.cs.tweeter.server.dao.FollowsDaoInterface;
import edu.byu.cs.tweeter.server.dao.S3Dao;
import edu.byu.cs.tweeter.server.dao.S3DaoInterface;
import edu.byu.cs.tweeter.server.dao.StatusDaoInterface;
import edu.byu.cs.tweeter.server.dao.UserDaoInterface;
import edu.byu.cs.tweeter.server.dao.DynamoUserDao;

public class DynamoDbDaoFactory implements DaoFactory{
    @Override
    public UserDaoInterface createUserDao() {
        return new DynamoUserDao();
    }
    @Override
    public AuthTokenDaoInterface createAuthTokenDao(){
        return new DyanmoAuthTokenDao();
    }
    @Override
    public FollowsDaoInterface createFollowsDao(){
        return new DynamoFollowsDao();
    }

    @Override
    public FeedDaoInterface createFeedDao() {
        return new DynamoFeedDao();
    }

    @Override
    public StatusDaoInterface createStatusDao() {
        return new DynamoStatusDao();
    }

    @Override
    public S3DaoInterface createS3Dao() {
        return new S3Dao();
    }
}
