package com.examples.federation.users.resolvers;

import com.examples.federation.users.domain.User;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

@Component
public class QueryResolver implements GraphQLQueryResolver {
    private static final Map<String, User> USERS = Stream.of(
            new User("a", "a"),
            new User("b", "b")
    ).collect(toMap(User::getId, Function.identity()));

    public CompletableFuture<User> getUserById(String id) {
        return CompletableFuture.completedFuture(USERS.get(id));
    }

    public Flux<User> getUsers() {
        return Flux.fromIterable(USERS.values());
    }
}
