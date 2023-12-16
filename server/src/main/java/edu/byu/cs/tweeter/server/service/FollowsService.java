package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.abstractFactory.DaoFactory;
import edu.byu.cs.tweeter.server.dao.FollowsDaoInterface;

public abstract class FollowsService extends AuthenticatedService {
    protected FollowsDaoInterface followsDao;
    FollowsService(DaoFactory daoFactory) {
        super(daoFactory);
        followsDao = daoFactory.createFollowsDao();
    }
}
