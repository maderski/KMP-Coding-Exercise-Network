package com.maderskitech.kmpcodingexercisenetwork.di

import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.ItemsApi
import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.network.HttpClientFactory
import com.maderskitech.kmpcodingexercisenetwork.domain.mapper.ItemListMapper
import com.maderskitech.kmpcodingexercisenetwork.domain.respository.DefaultItemRepository
import com.maderskitech.kmpcodingexercisenetwork.domain.respository.ItemRepository
import com.maderskitech.kmpcodingexercisenetwork.domain.usecase.DefaultSortItemsUseCase
import com.maderskitech.kmpcodingexercisenetwork.domain.usecase.SortedItemsUseCase
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    // Data - Remote
    single<HttpClient> { HttpClientFactory.create(get()) }
    singleOf(::ItemsApi)

    // Domain
    factoryOf(::ItemListMapper)
    singleOf(::DefaultItemRepository) bind ItemRepository::class
    singleOf(::DefaultSortItemsUseCase) bind SortedItemsUseCase::class
}