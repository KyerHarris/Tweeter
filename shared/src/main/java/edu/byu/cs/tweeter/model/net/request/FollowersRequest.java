package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * followers for a specified follower.
 */
public class FollowersRequest extends PagedRequest {
    private String followeeAlias;
    private String lastFollowerAlias;

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private FollowersRequest() {
    }

    /**
     * Creates an instance.
     *
     * @param followeeAlias     the alias of the user whose followees are to be returned.
     * @param limit             the maximum number of followees to return.
     * @param lastFollowerAlias the alias of the last followee that was returned in the previous request (null if
     *                          there was no previous request or if no followees were returned in the
     *                          previous request).
     */
    public FollowersRequest(AuthToken authToken, String followeeAlias, int limit, String lastFollowerAlias) {
        super(authToken, limit);
        this.followeeAlias = followeeAlias;
        this.lastFollowerAlias = lastFollowerAlias;
    }

    /**
     * Returns the follower whose followees are to be returned by this request.
     *
     * @return the follower.
     */
    public String getFolloweeAlias() {
        return followeeAlias;
    }

    /**
     * Sets the follower.
     *
     * @param followeeAlias the follower.
     */
    public void setFolloweeAlias(String followeeAlias) {
        this.followeeAlias = followeeAlias;
    }

    /**
     * Returns the last followee that was returned in the previous request or null if there was no
     * previous request or if no followees were returned in the previous request.
     *
     * @return the last followee.
     */
    public String getLastFollowerAlias() {
        return lastFollowerAlias;
    }

    /**
     * Sets the last followee.
     *
     * @param lastFollowerAlias the last followee.
     */
    public void setLastFollowerAlias(String lastFollowerAlias) {
        this.lastFollowerAlias = lastFollowerAlias;
    }
}
