package vitalir.io.feature.hotels.infrastructure

import vitalir.io.feature.hotels.domain.Hotel

interface ApiHotelMapper<TApiModel : Any> {

    fun toApiModel(domainModel: Hotel): TApiModel

    fun toDomainModel(apiModel: TApiModel): Hotel
}
