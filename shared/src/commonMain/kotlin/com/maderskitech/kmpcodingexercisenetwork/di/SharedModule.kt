package com.maderskitech.kmpcodingexercisenetwork.di

import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.ItemsApi
import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.network.HttpClientFactory
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val sharedModule = module {
    single<HttpClient> { HttpClientFactory.create(get()) }
    singleOf(::ItemsApi)
}