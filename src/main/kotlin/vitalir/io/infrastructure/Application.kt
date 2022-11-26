package vitalir.io.infrastructure

import io.ktor.server.application.*
import io.ktor.server.engine.*
import vitalir.io.infrastructure.grpc.GrpcNetty
import vitalir.io.infrastructure.grpc.GrpcHotelsService
import vitalir.io.infrastructure.routing.configureRouting

fun main() {
    embeddedServer(
        factory = GrpcNetty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module,
        configure = {
            services = listOf(
                GrpcHotelsService(),
            )
            withReflection = true
        }
    )
        .start(wait = true)
}

fun Application.module() {
    val appConfig = AppConfig(
        routingMethod = AppConfig.RoutingMethod.GRPC,
    )
    configureSerialization()
    configureRouting(appConfig)
}
