package edu.byu.cs.tweeter.server.sqs;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.google.gson.Gson;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.DynamoFollowsDao;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.server.dao.bean.FollowsBean;
import edu.byu.cs.tweeter.util.Pair;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PartitionHandler implements RequestHandler<SQSEvent, Void> {
    private static final int BATCH_SIZE = 15;
    private static final String APPEND_QUEUE_URL = "https://sqs.us-east-2.amazonaws.com/953227881999/AppendQueue";
    private SqsClient sqsClient = SqsClient.builder().build();
    private DynamoFollowsDao followsDao = new DynamoFollowsDao();

    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        System.out.println("Request entered");
        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            Status payload = parseMessage(msg.getBody());
            System.out.println("Payload partitioned");
            String userAlias = payload.getUser().getAlias();

            List<FollowsBean> followers = followsDao.get10000Followers(userAlias);
            for (int i = 0; i < followers.size(); i += BATCH_SIZE) {
                List<String> batchAliases = followers.subList(i, Math.min(i + BATCH_SIZE, followers.size()))
                        .stream()
                        .map(FollowsBean::getFollowerHandle)
                        .collect(Collectors.toList());

                sendBatchToAppendQueue(payload, batchAliases);
            }
        }
        return null;
    }

    private Status parseMessage(String messageBody) {
        Gson gson = new Gson();
        return gson.fromJson(messageBody, Status.class);
    }

    private void sendBatchToAppendQueue(Status payload, List<String> followerAliases) {
        String messageBody = createMessageBody(payload, followerAliases);
        sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(APPEND_QUEUE_URL)
                .messageBody(messageBody)
                .build());
        System.out.println("Batch sent");
    }

    private String createMessageBody(Status payload, List<String> followerAliases) {
        Gson gson = new Gson();
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("status", payload);
        messageMap.put("followerAliases", followerAliases);

        return gson.toJson(messageMap);
    }
}
