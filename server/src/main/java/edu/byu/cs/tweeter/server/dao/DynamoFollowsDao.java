package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.bean.FollowsBean;
import edu.byu.cs.tweeter.server.dao.bean.UserBean;
import edu.byu.cs.tweeter.util.Pair;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.util.*;

public class DynamoFollowsDao implements FollowsDaoInterface {
    public static final String INDEX = "followee_handle-follow_handle-index";
    private static final String TABLE = "follows";
    private static final String FOLLOWER_ATTR = "follow_handle";
    private static final String FOLLOWEE_ATTR = "followee_handle";

    private final DynamoDbTable<FollowsBean> followsTable;
    private final DynamoDbEnhancedClient enhancedClient;

    public DynamoFollowsDao() {
        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                .region(Region.US_EAST_2)
                .build();

        enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();

        followsTable = enhancedClient.table(TABLE, TableSchema.fromBean(FollowsBean.class));
    }

    @Override
    public void create(String followerHandle, String followeeHandle, String followerName, String followeeName) {
        followsTable.putItem(new FollowsBean(followerHandle, followeeHandle, followerName, followeeName));
    }
    @Override
    public void create(String followerHandle, String followeeHandle) {
        followsTable.putItem(new FollowsBean(followerHandle, followeeHandle));
    }

    public List<FollowsBean> get10000Followers(String targetUserAlias) {
        DynamoDbIndex<FollowsBean> index = followsTable.index(INDEX);

        QueryEnhancedRequest request = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(Key.builder()
                        .partitionValue(targetUserAlias)
                        .build()))
                .limit(10000)
                .build();

        SdkIterable<Page<FollowsBean>> pages = index.query(request);
        List<FollowsBean> followers = new ArrayList<>();

        for (Page<FollowsBean> page : pages) {
            followers.addAll(page.items());
            if (followers.size() >= 10000) {
                break;
            }
        }

