package edu.byu.cs.tweeter.model.net.response;

public class IsFollowerResponse extends Response {
    private boolean follower;
    public IsFollowerResponse(boolean success, boolean isFollower) {
        super(success);
        this.follower = isFollower;
    }

    public IsFollowerResponse(boolean success, String message, boolean isFollower) {
        super(success, message);
        this.follower = isFollower;
    }

    public boolean isFollower() {
        return follower;
    }

    public void setFollower(boolean follower) {
        this.follower = follower;
    }
}
