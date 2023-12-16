package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.abstractFactory.DaoFactory;
import edu.byu.cs.tweeter.server.dao.FeedDaoInterface;
import edu.byu.cs.tweeter.server.dao.StatusDaoInterface;

public abstract class StatusService extends AuthenticatedService {
    StatusDaoInterface statusDao;
    FeedDaoInterface feedDao;
    protected StatusService(DaoFactory daoFactory) {
        super(daoFactory);
        statusDao = daoFactory.createStatusDao();
        feedDao = daoFactory.createFeedDao();
    }
}
