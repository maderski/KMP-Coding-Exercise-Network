package com.maderskitech.kmpcodingexercisenetwork

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

object IOSHttpClientEngineProvider : HttpClientEngineProvider {
    override val engine: HttpClientEngine = Darwin.create()
}

actual fun getHttpClientEngine(): HttpClientEngine = IOSHttpClientEngineProvider.engine