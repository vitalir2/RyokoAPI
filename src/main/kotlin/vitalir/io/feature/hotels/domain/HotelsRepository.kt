package vitalir.io.feature.hotels.domain

interface HotelsRepository {

    suspend fun getById(id: Hotel.Id): Hotel?

    suspend fun add(hotel: Hotel)
}
