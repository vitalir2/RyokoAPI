package vitalir.io.feature.hotels.infrastructure

import vitalir.io.common.domain.money.Currency
import vitalir.io.common.domain.money.Money
import vitalir.io.common.domain.time.Duration
import vitalir.io.common.infrastructure.DefaultTime
import vitalir.io.feature.hotels.domain.Hotel

internal fun Hotel.Companion.sample(id: Hotel.Id): Hotel {
    return Hotel(
        id = id,
        commonInfo = Hotel.CommonInfo(
            title = "Hello world!",
            location = Hotel.CommonInfo.Location(
                name = "Los Angeles",
            ),
        ),
        priceInfo = Hotel.PriceInfo(
            price = Money(
                currency = Currency.US_DOLLAR,
                amount = 10_000,
            )
        ),
        houseRules = Hotel.HouseRules(
            checkIn = Duration(from = DefaultTime(hours = 10), to = DefaultTime(hours = 14)),
            checkOut = Duration(from = DefaultTime(hours = 16), to = DefaultTime(hours = 19)),
        ),
    )
}
