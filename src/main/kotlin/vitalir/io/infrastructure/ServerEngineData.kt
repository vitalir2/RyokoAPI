package vitalir.io.infrastructure

import io.ktor.server.engine.*

class ServerEngineData<TEngine : ApplicationEngine, TConfiguration : ApplicationEngine.Configuration>(
    val factory: ApplicationEngineFactory<TEngine, TConfiguration>,
    val configure: TConfiguration.() -> Unit = {},
)
