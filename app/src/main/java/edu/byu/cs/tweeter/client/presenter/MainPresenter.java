package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.CountService;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.IsFollowerService;
import edu.byu.cs.tweeter.client.model.service.LogoutService;
import edu.byu.cs.tweeter.client.model.service.PostStatusService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenter extends Presenter implements CountService.GetFollowCountObserver, FollowService.FollowObserver, LogoutService.LogoutObserver, PostStatusService.PostStatusObserver, IsFollowerService.IsFollowerObserver {
    public MainPresenter(MainView view) {
        super(view);
    }
    public interface MainView extends View {
        void numFollowers(int count);
        void numFollowing(int count);
        void followSuccess();
        void logoutSuccessful();
        void postSuccessful();
        void unfollowSuccess();
        void isFollower(boolean isFollower);
        void displayInfoMessage(String string);
    }

    public void follow(User followee){
        new FollowService().follow(Cache.getInstance().getCurrUserAuthToken(), followee, this);
    }
    public void unfollow(User followee){
        new FollowService().unfollow(Cache.getInstance().getCurrUserAuthToken(), followee, this);
    }
    public void isFollower(User user){
        new IsFollowerService().isFollower(Cache.getInstance().getCurrUserAuthToken(), Cache.getInstance().getCurrUser(), user, this);
    }
    public void getFollowersCount(User targetUser){
        new CountService().getFollowersCount(Cache.getInstance().getCurrUserAuthToken(), targetUser, this);
    }
    public void getFollowingCount(User targetUser){
        new CountService().getFollowingCount(Cache.getInstance().getCurrUserAuthToken(), targetUser, this);
    }
    public void logout(){
        new LogoutService().logout(Cache.getInstance().getCurrUserAuthToken(), this);
    }
    public void post(Status status){
        ((MainView) view).displayInfoMessage("Posting Status");
        getPostStatusService().postStatus(Cache.getInstance().getCurrUserAuthToken(), status, this);
    }
    public PostStatusService getPostStatusService() {
        return new PostStatusService();
    }

    @Override
    public void handleFollowersSuccess(int count) {
        ((MainView)view).numFollowers(count);
    }
    @Override
    public void handleFollowingSuccess(int count) {
        ((MainView)view).numFollowing(count);
    }
    @Override
    public void handleFollowSuccess() {
        ((MainView)view).followSuccess();
    }
    @Override
    public void handleUnfollowSuccess() {
        ((MainView)view).unfollowSuccess();
    }
    @Override
    public void handleLogoutSuccess() {
        Cache.getInstance().clearCache();
        ((MainView)view).logoutSuccessful();
    }
    @Override
    public void handlePostSuccess() {
        ((MainView)view).postSuccessful();
    }
    @Override
    public void handleIsFollowerSuccess(Boolean bool) {
        ((MainView)view).isFollower(bool);
    }
}
