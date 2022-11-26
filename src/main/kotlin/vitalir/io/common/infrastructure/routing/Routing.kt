package vitalir.io.common.infrastructure.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import vitalir.io.common.infrastructure.AppConfig
import vitalir.io.common.infrastructure.grpc.gRPC
import vitalir.io.feature.hotels.application.GrpcHotelMapper
import vitalir.io.feature.hotels.infrastructure.GrpcHotelsService
import vitalir.io.feature.hotels.infrastructure.StubHotelsRepository

fun Application.configureRouting(appConfig: AppConfig) {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    when (appConfig.networkApiType) {
        AppConfig.NetworkApiType.GRPC -> {
            install(gRPC) {
                withReflection = true
                services = listOf(
                    GrpcHotelsService(
                        hotelsRepository = StubHotelsRepository(),
                        apiHotelMapper = GrpcHotelMapper(),
                    )
                )
            }
        }
        AppConfig.NetworkApiType.GRAPHQL -> {
            // TODO
        }
    }
}
