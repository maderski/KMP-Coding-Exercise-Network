package com.maderskitech.kmpcodingexercisenetwork

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

object AndroidHttpClientEngineProvider : HttpClientEngineProvider {
    override val engine: HttpClientEngine = OkHttp.create()
}

actual fun getHttpClientEngine(): HttpClientEngine = AndroidHttpClientEngineProvider.engine