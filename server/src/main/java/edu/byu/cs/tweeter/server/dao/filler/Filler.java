package edu.byu.cs.tweeter.server.dao.filler;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.abstractFactory.DaoFactory;
import edu.byu.cs.tweeter.server.dao.DynamoFollowsDao;
import edu.byu.cs.tweeter.server.dao.FollowsDaoInterface;
import edu.byu.cs.tweeter.server.dao.UserDaoInterface;

public class Filler {

    // How many follower users to add
    // We recommend you test this with a smaller number first, to make sure it works for you
    private final static int NUM_USERS = 10000;

    // The alias of the user to be followed by each user created
    // This example code does not add the target user, that user must be added separately.
    private final static String FOLLOW_TARGET = "@Kyer";

    public static void fillDatabase() {
        UserDaoInterface userDAO = DaoFactory.getInstance().createUserDao();
        DynamoFollowsDao followDAO = new DynamoFollowsDao();

        List<String> followers = new ArrayList<>();
        List<User> users = new ArrayList<>();

        for (int i = 1; i <= NUM_USERS; i++) {
            String name = "Guy " + i;
            String alias = "guy" + i;

            User user = new User();
            user.setAlias(alias);
            user.setFirstName(name);
            users.add(user);

            followers.add(alias);
        }

//        if (!users.isEmpty()) {
//            userDAO.addUserBatch(users);
//        }
        if (!followers.isEmpty()) {
            followDAO.addFollowersBatch(followers, FOLLOW_TARGET);
        }
    }

}