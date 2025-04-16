package com.maderskitech.kmpcodingexercisenetwork.di

import com.maderskitech.kmpcodingexercisenetwork.domain.respository.ItemRepositoryWrapper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

object KoinHelper : KoinComponent {
    private val itemRepositoryWrapper: ItemRepositoryWrapper by inject()

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

    fun getItemRepositoryWrapper(): ItemRepositoryWrapper = itemRepositoryWrapper
}