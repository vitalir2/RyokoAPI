package vitalir.io.common.infrastructure.graphql

data class GraphQLMoney(
    val currencyCode: String,
    val units: Long,
)
