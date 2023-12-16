package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.server.dao.bean.AuthTokenBean;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.regions.Region;

public class DyanmoAuthTokenDao implements AuthTokenDaoInterface {
    private final DynamoDbTable<AuthTokenBean> authTokenTable;

    public DyanmoAuthTokenDao() {
        DynamoDbClient dbClient = DynamoDbClient.builder()
                .region(Region.US_EAST_2)
                .build();

        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dbClient)
                .build();

        authTokenTable = enhancedClient.table("AuthTokens", TableSchema.fromBean(AuthTokenBean.class));
    }

    @Override
    public void create(AuthToken authToken, String alias) {
        AuthTokenBean authTokenBean = new AuthTokenBean();
        authTokenBean.setToken(authToken.getToken());
        authTokenBean.setAlias(alias);
        authTokenBean.setTimeStamp(authToken.getTimestamp());
        authTokenTable.putItem(authTokenBean);
    }

    @Override
    public boolean validate(AuthToken authToken, String alias) {
        Key key = Key.builder()
                .partitionValue(authToken.getToken())
                .build();
        AuthTokenBean authTokenBean = authTokenTable.getItem(key);
        return authTokenBean != null && authTokenBean.getAlias().equals(alias);
    }

    @Override
    public String getAlias(AuthToken authToken) {
        Key key = Key.builder()
                .partitionValue(authToken.getToken())
                .build();
        AuthTokenBean authTokenBean = authTokenTable.getItem(key);
        return authTokenBean != null ? authTokenBean.getAlias() : null;
    }

    @Override
    public void deleteTokensByAlias(String alias) {
        // Query to find all tokens by alias using the GSI
        DynamoDbIndex<AuthTokenBean> aliasIndex = authTokenTable.index("Alias-index");

        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(alias).build());

        Iterator<Page<AuthTokenBean>> results = aliasIndex.query(queryConditional).iterator();

        // Iterate over the results and delete each token
        while (results.hasNext()) {
            List<AuthTokenBean> tokens = results.next().items();
            for (AuthTokenBean token : tokens) {
                authTokenTable.deleteItem(token);
            }
        }
    }
    @Override
    public void deleteToken(AuthToken authToken) {
        Key key = Key.builder()
                .partitionValue(authToken.getToken())
                .build();
        authTokenTable.deleteItem(key);
    }
}
