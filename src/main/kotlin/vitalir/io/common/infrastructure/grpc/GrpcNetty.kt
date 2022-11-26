package vitalir.io.common.infrastructure.grpc

import io.grpc.BindableService
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService
import io.ktor.server.application.*
import io.ktor.server.engine.*

/**
 * An [ApplicationEngineFactory] for providing Netty-based gRPC [ApplicationEngine]
 */
object GrpcNetty : ApplicationEngineFactory<GrpcNettyApplicationEngine, GrpcNettyApplicationEngine.Configuration> {

    override fun create(
        environment: ApplicationEngineEnvironment,
        configure: GrpcNettyApplicationEngine.Configuration.() -> Unit,
    ): GrpcNettyApplicationEngine {
        return GrpcNettyApplicationEngine(environment, configure)
    }
}

/**
 * Simple implementation of [ApplicationEngine] serving gRPC using grpc-netty.
 *
 *  DO NOT USE IN PRODUCTION
 */
class GrpcNettyApplicationEngine(
    environment: ApplicationEngineEnvironment,
    configure: Configuration.() -> Unit = {},
) : BaseApplicationEngine(environment) {

    class Configuration : BaseApplicationEngine.Configuration() {
        var port: Int? = null

        var services: List<BindableService> = emptyList()

        var withReflection: Boolean = false
    }

    private val configuration: Configuration by lazy {
        Configuration().apply(configure)
    }

    private val server: Server by lazy {
        val serverPort = configuration.port ?: application.environment.config.port
        val builder = ServerBuilder.forPort(serverPort)
        for (service in configuration.services) {
            builder.addService(service)
        }
        if (configuration.withReflection) {
            builder.addService(ProtoReflectionService.newInstance())
        }
        builder.build()
    }

    override fun start(wait: Boolean): ApplicationEngine {
        environment.start()
        server.start()
        if (wait) {
            server.awaitTermination()
        }
        return this
    }

    override fun stop(gracePeriodMillis: Long, timeoutMillis: Long) {
        server.shutdown()
    }
}
