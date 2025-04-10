package com.maderskitech.kmpcodingexercisenetwork.dependencies

import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.network.HttpClientFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

class IOSNetworkClient : NetworkClient {
    override val httpClient: HttpClient = HttpClientFactory.create(Darwin.create())
}

actual fun getNetworkClient(): NetworkClient = IOSNetworkClient()