        return followers;
    }
    @Override
    public Pair<List<User>, Boolean> getFollowers(String targetUserAlias, int pageSize, String lastUserAlias) {
        DynamoDbIndex<FollowsBean> index = followsTable.index(INDEX);

        QueryEnhancedRequest.Builder requestBuilder = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(Key.builder()
                        .partitionValue(targetUserAlias)
                        .build()))
                .limit(pageSize);

        if (lastUserAlias != null && !lastUserAlias.isEmpty()) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put("followee_handle", AttributeValue.builder().s(targetUserAlias).build());
            startKey.put("follow_handle", AttributeValue.builder().s(lastUserAlias).build());
            requestBuilder.exclusiveStartKey(startKey);
        }

        QueryEnhancedRequest request = requestBuilder.build();
        List<User> followers = new ArrayList<>();
        boolean hasMore = false;

        DynamoUserDao userDao = new DynamoUserDao();
        Iterator<Page<FollowsBean>> pages = index.query(request).iterator();
        if (pages.hasNext()) {
            Page<FollowsBean> page = pages.next();
            page.items().forEach(followsBean -> {
                User user = userDao.get(followsBean.getFollowerHandle());
                if(user != null) {
                    followers.add(user);
                }
            });
            hasMore = page.lastEvaluatedKey() != null;
        }

        return new Pair<>(followers, hasMore);
    }
    @Override
    public Pair<List<User>, Boolean> getFollowees(String targetUserAlias, int pageSize, String lastUserAlias ) {
        Key key = Key.builder()
                .partitionValue(targetUserAlias)
                .build();

        QueryEnhancedRequest.Builder requestBuilder = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(key))
                .limit(pageSize);

        if(isNonEmptyString(lastUserAlias)) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(FOLLOWER_ATTR, AttributeValue.builder().s(targetUserAlias).build());
            startKey.put(FOLLOWEE_ATTR, AttributeValue.builder().s(lastUserAlias).build());

            requestBuilder.exclusiveStartKey(startKey);
        }

        QueryEnhancedRequest request = requestBuilder.build();
        List<User> followees = new ArrayList<>();
        boolean hasMore = false;

        DynamoUserDao userDao = new DynamoUserDao();
        Iterator<Page<FollowsBean>> pages = followsTable.query(request).iterator();
        if (pages.hasNext()) {
            Page<FollowsBean> page = pages.next();
            page.items().forEach(followsBean -> {
                User user = userDao.get(followsBean.getFolloweeHandle());
                if(user != null) {
                    followees.add(user);
                }
            });
            hasMore = page.lastEvaluatedKey() != null;
        }



        return new Pair<>(followees, hasMore);
    }
    @Override
    public int countFollowers(String followeeHandle) {
        DynamoDbIndex<FollowsBean> index = followsTable.index(INDEX);

        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder()
                        .partitionValue(followeeHandle)
                        .build());

        int count = 0;
        Iterator<Page<FollowsBean>> pages = index.query(queryConditional).iterator();
        while (pages.hasNext()) {
            count += pages.next().items().size();
        }
        return count;
    }
    @Override
    public int countFollowees(String followerHandle) {
        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder()
                        .partitionValue(followerHandle)
                        .build());

        int count = 0;
        Iterator<Page<FollowsBean>> pages = followsTable.query(queryConditional).iterator();
        while (pages.hasNext()) {
            count += pages.next().items().size();
        }
        return count;
    }

    @Override
    public void update(String followerHandle, String followerName, String followeeHandle, String followeeName) {
        followsTable.updateItem(new FollowsBean(followerHandle, followeeHandle, followerName, followeeName));
    }
    @Override
    public void delete(String followerHandle, String followeeHandle) {
        Key key = Key.builder()
                .partitionValue(followerHandle)
                .sortValue(followeeHandle)
                .build();
        followsTable.deleteItem(key);
    }
    public boolean doesUserFollow(String followerHandle, String followeeHandle) {
//        QueryConditional queryConditional = QueryConditional
//                .keyEqualTo(Key.builder()
//                        .partitionValue(followerHandle)
//                        .sortValue(followeeHandle)
//                        .build());
//        Iterator<Page<FollowsBean>> results = followsTable.query(queryConditional).iterator();
//        if (results.hasNext()) {
//            Page<FollowsBean> page = results.next();
//            List<FollowsBean> items = page.items();
//            if(!items.isEmpty()) {
//                return true;
//            }
//        }
//        return false;
        Key key = Key.builder()
                .partitionValue(followerHandle)
                .sortValue(followeeHandle)
                .build();
        return (followsTable.getItem(key) != null) ? true : false;
    }

    @Override
    public void addFollowersBatch(List<String> followerAliases, String followeeAlias) {
        List<FollowsBean> batchToWrite = new ArrayList<>();
        for (String followerAlias : followerAliases) {
            FollowsBean followsBean = new FollowsBean(followerAlias, followeeAlias);
            batchToWrite.add(followsBean);

            if (batchToWrite.size() == 25) {
                writeFollowsBatch(batchToWrite);
                batchToWrite = new ArrayList<>();
            }
        }

        if (!batchToWrite.isEmpty()) {
            writeFollowsBatch(batchToWrite);
        }
    }

    private void writeFollowsBatch(List<FollowsBean> followsBeans) {
        if (followsBeans.size() > 25) {
            throw new RuntimeException("Too many follows to write in a single batch");
        }

        DynamoDbTable<FollowsBean> table = enhancedClient.table("follows", TableSchema.fromBean(FollowsBean.class));
        WriteBatch.Builder<FollowsBean> writeBuilder = WriteBatch.builder(FollowsBean.class).mappedTableResource(table);

        for (FollowsBean followsBean : followsBeans) {
            writeBuilder.addPutItem(builder -> builder.item(followsBean));
        }

        BatchWriteItemEnhancedRequest batchWriteRequest = BatchWriteItemEnhancedRequest.builder()
                .writeBatches(writeBuilder.build())
                .build();

        try {
            BatchWriteResult result = enhancedClient.batchWriteItem(batchWriteRequest);

            if (!result.unprocessedPutItemsForTable(table).isEmpty()) {
                writeFollowsBatch(result.unprocessedPutItemsForTable(table));
            }
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
    private boolean isNonEmptyString(String value) {
        return (value != null && value.length() > 0);
    }
}