package vitalir.io.common.infrastructure.graphql

data class GraphQLDayDuration(
    val from: GraphQLTimeOfDay,
    val to: GraphQLTimeOfDay,
)
