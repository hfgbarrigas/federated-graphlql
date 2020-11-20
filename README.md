# Async _Entity DataFetcher

    curl -X POST -H 'Content-Type: application/json' -d 'query { _entities(representations: [{__typename: "User", id: "a"}]) {... on User {id name}}}' localhost:8080/graphql -v
