package com.maderskitech.kmpcodingexercisenetwork.di

import com.maderskitech.kmpcodingexercisenetwork.domain.respository.ItemRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import kotlin.jvm.JvmStatic

class KoinHelper : KoinComponent {
    private val itemRepository: ItemRepository  by inject()

    fun getItemRepository(): ItemRepository = itemRepository

    companion object {
        @JvmStatic
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
    }
}