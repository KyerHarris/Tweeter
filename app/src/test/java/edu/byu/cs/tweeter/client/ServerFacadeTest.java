package edu.byu.cs.tweeter.client;

import edu.byu.cs.tweeter.client.model.service.StoryService;
import edu.byu.cs.tweeter.client.model.service.observer.ListObserver;
import edu.byu.cs.tweeter.client.network.ServerFacade;
import edu.byu.cs.tweeter.client.presenter.ListPresenter;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;
import edu.byu.cs.tweeter.client.presenter.StoryPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.*;
import edu.byu.cs.tweeter.model.net.response.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ServerFacadeTest {
    private AuthToken authToken = new AuthToken("", 0);
    private User user = new User("FirstName", "LastName", "imageURL");

    @Test
    public void testRegister() {
        RegisterRequest request = new RegisterRequest("@newUser", "password",
                "FirstName", "LastName", "imageURL");
        RegisterResponse response = ServerFacade.register(request);
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertNotNull(response.getUser());
    }

    @Test
    public void testGetFollowers() {
        FollowersRequest request = new FollowersRequest(authToken, "@newUser", 10, null);
        FollowersResponse response = ServerFacade.getFollowers(request);
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertNotNull(response.getFollowees());
    }

    @Test
    public void testGetFollowersError() {
        FollowersRequest request = new FollowersRequest(null, "@newUser", 10, null);
        FollowersResponse response = ServerFacade.getFollowers(request);
        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertNull(response.getFollowees());
    }

    @Test
    public void testGetFollowersCount() {
        FollowersCountRequest request = new FollowersCountRequest(authToken, "@newUser");
        FollowersCountResponse response = ServerFacade.getFollowersCount(request);
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertTrue(response.getCount() >= 0);
    }

    @Test
    public void testGetFollowersCountError() {
        FollowersCountRequest request = new FollowersCountRequest(null, "@newUser");
        FollowersCountResponse response = ServerFacade.getFollowersCount(request);
        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertTrue(response.getCount() == 0);
    }

    @Test
    public void storyServiceTest(){
        StoryService service = new StoryService();
        ListObserver<Status> presenter = new ListObserver<>() {
            @Override
            public void handleSuccess(List items, boolean hasMorePages) {
                Assertions.assertTrue(true);
            }

            @Override
            public void handleFailure(String message) {

            }

            @Override
            public void handleException(Exception exception) {

            }
        };
        service.getStory(authToken, user, 10, null, presenter);
    }
}