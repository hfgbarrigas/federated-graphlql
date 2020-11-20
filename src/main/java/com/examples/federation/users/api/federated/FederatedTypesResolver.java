package com.examples.federation.users.api.federated;

import graphql.GraphQLContext;
import graphql.TypeResolutionEnvironment;
import graphql.schema.GraphQLObjectType;
import graphql.schema.TypeResolver;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FederatedTypesResolver implements TypeResolver {
    @Override
    public GraphQLObjectType getType(TypeResolutionEnvironment env) {
        final GraphQLContext context = env.getContext();

        String typename = (String) context.get("__typename");

        return Optional.ofNullable(typename)
                .map(s -> env.getSchema().getObjectType(typename))
                .orElse(null);
    }
}
