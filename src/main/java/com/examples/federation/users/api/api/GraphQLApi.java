package com.examples.federation.users.api.api;

import graphql.ExecutionInput;
import org.reactivestreams.Publisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import java.util.Map;

@Controller
public class GraphQLApi {

    private graphql.GraphQL graphQL;

    public GraphQLApi(graphql.GraphQL graphQL) {
        this.graphQL = graphQL;
    }

    @PostMapping(
            path = "/graphql",
            consumes = {"application/json"}
    )
    public Mono<ResponseEntity<Publisher<Map<String, Object>>>> post(@RequestBody String query) {
        return Mono.fromFuture(this.graphQL.executeAsync(ExecutionInput.newExecutionInput()
                .query(query)
                .build()))
        .map(r -> ResponseEntity.ok(Mono.just(r.toSpecification())));
    }
}
