package vitalir.io.feature.hotels.infrastructure.graphql

import vitalir.io.common.infrastructure.graphql.GraphQLMoney

data class GraphQLHotelInput(
    override val id: Long,
    override val commonInfo: CommonInfo,
    override val price: GraphQLMoney,
    override val houseRules: HouseRules,
) : GraphQLHotel(
    id = id,
    commonInfo = commonInfo,
    price = price,
    houseRules = houseRules,
)
