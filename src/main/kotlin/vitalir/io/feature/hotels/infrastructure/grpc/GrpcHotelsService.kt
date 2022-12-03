package vitalir.io.feature.hotels.infrastructure.grpc

import com.google.protobuf.Empty
import io.grpc.Status
import io.grpc.StatusException
import vitalir.io.feature.hotels.infrastructure.ApiHotelMapper
import vitalir.io.feature.hotels.domain.Hotel
import vitalir.io.feature.hotels.domain.HotelsRepository
import vitalir.io.hotels.Hotels
import vitalir.io.hotels.HotelsServiceGrpcKt
import vitalir.io.hotels.HotelsSpec
import vitalir.io.hotels.getHotelResponse

internal class GrpcHotelsService(
    private val hotelsRepository: HotelsRepository,
    private val apiHotelMapper: ApiHotelMapper<Hotels.Hotel>,
) : HotelsServiceGrpcKt.HotelsServiceCoroutineImplBase() {
    override suspend fun getHotel(request: HotelsSpec.GetHotelRequest): HotelsSpec.GetHotelResponse {
        val hotelId = Hotel.Id(request.id)
        val domainHotel = hotelsRepository.getById(hotelId) ?: throw StatusException(Status.NOT_FOUND)
        val apiHotel = apiHotelMapper.toApiModel(domainHotel)
        return getHotelResponse {
            hotel = apiHotel
        }
    }

    override suspend fun addHotel(request: HotelsSpec.AddHotelRequest): Empty {
        val hotelToAdd = request.hotel
        val domainHotel = apiHotelMapper.toDomainModel(hotelToAdd)
        hotelsRepository.add(domainHotel)
        return Empty.getDefaultInstance()
    }
}
