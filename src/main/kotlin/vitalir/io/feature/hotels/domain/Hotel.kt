package vitalir.io.feature.hotels.domain

import vitalir.io.common.domain.Link
import vitalir.io.common.domain.money.Money
import vitalir.io.common.domain.time.Duration

data class Hotel(
    val id: Id,
    val commonInfo: CommonInfo,
    val priceInfo: PriceInfo,
    val houseRules: HouseRules,
) {

    @JvmInline
    value class Id(val id: Int)

    data class CommonInfo(
        val title: String,
        val tags: List<Tag>,
        val location: Location,
        val photos: List<Photo>,
        val facilities: List<Facility>,
        val description: String,
    ) {

        data class Tag(
            val text: String,
            val description: String,
        )

        data class Location(
            val name: String,
        )

        data class Photo(
            val link: Link,
        )

        data class Facility(
            val name: String,
            val icon: Icon,
        ) {

            @JvmInline
            value class Icon(val link: Link)
        }
    }

    data class PriceInfo(
        val price: Money,
    )

    data class HouseRules(
        val check: Duration,
        val checkOutTime: Duration,
        val childPolicy: ChildPolicy,
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
