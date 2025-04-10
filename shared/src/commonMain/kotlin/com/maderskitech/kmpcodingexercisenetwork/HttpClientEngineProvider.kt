package com.maderskitech.kmpcodingexercisenetwork

import io.ktor.client.engine.HttpClientEngine

interface HttpClientEngineProvider {
    val engine: HttpClientEngine
}

expect fun getHttpClientEngine(): HttpClientEngine