package vitalir.io.common.infrastructure.grpc

import vitalir.io.hotels.HotelsServiceGrpcKt
import vitalir.io.hotels.HotelsSpec
import vitalir.io.hotels.getHotelResponse
import vitalir.io.hotels.hotel

class GrpcHotelsService : HotelsServiceGrpcKt.HotelsServiceCoroutineImplBase() {
    override suspend fun getHotel(request: HotelsSpec.GetHotelRequest): HotelsSpec.GetHotelResponse {
        val hotel = hotel {
            id = 1
        }
        return getHotelResponse {
            this.hotel = hotel
        }
    }
}
