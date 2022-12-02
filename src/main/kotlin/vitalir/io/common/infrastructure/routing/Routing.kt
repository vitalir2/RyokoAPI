package vitalir.io.common.infrastructure.routing

import com.apurebase.kgraphql.GraphQL
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import vitalir.io.common.infrastructure.AppConfig
import vitalir.io.common.infrastructure.grpc.gRPC
import vitalir.io.feature.hotels.infrastructure.grpc.GrpcHotelMapper
import vitalir.io.feature.hotels.domain.Hotel
import vitalir.io.feature.hotels.infrastructure.grpc.GrpcHotelsService
import vitalir.io.feature.hotels.infrastructure.InMemoryHotelsRepository
import vitalir.io.feature.hotels.infrastructure.graphql.GraphQLHotelMapper
import vitalir.io.feature.hotels.infrastructure.graphql.hotelsSchema
import vitalir.io.feature.hotels.infrastructure.sample

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
                        hotelsRepository = InMemoryHotelsRepository {
                            add(Hotel.sample(123))
                        },
                        apiHotelMapper = GrpcHotelMapper(),
                    )
                )
            }
        }
        AppConfig.NetworkApiType.GRAPHQL -> {
            install(GraphQL) {
                playground = true
                endpoint = "/graphql"
                schema {
                    hotelsSchema(
                        hotelsRepository = InMemoryHotelsRepository {
                            add(Hotel.sample(123))
                        },
                        apiHotelMapper = GraphQLHotelMapper(),
                    )
                }
            }
        }
    }
}
