package com.maderskitech.kmpcodingexercisenetwork.domain.usecase

import com.maderskitech.kmpcodingexercisenetwork.domain.model.Item
import kotlinx.coroutines.flow.Flow

/**
 * Sorts the results first by "listId" then by "name"
 */
interface SortedItemsUseCase {
    fun getSortedItemsFlow(): Flow<Result<Map<Int, List<Item>>>>
}