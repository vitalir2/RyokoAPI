package vitalir.io.common.infrastructure

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import vitalir.io.common.infrastructure.routing.configureRouting

val appConfig = AppConfig(
    port = 8080,
    host = "0.0.0.0",
    networkApiType = AppConfig.NetworkApiType.GRPC,
)

fun main() {
    embeddedServer(
        factory = Netty,
        port = appConfig.port,
        host = appConfig.host,
        module = Application::module,
    ).start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting(appConfig)
}
