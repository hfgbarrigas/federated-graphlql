package com.examples.federation.users.api.configuration;

import com.apollographql.federation.graphqljava.Federation;
import com.examples.federation.users.api.federated.FederatedEntitiesProvider;
import com.examples.federation.users.api.federated.FederatedTypesResolver;
import graphql.GraphQL;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.kickstart.tools.SchemaParserBuilder;
import graphql.schema.GraphQLSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphQLConfiguration {
    @Bean
    public GraphQLSchema graphQLSchema(FederatedTypesResolver federatedTypesResolver,
                                       FederatedEntitiesProvider federatedEntitiesProvider,
                                       GraphQLResolver... resolvers) {
        GraphQLSchema schema = new SchemaParserBuilder()
                .schemaString("type Query { userById(id: String!): User } type User @key(fields: \"id\") { id: String! name: String! }")
                .resolvers(resolvers)
                .build()
                .makeExecutableSchema();

        return Federation.transform(schema)
                .fetchEntities(federatedEntitiesProvider)
                .resolveEntityType(federatedTypesResolver)
                .build();
    }

    @Bean
    public GraphQL graphQL(GraphQLSchema graphQLSchema) {
        return GraphQL.newGraphQL(graphQLSchema).build();
    }
}
