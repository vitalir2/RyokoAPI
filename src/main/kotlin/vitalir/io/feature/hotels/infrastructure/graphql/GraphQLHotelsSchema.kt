package vitalir.io.feature.hotels.infrastructure.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.apurebase.kgraphql.schema.model.InputValueDef
import vitalir.io.feature.hotels.application.ApiHotelMapper
import vitalir.io.feature.hotels.domain.Hotel
import vitalir.io.feature.hotels.domain.HotelsRepository

internal fun SchemaBuilder.hotelsSchema(
    hotelsRepository: HotelsRepository,
    apiHotelMapper: ApiHotelMapper<GraphQLHotel>,
) {
    type(GraphQLHotel::class) {
        name = "Hotel"
    }
    query("GetHotel") {
        addInputValues(
            listOf(
                InputValueDef(Long::class, "id"),
            )
        )
        resolver { id: Long ->
            hotelsRepository
                .getById(Hotel.Id(id))
                ?.let(apiHotelMapper::toApiModel)
        }
    }
}
