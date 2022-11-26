package vitalir.io.common.infrastructure

data class AppConfig(
    val port: Int,
    val host: String,
    val networkApiType: NetworkApiType,
) {

    enum class NetworkApiType {
        GRPC,
        GRAPHQL,
    }
}
