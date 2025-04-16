package com.maderskitech.kmpcodingexercisenetwork.platform

import io.ktor.client.HttpClient

interface NetworkClient {
    val httpClient: HttpClient
}