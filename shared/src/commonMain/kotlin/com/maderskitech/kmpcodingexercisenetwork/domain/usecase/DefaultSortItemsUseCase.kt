package com.maderskitech.kmpcodingexercisenetwork.domain.usecase

import com.maderskitech.kmpcodingexercisenetwork.domain.model.Item
import com.maderskitech.kmpcodingexercisenetwork.domain.respository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultSortItemsUseCase(private val repository: ItemRepository) : SortedItemsUseCase {
    override fun getSortedItemsFlow(): Flow<Result<Map<Int, List<Item>>>> = flow {
        repository.getAllItems()
            .onSuccess { items ->
                val sortedItemsMap = items
                    .sortedWith(compareBy({ it.listId }, { it.name }))
                    .groupBy { it.listId }
                emit(Result.success(sortedItemsMap))
            }.onFailure { throwable ->
                emit(Result.failure(throwable))
            }
    }
}