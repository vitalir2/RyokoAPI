package vitalir.io.common.infrastructure

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import vitalir.io.common.infrastructure.grpc.GrpcHotelsService
import vitalir.io.common.infrastructure.grpc.GrpcNetty
import vitalir.io.common.infrastructure.routing.configureRouting

val appConfig = AppConfig(
    port = 8080,
    host = "0.0.0.0",
    networkApiType = AppConfig.NetworkApiType.GRPC,
)

fun main() {
    embeddedServer(appConfig)
}

fun embeddedServer(appConfig: AppConfig) {
    val serverEngineData = when (appConfig.networkApiType) {
        AppConfig.NetworkApiType.GRPC -> ServerEngineData(GrpcNetty) {
            // TODO build services from custom Routing plugin
            services = listOf(
                GrpcHotelsService(),
            )
            withReflection = true
        }
        AppConfig.NetworkApiType.GRAPHQL -> ServerEngineData(Netty)
    }
    embeddedServer(serverEngineData, appConfig.port, appConfig.host)
}

fun <TEngine : ApplicationEngine, TConfiguration : ApplicationEngine.Configuration> embeddedServer(
    serverEngineData: ServerEngineData<TEngine, TConfiguration>,
    port: Int,
    host: String,
) {
    embeddedServer(
        factory = serverEngineData.factory,
        port = port,
        host = host,
        module = Application::module,
        configure = serverEngineData.configure,
    ).start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting(appConfig)
}
