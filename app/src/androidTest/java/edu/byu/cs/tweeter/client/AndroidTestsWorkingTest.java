package edu.byu.cs.tweeter.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.presenter.AuthenticationPresenter;
import edu.byu.cs.tweeter.client.presenter.ListPresenter;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;
import edu.byu.cs.tweeter.client.presenter.StoryPresenter;
import edu.byu.cs.tweeter.client.presenter.View;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * This class exists purely to prove that tests in your androidTest/java folder have the correct dependencies.
 * Click on the green arrow to the left of the class declarations to run. These tests should pass if all
 * dependencies are correctly set up.
 */
public class AndroidTestsWorkingTest {
    AuthenticationPresenter.AuthView loginView = Mockito.mock(AuthenticationPresenter.AuthView.class);
    AuthenticationPresenter loginPresenter = new AuthenticationPresenter(loginView);
    MainPresenter.MainView mainView = Mockito.mock(MainPresenter.MainView.class);
    MainPresenter mainPresenter = new MainPresenter(mainView);

    @Test
    public void loginTest(){
        loginPresenter.initiateLogin("@Emma", "test");
        //countdown latch
        wait10();
        ArgumentCaptor<AuthToken> authTokenCaptor = ArgumentCaptor.forClass(AuthToken.class);
        Mockito.verify(loginView, Mockito.times(1)).success(Mockito.any(), authTokenCaptor.capture());
        AuthToken capturedAuthToken = authTokenCaptor.getValue();

        User user = new User("emma", "rowland", "@Emma", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png", "test");
        Status testStatus = new Status("test post", user, 123456789L, new ArrayList<>(), new ArrayList<>());
        mainPresenter.post(testStatus);
        wait10();
        Mockito.verify(mainView, Mockito.times(1)).displayInfoMessage(Mockito.any());

        ListPresenter.ListView<Status> listView = Mockito.mock(ListPresenter.ListView.class);
        StoryPresenter storyPresenter = new StoryPresenter(listView);
        storyPresenter.getStory(capturedAuthToken, user, 10, null);
        wait10();

        ArgumentCaptor<List<Status>> argumentCaptor = ArgumentCaptor.forClass(List.class);
        Mockito.verify(listView, Mockito.times(1)).addItems(argumentCaptor.capture());
        List<Status> capturedStatus = argumentCaptor.getValue();

        Assertions.assertTrue(capturedStatus.contains(testStatus));
    }

    public void wait10(){
        try {
            Thread.sleep(10000);
        } catch (Exception e){}
        return;
    }

}
