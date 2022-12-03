package vitalir.io.feature.hotels.infrastructure.graphql

import vitalir.io.common.domain.Link
import vitalir.io.common.infrastructure.graphql.toDomainModel
import vitalir.io.common.infrastructure.graphql.toGraphQLModel
import vitalir.io.feature.hotels.domain.Hotel
import vitalir.io.feature.hotels.infrastructure.ApiHotelMapper

internal class GraphQLHotelMapper : ApiHotelMapper<GraphQLHotel> {

    override fun toApiModel(domainModel: Hotel): GraphQLHotel {
        return domainModel.toGraphQLModel()
    }

    override fun toDomainModel(apiModel: GraphQLHotel): Hotel {
        return apiModel.toDomainModel()
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
        checkIn = checkIn.toGraphQLModel(),
        checkOut = checkOut.toGraphQLModel(),
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

private fun GraphQLHotel.toDomainModel(): Hotel {
    return Hotel(
        id = Hotel.Id(id),
        commonInfo = commonInfo.toDomainModel(),
        priceInfo = toHotelPriceInfo(),
        houseRules = houseRules.toDomainModel(),
    )
}

private fun GraphQLHotel.CommonInfo.toDomainModel(): Hotel.CommonInfo {
    return Hotel.CommonInfo(
        title = title,
        location = location.toDomainModel(),
        tags = tags.map(GraphQLHotel.CommonInfo.Tag::toDomainModel),
        photos = photos.map(GraphQLHotel.CommonInfo.Photo::toDomainModel),
        facilities = facilities.map(GraphQLHotel.CommonInfo.Facility::toDomainModel),
        description = description,
    )
}

private fun GraphQLHotel.CommonInfo.Location.toDomainModel(): Hotel.CommonInfo.Location {
    return Hotel.CommonInfo.Location(
        name = name,
    )
}

private fun GraphQLHotel.CommonInfo.Tag.toDomainModel(): Hotel.CommonInfo.Tag {
    return Hotel.CommonInfo.Tag(
        text = text,
        description = description,
    )
}

private fun GraphQLHotel.CommonInfo.Photo.toDomainModel(): Hotel.CommonInfo.Photo {
    return Hotel.CommonInfo.Photo(
        link = Link(url),
    )
}

private fun GraphQLHotel.CommonInfo.Facility.toDomainModel(): Hotel.CommonInfo.Facility {
    return Hotel.CommonInfo.Facility(
        name = name,
        icon = Hotel.CommonInfo.Facility.Icon(Link(iconUrl)),
    )
}

private fun GraphQLHotel.toHotelPriceInfo(): Hotel.PriceInfo {
    return Hotel.PriceInfo(
        price = price.toDomainModel(),
    )
}

private fun GraphQLHotel.HouseRules.toDomainModel(): Hotel.HouseRules {
    return Hotel.HouseRules(
        checkIn = checkIn.toDomainModel(),
        checkOut = checkOut.toDomainModel(),
        childPolicy = childPolicy?.toDomainModel(),
        allowance = allowance.toDomainModel(),
    )
}

private fun GraphQLHotel.HouseRules.ChildPolicy.toDomainModel(): Hotel.HouseRules.ChildPolicy {
    return Hotel.HouseRules.ChildPolicy(
        content = content,
    )
}

private fun GraphQLHotel.HouseRules.Allowance.toDomainModel(): Hotel.HouseRules.Allowance {
    return Hotel.HouseRules.Allowance(
        smoking = smoking,
        parties = parties,
        pets = pets,
    )
}
