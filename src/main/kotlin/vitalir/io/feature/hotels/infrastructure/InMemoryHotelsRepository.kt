package vitalir.io.feature.hotels.infrastructure

import vitalir.io.feature.hotels.domain.Hotel
import vitalir.io.feature.hotels.domain.HotelsRepository

internal class InMemoryHotelsRepository(
    initStorage: MutableList<Hotel>.() -> Unit,
) : HotelsRepository {

    private val hotelsStorage = mutableListOf<Hotel>()

    init {
        hotelsStorage.initStorage()
    }

    override suspend fun getById(id: Hotel.Id): Hotel? {
        return hotelsStorage.firstOrNull { it.id == id }
    }

    override suspend fun add(hotel: Hotel) {
        hotelsStorage.add(hotel)
    }
}
