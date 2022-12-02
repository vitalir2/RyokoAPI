package vitalir.io.feature.hotels.infrastructure.grpc

import com.google.type.TimeOfDay
import vitalir.io.common.Common.DayDuration
import vitalir.io.common.domain.CountryId
import vitalir.io.common.domain.Link
import vitalir.io.common.domain.money.Currency
import vitalir.io.common.domain.money.Money
import vitalir.io.common.domain.time.Duration
import vitalir.io.common.domain.time.Time
import vitalir.io.common.infrastructure.DefaultTime
import vitalir.io.feature.hotels.application.ApiHotelMapper
import vitalir.io.feature.hotels.domain.Hotel
import vitalir.io.hotels.HotelKt
import vitalir.io.hotels.Hotels
import vitalir.io.hotels.childPolicyOrNull
import vitalir.io.hotels.hotel

internal class GrpcHotelMapper : ApiHotelMapper<Hotels.Hotel> {

    override fun toApiModel(domainModel: Hotel): Hotels.Hotel {
        return hotel {
            id = domainModel.id.value
            commonInfo = toApiModel(domainModel.commonInfo)
            price = domainModel.priceInfo.price.toApiModel()
            houseRules = toApiModel(domainModel.houseRules)
        }
    }

    override fun toDomainModel(apiModel: Hotels.Hotel): Hotel {
        return Hotel(
            id = Hotel.Id(apiModel.id),
            commonInfo = apiModel.commonInfo.toDomainModel(),
            priceInfo = apiModel.toPriceModel(),
            houseRules = apiModel.houseRules.toDomainModel(),
        )
    }

    private fun toApiModel(commonInfo: Hotel.CommonInfo): Hotels.Hotel.CommonInfo {
        return HotelKt.commonInfo {
            title = commonInfo.title
            location = toApiModel(commonInfo.location)
            tags.addAll(commonInfo.tags.map(::toApiModel))
            photos.addAll(commonInfo.photos.map(::toApiModel))
            facilities.addAll(commonInfo.facilities.map(::toApiModel))
            description = commonInfo.description
        }
    }

    private fun toApiModel(location: Hotel.CommonInfo.Location): Hotels.Hotel.CommonInfo.Location {
        return HotelKt.CommonInfoKt.location {
            name = location.name
        }
    }

    private fun toApiModel(tag: Hotel.CommonInfo.Tag): Hotels.Hotel.CommonInfo.Tag {
        return HotelKt.CommonInfoKt.tag {
            text = tag.text
            description = tag.description
        }
    }

    private fun toApiModel(photo: Hotel.CommonInfo.Photo): Hotels.Hotel.CommonInfo.Photo {
        return HotelKt.CommonInfoKt.photo {
            url = photo.link.value
        }
    }

    private fun toApiModel(facility: Hotel.CommonInfo.Facility): Hotels.Hotel.CommonInfo.Facility {
        return HotelKt.CommonInfoKt.facility {
            name = facility.name
            iconUrl = facility.icon.link.value
        }
    }

    private fun toApiModel(houseRules: Hotel.HouseRules): Hotels.Hotel.HouseRules {
        return HotelKt.houseRules {
            checkIn = houseRules.checkIn.toApiModel()
            checkOut = houseRules.checkOut.toApiModel()
            houseRules.childPolicy?.let { childPolicy = toApiModel(it) }
            allowance = toApiModel(houseRules.allowance)
        }
    }

    private fun toApiModel(childPolicy: Hotel.HouseRules.ChildPolicy): Hotels.Hotel.HouseRules.ChildPolicy {
        return HotelKt.HouseRulesKt.childPolicy {
            content = childPolicy.content
        }
    }

    private fun toApiModel(allowance: Hotel.HouseRules.Allowance): Hotels.Hotel.HouseRules.Allowance {
        return HotelKt.HouseRulesKt.allowance {
            smoking = allowance.smoking
            parties = allowance.parties
            pets = allowance.pets
        }
    }

    companion object {
        private fun Money.toApiModel(): com.google.type.Money {
            return com.google.type.Money.newBuilder()
                .setCurrencyCode(currency.currencyCode.code)
                .setUnits(amount)
                .build()
        }

        private fun Duration.toApiModel(): DayDuration {
            return DayDuration.newBuilder()
                .setStartTime(from.toApiModel())
                .setEndTime(to.toApiModel())
                .build()
        }

        private fun Time.toApiModel(): TimeOfDay {
            return TimeOfDay.newBuilder()
                .setHours(hours)
                .setMinutes(minutes)
                .setSeconds(seconds)
                .build()
        }

        private fun Hotels.Hotel.CommonInfo.toDomainModel(): Hotel.CommonInfo {
            return Hotel.CommonInfo(
                title = title,
                location = location.toDomainModel(),
                tags = tagsList.map { it.toDomainModel() },
                photos = photosList.map { it.toDomainModel() },
                facilities = facilitiesList.map { it.toDomainModel() },
                description = description,
            )
        }

        private fun Hotels.Hotel.CommonInfo.Location.toDomainModel(): Hotel.CommonInfo.Location {
            return Hotel.CommonInfo.Location(
                name = name,
            )
        }

        private fun Hotels.Hotel.CommonInfo.Tag.toDomainModel(): Hotel.CommonInfo.Tag {
            return Hotel.CommonInfo.Tag(
                text = text,
                description = description,
            )
        }

        private fun Hotels.Hotel.CommonInfo.Photo.toDomainModel(): Hotel.CommonInfo.Photo {
            return Hotel.CommonInfo.Photo(
                link = Link(url),
            )
        }

        private fun Hotels.Hotel.CommonInfo.Facility.toDomainModel(): Hotel.CommonInfo.Facility {
            return Hotel.CommonInfo.Facility(
                name = name,
                icon = Hotel.CommonInfo.Facility.Icon(Link(iconUrl)),
            )
        }

        private fun Hotels.Hotel.toPriceModel(): Hotel.PriceInfo {
            return Hotel.PriceInfo(
                price = price.toDomainModel(),
            )
        }

        private fun com.google.type.Money.toDomainModel(): Money {
            return Money(
                currency = Currency(
                    currencyCode = Currency.CurrencyCode(currencyCode),
                    countryId = CountryId.Code.US, // TODO Will be replaced by real one
                ),
                amount = units,
            )
        }

        private fun Hotels.Hotel.HouseRules.toDomainModel(): Hotel.HouseRules {
            return Hotel.HouseRules(
                checkIn = checkIn.toDomainModel(),
                checkOut = checkOut.toDomainModel(),
                childPolicy = childPolicyOrNull?.toDomainModel(),
                allowance = allowance.toDomainModel(),
            )
        }

        private fun DayDuration.toDomainModel(): Duration {
            return Duration(
                from = startTime.toDomainModel(),
                to = endTime.toDomainModel(),
            )
        }

        private fun TimeOfDay.toDomainModel(): Time {
            return DefaultTime(
                hours = hours,
                minutes = minutes,
                seconds = seconds,
            )
        }

        private fun Hotels.Hotel.HouseRules.ChildPolicy.toDomainModel(): Hotel.HouseRules.ChildPolicy {
            return Hotel.HouseRules.ChildPolicy(
                content = content,
            )
        }

        private fun Hotels.Hotel.HouseRules.Allowance.toDomainModel(): Hotel.HouseRules.Allowance {
            return Hotel.HouseRules.Allowance(
                smoking = smoking,
                parties = parties,
                pets = pets,
            )
        }
    }
}
