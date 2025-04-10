package com.maderskitech.kmpcodingexercisenetwork.dependencies

import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.network.HttpClientFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

class AndroidNetworkClient : NetworkClient {
    override val httpClient: HttpClient = HttpClientFactory.create(OkHttp.create())
}

actual fun getNetworkClient(): NetworkClient = AndroidNetworkClient()