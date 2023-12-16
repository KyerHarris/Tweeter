package edu.byu.cs.tweeter.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.PostStatusService;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class PostStatusTest {
    private MainPresenter mainPresenter;
    private MainPresenter.MainView mainView;
    private PostStatusService service;
    static private Status status;

    @BeforeAll
    static void beforeAll() {
        status = new Status("test post",  new User("firstName", "lastName", "@coolAlias"), System.currentTimeMillis(), new ArrayList<String>() {{
            add("https://youtube.com");
        }}, new ArrayList<String>() {{
            add("@Dude1");
        }});
    }

    @BeforeEach
    void beforeEach() {
        mainView = Mockito.spy(MainPresenter.MainView.class);
        mainPresenter = Mockito.spy(new MainPresenter(mainView));
        service = Mockito.spy(PostStatusService.class);
        Mockito.when(mainPresenter.getPostStatusService()).thenReturn(service);
        Cache mockCache = Mockito.spy(Cache.class);
        Cache.setInstance(mockCache);
    }
    @AfterEach
    void afterEach() {
        mainPresenter = null;
    }
    @Test
    @DisplayName("Posting Status Success")
    void postingStatusSuccess() {
        Answer<Void> answer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                PostStatusService.PostStatusObserver observer = invocation.getArgument(2, PostStatusService.PostStatusObserver.class);
                observer.handlePostSuccess();
                return null;
            }
        };
        Mockito.doAnswer(answer).when(service).postStatus(Mockito.any(), Mockito.any(), Mockito.any());

        mainPresenter.post(status);
        //verify "Posting Status" was called
        Mockito.verify(mainView, Mockito.times(1)).displayInfoMessage(Mockito.any());
        //verify service was called
        Mockito.verify(mainPresenter, Mockito.times(1)).post(Mockito.any());
        ArgumentCaptor<AuthToken> authTokenArgumentCaptor = ArgumentCaptor.forClass(AuthToken.class);
        ArgumentCaptor<Status> statusArgumentCaptor = ArgumentCaptor.forClass(Status.class);
        ArgumentCaptor<PostStatusService.PostStatusObserver> observerArgumentCaptor = ArgumentCaptor.forClass(PostStatusService.PostStatusObserver.class);
        Mockito.verify(service, Mockito.times(1)).postStatus(
            authTokenArgumentCaptor.capture(),
            statusArgumentCaptor.capture(),
            observerArgumentCaptor.capture()
        );
        // Extract the captured arguments
        AuthToken capturedAuthToken = authTokenArgumentCaptor.getValue();
        Status capturedStatus = statusArgumentCaptor.getValue();
        PostStatusService.PostStatusObserver capturedObserver = observerArgumentCaptor.getValue();
        Assertions.assertTrue(capturedStatus != null && !capturedStatus.post.isEmpty());
        Assertions.assertTrue(capturedObserver != null);
        //verify success message was displayed
        Mockito.verify(mainPresenter, Mockito.times(1)).handlePostSuccess();
        Mockito.verify(mainView, Mockito.times(1)).postSuccessful();
    }

    @Test
    @DisplayName("Posting Status Failure")
    void postingStatusFailure() {
        Answer<Void> answer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                PostStatusService.PostStatusObserver observer = invocation.getArgument(2, PostStatusService.PostStatusObserver.class);
                observer.handleFailure("fail test");
                return null;
            }
        };
        Mockito.doAnswer(answer).when(service).postStatus(Mockito.any(), Mockito.any(), Mockito.any());

        mainPresenter.post(status);
        //verify "Posting Status" was called
        Mockito.verify(mainView, Mockito.times(1)).displayInfoMessage(Mockito.any());
        //verify service was called
        Mockito.verify(mainPresenter, Mockito.times(1)).post(Mockito.any());
        ArgumentCaptor<AuthToken> authTokenArgumentCaptor = ArgumentCaptor.forClass(AuthToken.class);
        ArgumentCaptor<Status> statusArgumentCaptor = ArgumentCaptor.forClass(Status.class);
        ArgumentCaptor<PostStatusService.PostStatusObserver> observerArgumentCaptor = ArgumentCaptor.forClass(PostStatusService.PostStatusObserver.class);
        Mockito.verify(service, Mockito.times(1)).postStatus(
                authTokenArgumentCaptor.capture(),
                statusArgumentCaptor.capture(),
                observerArgumentCaptor.capture()
        );
        // Extract the captured arguments
        AuthToken capturedAuthToken = authTokenArgumentCaptor.getValue();
        Status capturedStatus = statusArgumentCaptor.getValue();
        PostStatusService.PostStatusObserver capturedObserver = observerArgumentCaptor.getValue();
        Assertions.assertTrue(capturedStatus != null && !capturedStatus.post.isEmpty());
        Assertions.assertTrue(capturedObserver != null);
        //verify success message was displayed
        Mockito.verify(mainPresenter, Mockito.times(1)).handleFailure(Mockito.any());
        Mockito.verify(mainView, Mockito.times(1)).displayErrorMessage(Mockito.any());
    }

    @Test
    @DisplayName("Posting Status Exception")
    void postingStatusException() {
        Answer<Void> answer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                PostStatusService.PostStatusObserver observer = invocation.getArgument(2, PostStatusService.PostStatusObserver.class);
                observer.handleException(new Exception());
                return null;
            }
        };
        Mockito.doAnswer(answer).when(service).postStatus(Mockito.any(), Mockito.any(), Mockito.any());

        mainPresenter.post(status);
        //verify "Posting Status" was called
        Mockito.verify(mainView, Mockito.times(1)).displayInfoMessage(Mockito.any());
        //verify service was called
        Mockito.verify(mainPresenter, Mockito.times(1)).post(Mockito.any());
        ArgumentCaptor<AuthToken> authTokenArgumentCaptor = ArgumentCaptor.forClass(AuthToken.class);
        ArgumentCaptor<Status> statusArgumentCaptor = ArgumentCaptor.forClass(Status.class);
        ArgumentCaptor<PostStatusService.PostStatusObserver> observerArgumentCaptor = ArgumentCaptor.forClass(PostStatusService.PostStatusObserver.class);
        Mockito.verify(service, Mockito.times(1)).postStatus(
                authTokenArgumentCaptor.capture(),
                statusArgumentCaptor.capture(),
                observerArgumentCaptor.capture()
        );
        // Extract the captured arguments
        AuthToken capturedAuthToken = authTokenArgumentCaptor.getValue();
        Status capturedStatus = statusArgumentCaptor.getValue();
        PostStatusService.PostStatusObserver capturedObserver = observerArgumentCaptor.getValue();
        Assertions.assertTrue(capturedStatus != null && !capturedStatus.post.isEmpty());
        Assertions.assertTrue(capturedObserver != null);
        //verify success message was displayed
        Mockito.verify(mainPresenter, Mockito.times(1)).handleException(Mockito.any());
        Mockito.verify(mainView, Mockito.times(1)).displayErrorMessage(Mockito.any());
    }
}
