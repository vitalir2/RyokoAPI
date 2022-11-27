package vitalir.io.feature.hotels.application

import com.google.type.TimeOfDay
import vitalir.io.common.Common.DayDuration
import vitalir.io.common.domain.money.Money
import vitalir.io.common.domain.time.Duration
import vitalir.io.common.domain.time.Time
import vitalir.io.feature.hotels.domain.Hotel
import vitalir.io.hotels.HotelKt
import vitalir.io.hotels.Hotels
import vitalir.io.hotels.Hotels.Hotel.HouseRules.ChildPolicy
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
        return TODO("Not implemented yet")
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
            houseRules.childPolicy?.let {
                childPolicy = ChildPolicy.newBuilder().setContent(it.content).build()
            }
            allowance = toApiModel(houseRules.allowance)
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
    }
}
