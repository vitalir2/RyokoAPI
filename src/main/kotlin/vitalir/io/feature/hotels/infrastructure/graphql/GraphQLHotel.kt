package vitalir.io.feature.hotels.infrastructure.graphql

import vitalir.io.common.domain.time.Duration
import vitalir.io.common.infrastructure.graphql.GraphQLMoney

data class GraphQLHotel(
    val id: Long,
    val commonInfo: CommonInfo,
    val price: GraphQLMoney,
    val houseRules: HouseRules,
) {

    data class CommonInfo(
        val title: String,
        val description: String,
        val location: Location,
        val tags: List<Tag>,
        val photos: List<Photo>,
        val facilities: List<Facility>,
    ) {

        data class Location(
            val name: String,
        )

        data class Tag(
            val text: String,
            val description: String,
        )

        data class Photo(
            val url: String,
        )

        data class Facility(
            val name: String,
            val iconUrl: String?,
        )
    }

    data class HouseRules(
        val checkIn: Duration,
        val checkOut: Duration,
        val childPolicy: ChildPolicy?,
        val allowance: Allowance,
    ) {

        data class ChildPolicy(
            val content: String,
        )

        data class Allowance(
            val smoking: Boolean,
            val parties: Boolean,
            val pets: Boolean,
        )
    }
}
