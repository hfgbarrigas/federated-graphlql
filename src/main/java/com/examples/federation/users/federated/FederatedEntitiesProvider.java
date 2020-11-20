package com.examples.federation.users.federated;

import com.apollographql.federation.graphqljava._Entity;
import com.examples.federation.users.resolvers.QueryResolver;
import graphql.GraphQLContext;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

@Component
public class FederatedEntitiesProvider implements DataFetcher {

    private QueryResolver queryResolver;

    public FederatedEntitiesProvider(QueryResolver queryResolver) {
        this.queryResolver = queryResolver;
    }

    @Override
    public CompletableFuture<List<?>> get(DataFetchingEnvironment env) {
        return CompletableFuture.completedFuture(env.<List<Map<String, Object>>>getArgument(_Entity.argumentName)
                .stream()
                .map(values -> {
                    if ("User".equals(values.get("__typename"))) {
                        final Object id = values.get("id");
                        if (id instanceof String) {
                            GraphQLContext context = env.getContext();
                            context.put("__typename", "User");
                            return this.queryResolver.getUserById((String) id);
                        }
                    }
                    return null;
                })
                .collect(toList()));
    }
}
