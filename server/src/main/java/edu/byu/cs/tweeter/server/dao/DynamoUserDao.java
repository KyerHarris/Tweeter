package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.bean.UserBean;
import edu.byu.cs.tweeter.server.service.utils.PasswordUtils;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteResult;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

public class DynamoUserDao implements UserDaoInterface {

    private final DynamoDbTable<UserBean> userTable;
    private final DynamoDbEnhancedClient enhancedClient;

    public DynamoUserDao() {
        // Initialize the DynamoDB client
        DynamoDbClient dbClient = DynamoDbClient.builder()
                .region(Region.US_EAST_2) // Set your region accordingly
                .build();

        // Create an Enhanced Client
        enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dbClient)
                .build();

        // Map the 'User' table in DynamoDB
        userTable = enhancedClient.table("Users", TableSchema.fromBean(UserBean.class));
    }

    public void create(String firstName, String lastName, String alias, String imageUrl, String password) {
        S3Dao s3Dao = new S3Dao();
        userTable.putItem(new UserBean(firstName, lastName, alias, imageUrl, PasswordUtils.hashPassword(password)));
    }

    public User get(String alias) {
        Key key = Key.builder()
                .partitionValue(alias)
                .build();
        return convertBeanToUser(userTable.getItem(key));
    }

    public void update(User user) {
        UserBean userBean = new UserBean();
        userBean.setFirstName(user.getFirstName());
        userBean.setLastName(user.getLastName());
        userBean.setAlias(user.getAlias());
        userBean.setImageUrl(user.getImageUrl());
        userTable.updateItem(userBean);
    }

    public void delete(String alias) {
        Key key = Key.builder()
                .partitionValue(alias)
                .build();
        userTable.deleteItem(key);
    }

    private User convertBeanToUser(UserBean userBean) {
        return userBean == null ? null : new User(userBean.getFirstName(), userBean.getLastName(), userBean.getAlias(), userBean.getImageUrl(), userBean.getPassword());
    }

    public void addUserBatch(List<User> users) {
        List<UserBean> batchToWrite = new ArrayList<>();
        for (User u : users) {
            UserBean userBean = convertUserToBean(u);
            batchToWrite.add(userBean);

            if (batchToWrite.size() == 25) {
                writeChunkOfUserBeans(batchToWrite);
                batchToWrite = new ArrayList<>();
            }
        }

        if (!batchToWrite.isEmpty()) {
            writeChunkOfUserBeans(batchToWrite);
        }
    }

    private void writeChunkOfUserBeans(List<UserBean> userBeans) {
        if (userBeans.size() > 25) {
            throw new RuntimeException("Too many users to write in a single batch");
        }

        DynamoDbTable<UserBean> table = enhancedClient.table("Users", TableSchema.fromBean(UserBean.class));
        WriteBatch.Builder<UserBean> writeBuilder = WriteBatch.builder(UserBean.class).mappedTableResource(table);

        for (UserBean userBean : userBeans) {
            writeBuilder.addPutItem(builder -> builder.item(userBean));
        }

        BatchWriteItemEnhancedRequest batchWriteRequest = BatchWriteItemEnhancedRequest.builder()
                .writeBatches(writeBuilder.build())
                .build();

        try {
            BatchWriteResult result = enhancedClient.batchWriteItem(batchWriteRequest);

            if (!result.unprocessedPutItemsForTable(table).isEmpty()) {
                writeChunkOfUserBeans(result.unprocessedPutItemsForTable(table));
            }
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
    private UserBean convertUserToBean (User user){
        return new UserBean(user.getFirstName(), user.getLastName(), user.getAlias(), user.getImageUrl(), user.getPassword());
    }
}
