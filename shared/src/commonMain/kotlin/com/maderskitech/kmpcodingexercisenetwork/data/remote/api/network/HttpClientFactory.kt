package com.maderskitech.kmpcodingexercisenetwork.data.remote.api.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(engine: HttpClientEngine): HttpClient = HttpClient(engine) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(
                json = Json {
                    isLenient = true

                    /*
                    If the API responds with client fields that our client doesn't know about
                    because we didn't define it in our model, don't crash the app and ignore these
                    fields.
                     */
                    ignoreUnknownKeys = true
                }
            )
        }
    }
}