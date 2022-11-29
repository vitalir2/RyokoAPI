package vitalir.io.feature.hotels.infrastructure.graphql

import vitalir.io.common.infrastructure.graphql.toGraphQLModel
import vitalir.io.feature.hotels.application.ApiHotelMapper
import vitalir.io.feature.hotels.domain.Hotel

internal class GraphQLHotelMapper : ApiHotelMapper<GraphQLHotel> {

    override fun toApiModel(domainModel: Hotel): GraphQLHotel {
        return domainModel.toGraphQLModel()
    }

    override fun toDomainModel(apiModel: GraphQLHotel): Hotel {
        TODO("Not yet implemented")
    }
}

private fun Hotel.toGraphQLModel(): GraphQLHotel {
    return GraphQLHotel(
        id = id.value,
        commonInfo = commonInfo.toGraphQLModel(),
        price = priceInfo.price.toGraphQLModel(),
        houseRules = houseRules.toGraphQLModel(),
    )
}

private fun Hotel.CommonInfo.toGraphQLModel(): GraphQLHotel.CommonInfo {
    return GraphQLHotel.CommonInfo(
        title = title,
        description = description,
        location = location.toGraphQLModel(),
        tags = tags.map(Hotel.CommonInfo.Tag::toGraphQLModel),
        photos = photos.map(Hotel.CommonInfo.Photo::toGraphQLModel),
        facilities = facilities.map(Hotel.CommonInfo.Facility::toGraphQLModel),
    )
}

private fun Hotel.CommonInfo.Location.toGraphQLModel(): GraphQLHotel.CommonInfo.Location {
    return GraphQLHotel.CommonInfo.Location(
        name = name,
    )
}

private fun Hotel.CommonInfo.Tag.toGraphQLModel(): GraphQLHotel.CommonInfo.Tag {
    return GraphQLHotel.CommonInfo.Tag(
        text = text,
        description = description,
    )
}

private fun Hotel.CommonInfo.Photo.toGraphQLModel(): GraphQLHotel.CommonInfo.Photo {
    return GraphQLHotel.CommonInfo.Photo(
        url = link.value,
    )
}

private fun Hotel.CommonInfo.Facility.toGraphQLModel(): GraphQLHotel.CommonInfo.Facility {
    return GraphQLHotel.CommonInfo.Facility(
        name = name,
        iconUrl = icon.link.value,
    )
}

private fun Hotel.HouseRules.toGraphQLModel(): GraphQLHotel.HouseRules {
    return GraphQLHotel.HouseRules(
        checkIn = checkIn,
        checkOut = checkOut,
        childPolicy = childPolicy?.toGraphQLModel(),
        allowance = allowance.toGraphQLModel(),
    )
}

private fun Hotel.HouseRules.ChildPolicy.toGraphQLModel(): GraphQLHotel.HouseRules.ChildPolicy {
    return GraphQLHotel.HouseRules.ChildPolicy(
        content = content,
    )
}

private fun Hotel.HouseRules.Allowance.toGraphQLModel(): GraphQLHotel.HouseRules.Allowance {
    return GraphQLHotel.HouseRules.Allowance(
        smoking = smoking,
        parties = parties,
        pets = pets,
    )
}
