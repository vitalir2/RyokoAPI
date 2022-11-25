package vitalir.io.infrastructure.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import vitalir.io.infrastructure.AppConfig

fun Application.configureRouting(appConfig: AppConfig) {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        when (appConfig.routingMethod) {
            AppConfig.RoutingMethod.GRPC -> gRPCRouting()
            AppConfig.RoutingMethod.GRAPHQL -> graphQLRouting()
        }
    }
}

private fun Routing.gRPCRouting() {
    // TODO
}

private fun Routing.graphQLRouting() {
    // TODO
}
