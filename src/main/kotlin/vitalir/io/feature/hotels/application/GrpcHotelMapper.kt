package vitalir.io.feature.hotels.application

import vitalir.io.feature.hotels.domain.Hotel
import vitalir.io.hotels.Hotels
import vitalir.io.hotels.hotel

internal class GrpcHotelMapper : ApiHotelMapper<Hotels.Hotel> {

    override fun toApiModel(domainModel: Hotel): Hotels.Hotel {
        return hotel {
            id = domainModel.id.value
        }
    }

    override fun toDomainModel(apiModel: Hotels.Hotel): Hotel {
        return TODO("Not implemented yet")
    }
}
