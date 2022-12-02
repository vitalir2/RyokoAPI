# Ktor gRPC / GraphQL example [Work in progress  🔨]
RyokoAPI (TravelAPI) is a showcase for different server API contract styles using Ktor

Contract styles
---
- gRPC - by custom plugin
- GraphQL - by KGraphQL Ktor plugin

How to run?
---
Prerequisites: Java 11 or above

Run from the project root dir: `./gradlew run` or by your IDE
Application config can be changed from the code in Application.kt

gRPC runs on 50551 port by default (see `GrpcPluginConfig`)
GraphQL - on 8080 port, the routes start with /graphql
