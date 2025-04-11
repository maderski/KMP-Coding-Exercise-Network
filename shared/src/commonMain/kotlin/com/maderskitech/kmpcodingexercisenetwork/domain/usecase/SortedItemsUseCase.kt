package com.maderskitech.kmpcodingexercisenetwork.domain.usecase

import com.maderskitech.kmpcodingexercisenetwork.domain.model.Item
import kotlinx.coroutines.flow.Flow

/**
 * Items are grouped by listId, then the associated items are sorted by name.  And
 * groups are sorted by listId.
 */
interface SortedItemsUseCase {
    fun getSortedItemsFlow(): Flow<Result<Map<Int, List<Item>>>>
}