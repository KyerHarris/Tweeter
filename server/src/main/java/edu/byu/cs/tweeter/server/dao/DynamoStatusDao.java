package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.server.dao.bean.StatusBean;
import edu.byu.cs.tweeter.util.Pair;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class DynamoStatusDao implements StatusDaoInterface {
    private static final String TABLE_NAME = "Statuses";
    private UserDaoInterface userDao = new DynamoUserDao();
    private final DynamoDbTable<StatusBean> statusTable;

    public DynamoStatusDao() {
        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                .region(Region.US_EAST_2) // Set the appropriate region
                .build();

        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();

        statusTable = enhancedClient.table(TABLE_NAME, TableSchema.fromBean(StatusBean.class));
    }

    @Override
    public void create(Status status) {
        StatusBean statusBean = convertToStatusBean(status);
        statusTable.putItem(statusBean);
    }

    @Override
    public void delete(Status status) {
        Key key = Key.builder()
                .partitionValue(status.getUser().getAlias())
                .sortValue(status.getTimestamp())
                .build();

        statusTable.deleteItem(key);
    }

    @Override
    public Pair<List<Status>, Boolean> getStory(String userAlias, int pagesize, Status lastStatus) {
        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(userAlias).build());

        QueryEnhancedRequest.Builder requestBuilder = QueryEnhancedRequest.builder()
                .queryConditional(queryConditional)
                .limit(pagesize);

        if (lastStatus != null) {
            Map<String, AttributeValue> exclusiveStartKey = new HashMap<>();
            exclusiveStartKey.put("User", AttributeValue.builder().s(userAlias).build());
            exclusiveStartKey.put("Timestamp", AttributeValue.builder().n(String.valueOf(lastStatus.getTimestamp())).build());
            requestBuilder.exclusiveStartKey(exclusiveStartKey);
        }

        QueryEnhancedRequest request = requestBuilder.build();


        List<Status> story = new ArrayList<>();
        boolean hasMore = false;

        Iterator<Page<StatusBean>> pages = statusTable.query(request).iterator();
        if (pages.hasNext()) {
            Page<StatusBean> page = pages.next();
            page.items().forEach(statusBean -> story.add(convertToStatusDomain(statusBean)));
            hasMore = page.lastEvaluatedKey() != null;
        }

        return new Pair<>(story, hasMore);
    }

    private StatusBean convertToStatusBean(Status status) {
        return new StatusBean(status.getPost(), status.getUser().getAlias(), status.getTimestamp(), status.getUrls(), status.getMentions());
    }

    private Status convertToStatusDomain(StatusBean statusBean) {
        return new Status(statusBean.getPost(), userDao.get(statusBean.getUser()), statusBean.getTimestamp(), statusBean.getUrls(), statusBean.getMentions());
    }
}
