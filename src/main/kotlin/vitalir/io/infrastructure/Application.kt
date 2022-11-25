package vitalir.io.infrastructure

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import vitalir.io.infrastructure.routing.configureRouting

fun main() {
    embeddedServer(
        factory = Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
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
