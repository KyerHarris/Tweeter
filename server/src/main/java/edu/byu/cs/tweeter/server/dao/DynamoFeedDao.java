package edu.byu.cs.tweeter.server.dao;

import com.google.gson.Gson;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.server.dao.bean.FeedBean;
import edu.byu.cs.tweeter.util.Pair;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.BatchWriteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.BatchWriteItemResponse;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.PutRequest;
import software.amazon.awssdk.services.dynamodb.model.WriteRequest;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DynamoFeedDao implements FeedDaoInterface {
    private static final String TABLE_NAME = "Feeds"; // Replace with your DynamoDB table name
    private final DynamoDbTable<FeedBean> feedTable;
    private UserDaoInterface userDao = new DynamoUserDao();
    private SqsClient sqsClient;
    private final DynamoDbEnhancedClient enhancedClient;
    private final String partitionQueueUrl = "https://sqs.us-east-2.amazonaws.com/953227881999/PartitionQueue";


    public DynamoFeedDao() {
        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                .region(Region.US_EAST_2)
                .build();

        enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();

        this.sqsClient = SqsClient.builder()
                .region(Region.US_EAST_2)
                .build();

        feedTable = enhancedClient.table(TABLE_NAME, TableSchema.fromBean(FeedBean.class));
    }

    @Override
    public void post(Status status) {
        Gson gson = new Gson();
        String messagePayload = gson.toJson(status);

        sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(partitionQueueUrl)
                .messageBody(messagePayload)
                .build());
    }

    public void createBatch(List<String> followerAliases, Status status) {
        List<FeedBean> writeRequests = new ArrayList<>();

        for (String alias : followerAliases) {
            writeRequests.add(convertToFeedBean(status, alias));

            // Check if the batch is full (DynamoDB batch write limit is 25 items)
            if (writeRequests.size() == 25) {
                writeBatch(writeRequests);
                writeRequests.clear();
            }
        }

        // Write any remaining items
        if (!writeRequests.isEmpty()) {
            writeBatch(writeRequests);
        }
    }

    private void writeBatch(List<FeedBean> feedBeans) {
        // Create a builder for the batch write
        BatchWriteItemEnhancedRequest.Builder batchBuilder = BatchWriteItemEnhancedRequest.builder();

        // Add each item in the batch
        for (FeedBean feedBean : feedBeans) {
            WriteBatch writeBatch = WriteBatch.builder(FeedBean.class)
                    .mappedTableResource(feedTable)
                    .addPutItem(feedBean)
                    .build();
            batchBuilder.addWriteBatch(writeBatch);
        }

        BatchWriteItemEnhancedRequest batchWriteRequest = batchBuilder.build();

        try {
            enhancedClient.batchWriteItem(batchWriteRequest);
            // Handle any logic post batch write if needed
        } catch (DynamoDbException e) {
            System.out.println("Error with batch write");
            try {
                wait(1000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            writeBatch(feedBeans);
        }
    }

    @Override
    public void create(Status status, String feedOwner) {
        FeedBean feedBean = convertToFeedBean(status, feedOwner);
        feedTable.putItem(feedBean);
    }

    @Override
    public void delete(Status status) {
        Key key = Key.builder()
                .partitionValue(status.getUser().getAlias()) // Assuming userAlias is the partition key
                .sortValue(status.getTimestamp()) // Assuming timestamp is the sort key
                .build();
        feedTable.deleteItem(key);
    }

    @Override
    public Pair<List<Status>, Boolean> getFeed(String userAlias, int pageSize, Status lastStatus) {
        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(userAlias).build());

        QueryEnhancedRequest.Builder requestBuilder = QueryEnhancedRequest.builder()
                .queryConditional(queryConditional)
                .limit(pageSize);

        if (lastStatus != null) {
            Map<String, AttributeValue> exclusiveStartKey = new HashMap<>();
            exclusiveStartKey.put("FeedOwner", AttributeValue.builder().s(userAlias).build());
            exclusiveStartKey.put("Timestamp", AttributeValue.builder().n(String.valueOf(lastStatus.getTimestamp())).build());
            requestBuilder.exclusiveStartKey(exclusiveStartKey);
        }

        QueryEnhancedRequest request = requestBuilder.build();
        List<Status> feed = new ArrayList<>();
        boolean hasMore = false;

        Iterator<Page<FeedBean>> pages = feedTable.query(request).iterator();
        if (pages.hasNext()) {
            Page<FeedBean> page = pages.next();
            page.items().forEach(feedBean -> feed.add(convertToStatusDomain(feedBean)));
            hasMore = page.lastEvaluatedKey() != null;
        }
        return new Pair<>(feed, hasMore);
    }

    private FeedBean convertToFeedBean(Status status, String feedOwner) {
        return new FeedBean(status.getPost(), status.getUser().getAlias(), status.getTimestamp(), status.getUrls(), status.getMentions(), feedOwner);
    }

    private Status convertToStatusDomain(FeedBean feedBean) {
        return new Status(feedBean.getPost(), userDao.get(feedBean.getPersonWhoPosted()), feedBean.getTimestamp(), feedBean.getUrls(), feedBean.getMentions());
    }
}
