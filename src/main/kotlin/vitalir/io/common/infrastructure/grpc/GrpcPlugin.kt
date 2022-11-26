package vitalir.io.common.infrastructure.grpc

import io.grpc.BindableService
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService
import io.ktor.server.application.*
import io.ktor.server.application.hooks.*
import java.util.concurrent.atomic.AtomicReference
import kotlinx.coroutines.launch

val gRPC = createApplicationPlugin("gRPC", ::GrpcPluginConfig) {
    val server: AtomicReference<Server?> = AtomicReference()

    application.launch {
        val serverBuilder = ServerBuilder.forPort(pluginConfig.port)
        for (service in pluginConfig.services) {
            serverBuilder.addService(service)
        }
        if (pluginConfig.withReflection) {
            serverBuilder.addService(ProtoReflectionService.newInstance())
        }
        val localServer = serverBuilder.build()
        server.set(localServer)
        localServer.start()
        application.log.info("gRPC server started")
        localServer.awaitTermination()
    }

    on(MonitoringEvent(ApplicationStopped)) {
        server.get()?.shutdown()
        application.log.info("gRPC server stopped")
    }
}

class GrpcPluginConfig(
    var port: Int = 50551,
    var services: List<BindableService> = emptyList(),
    var withReflection: Boolean = false,
)
