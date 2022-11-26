package vitalir.io.feature.hotels.infrastructure

import vitalir.io.common.domain.money.Currency
import vitalir.io.common.domain.money.Money
import vitalir.io.common.domain.time.Duration
import vitalir.io.common.infrastructure.DefaultTime
import vitalir.io.feature.hotels.domain.Hotel
import vitalir.io.feature.hotels.domain.HotelsRepository

internal class StubHotelsRepository : HotelsRepository {
    override suspend fun getHotelById(hotelId: Hotel.Id): Hotel {
        return Hotel(
            id = hotelId,
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
                checkInTime = Duration(from = DefaultTime(hours = 10), to = DefaultTime(hours = 14)),
                checkOutTime = Duration(from = DefaultTime(hours = 16), to = DefaultTime(hours = 19)),
            ),
        )
    }
}
