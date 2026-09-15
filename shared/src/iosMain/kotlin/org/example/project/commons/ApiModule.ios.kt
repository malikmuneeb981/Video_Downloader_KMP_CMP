package org.example.project.commons

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

class ProvideApiModule() : ApiModule{
    override val module: Module = module {
        single<HttpClient> {
            HttpClient(Darwin) {
                install(ContentNegotiation){
                    json(Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    })
                }
                install(Logging){
                    level = LogLevel.ALL
                    logger = object : Logger {
                        override fun log(message: String) {
                            println("KtorHttpClient, $message")
                        }
                    }
                }
                install(HttpTimeout) {
                    requestTimeoutMillis = 60_000
                    connectTimeoutMillis = 60_000
                    socketTimeoutMillis = 60_000
                }
            }
        }
    }
}
actual fun provideApiModule(): Module {
    return ProvideApiModule().module
}