package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.HasMoreTaskHandler;
import edu.byu.cs.tweeter.client.model.service.observer.ListObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class GetFollowService extends ExecuteService{
    /**
     * Creates an instance.
     */
    public GetFollowService() {}

    /**
     * Requests the users that the user specified in the request is following.
     * Limits the number of followees returned and returns the next set of
     * followees after any that were returned in a previous request.
     * This is an asynchronous operation.
     *
     * @param authToken the session auth token.
     * @param targetUser the user for whom followees are being retrieved.
     * @param limit the maximum number of followees to return.
     * @param lastFollowee the last followee returned in the previous request (can be null).
     */
    public void getFollowing(AuthToken authToken, User targetUser, int limit, User lastFollowee, ListObserver<User> observer) {
        GetFollowingTask followingTask = getGetFollowingTask(authToken, targetUser, limit, lastFollowee, observer);
        execute(followingTask);
    }



    /**
     * Returns an instance of {@link GetFollowingTask}. Allows mocking of the
     * GetFollowingTask class for testing purposes. All usages of GetFollowingTask
     * should get their instance from this method to allow for proper mocking.
     *
     * @return the instance.
     */
    // This method is public so it can be accessed by test cases
    public GetFollowingTask getGetFollowingTask(AuthToken authToken, User targetUser, int limit, User lastFollowee, ListObserver<User> observer) {
        return new GetFollowingTask(authToken, targetUser, limit, lastFollowee, new HasMoreTaskHandler<User>(observer));
    }

    public void getFollowers(AuthToken authToken, User user, int limit, User lastFollower, ListObserver<User> observer){
        GetFollowersTask followersTask = getGetFollowers(authToken, user, limit, lastFollower, observer);
        execute(followersTask);
    }

    public GetFollowersTask getGetFollowers(AuthToken authToken, User user, int limit, User lastFollower, ListObserver<User> observer){
        return new GetFollowersTask(authToken, user, limit, lastFollower, new HasMoreTaskHandler<User>(observer));
    }
}
