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
    value class Id(val value: Long)

    data class CommonInfo(
        val title: String,
        val location: Location,
        val tags: List<Tag> = emptyList(),
        val photos: List<Photo> = emptyList(),
        val facilities: List<Facility> = emptyList(),
        val description: String = "",
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
        val checkIn: Duration,
        val checkOut: Duration,
        val allowance: Allowance = Allowance(),
        val childPolicy: ChildPolicy? = null,
    ) {

        data class ChildPolicy(
            val content: String,
        )

        data class Allowance(
            val smoking: Boolean = false,
            val parties: Boolean = false,
            val pets: Boolean = false,
        )
    }

    companion object
}
