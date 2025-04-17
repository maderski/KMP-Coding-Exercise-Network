package com.maderskitech.kmpcodingexercisenetwork.platform.di

import com.maderskitech.kmpcodingexercisenetwork.platform.IOSNetworkClient
import com.maderskitech.kmpcodingexercisenetwork.platform.NetworkClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule: Module = module {
    singleOf(::IOSNetworkClient) bind NetworkClient::class
}