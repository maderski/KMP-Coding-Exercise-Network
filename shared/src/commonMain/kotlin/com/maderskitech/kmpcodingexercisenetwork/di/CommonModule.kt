package com.maderskitech.kmpcodingexercisenetwork.di

import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.ItemsApi
import com.maderskitech.kmpcodingexercisenetwork.domain.mapper.ItemListMapper
import com.maderskitech.kmpcodingexercisenetwork.domain.respository.DefaultItemRepository
import com.maderskitech.kmpcodingexercisenetwork.domain.respository.ItemRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val commonModule = module {
    // Data - Remote
    singleOf(::ItemsApi)

    // Domain
    factoryOf(::ItemListMapper)
    singleOf(::DefaultItemRepository) bind ItemRepository::class

    includes(platformModule)
}