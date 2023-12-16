package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import edu.byu.cs.tweeter.client.model.service.CountService;

public class GetFollowersCountHandler extends GetCountHandler{
    public GetFollowersCountHandler(CountService.GetFollowCountObserver observer){
        super(observer);
    }
    @Override
    public void handleSuccess(int count) {
        ((CountService.GetFollowCountObserver) observer).handleFollowersSuccess(count);
    }
}
