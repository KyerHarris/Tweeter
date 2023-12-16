package edu.byu.cs.tweeter.server.sqs;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.server.dao.DynamoFeedDao;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class AppendHandler implements RequestHandler<SQSEvent, Void> {
    private final DynamoFeedDao feedDao = new DynamoFeedDao(); // Ensure proper initialization
    private final Gson gson = new Gson();

    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        System.out.println("entered handleRequest");
        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            Map<String, Object> messageMap = parseMessage(msg.getBody());
            Status status = gson.fromJson(gson.toJson(messageMap.get("status")), Status.class);
            System.out.println("Status retrieved");
            List<String> followerAliases = gson.fromJson(gson.toJson(messageMap.get("followerAliases")),
                    new TypeToken<List<String>>() {}.getType());
            System.out.println("Followers retrieved");

            feedDao.createBatch(followerAliases, status);
            System.out.println("Batch sent");
        }
        return null;
    }

    private Map<String, Object> parseMessage(String messageBody) {
        Type type = new TypeToken<Map<String, Object>>() {}.getType();
        return gson.fromJson(messageBody, type);
    }
}
