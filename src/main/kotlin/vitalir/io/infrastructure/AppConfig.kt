package vitalir.io.infrastructure

data class AppConfig(
    val routingMethod: RoutingMethod,
) {

    enum class RoutingMethod {
        GRPC,
        GRAPHQL,
    }
}
