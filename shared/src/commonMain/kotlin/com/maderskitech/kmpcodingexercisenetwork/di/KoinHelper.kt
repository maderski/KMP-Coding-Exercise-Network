package com.maderskitech.kmpcodingexercisenetwork.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    appModules: List<Module> = emptyList(),
    config: KoinAppDeclaration? = null
) {
    startKoin {
        printLogger()
        config?.invoke(this) // optional custom setup
        modules(
            buildList {
                add(commonModule)
                addAll(appModules)
            }
        )
    }
}