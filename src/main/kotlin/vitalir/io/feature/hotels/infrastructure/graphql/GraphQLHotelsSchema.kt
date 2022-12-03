package vitalir.io.feature.hotels.infrastructure.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import vitalir.io.common.infrastructure.graphql.GraphQLDayDuration
import vitalir.io.common.infrastructure.graphql.GraphQLMoney
import vitalir.io.common.infrastructure.graphql.GraphQLTimeOfDay
import vitalir.io.common.infrastructure.graphql.inputType
import vitalir.io.common.infrastructure.graphql.type
import vitalir.io.feature.hotels.domain.Hotel
import vitalir.io.feature.hotels.domain.HotelsRepository
import vitalir.io.feature.hotels.infrastructure.ApiHotelMapper

internal fun SchemaBuilder.hotelsSchema(
    hotelsRepository: HotelsRepository,
    apiHotelMapper: ApiHotelMapper<GraphQLHotel>,
) {
    type<GraphQLHotel>("Hotel")
    inputType<GraphQLHotelInput>("Hotel")
    hotelInputTypes()
    inputType<GraphQLMoney>("Money")
    inputType<GraphQLDayDuration>("DayDuration")
    inputType<GraphQLTimeOfDay>("TimeOfDay")
    query("GetHotel") {
        resolver { id: Long ->
            hotelsRepository
                .getById(Hotel.Id(id))
                ?.let(apiHotelMapper::toApiModel)
        }
    }

    mutation("AddHotel") {
        resolver { hotel: GraphQLHotelInput ->
            val domainHotel = apiHotelMapper.toDomainModel(hotel)
            hotelsRepository.add(domainHotel)
            apiHotelMapper.toApiModel(domainHotel)
        }
    }
}

private inline fun <reified T : Any> SchemaBuilder.hotelInputType(name: String) {
    inputType<T>("Hotel$name")
}

private fun SchemaBuilder.hotelInputTypes() {
    hotelInputType<GraphQLHotel.CommonInfo>("CommonInfo")
    hotelInputType<GraphQLHotel.HouseRules>("HouseRules")
    hotelInputType<GraphQLHotel.CommonInfo.Tag>("Tag")
    hotelInputType<GraphQLHotel.CommonInfo.Photo>("Photo")
    hotelInputType<GraphQLHotel.CommonInfo.Facility>("Facility")
    hotelInputType<GraphQLHotel.CommonInfo.Location>("Location")
    hotelInputType<GraphQLHotel.HouseRules.Allowance>("Allowance")
    hotelInputType<GraphQLHotel.HouseRules.ChildPolicy>("ChildPolicy")
}
