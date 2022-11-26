package vitalir.io.feature.hotels.domain

interface HotelsRepository {

    suspend fun getHotelById(hotelId: Hotel.Id): Hotel?
}
