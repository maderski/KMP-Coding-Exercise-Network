package com.maderskitech.kmpcodingexercisenetwork.dependencies

import io.ktor.client.HttpClient

interface NetworkClient {
    val httpClient: HttpClient
}