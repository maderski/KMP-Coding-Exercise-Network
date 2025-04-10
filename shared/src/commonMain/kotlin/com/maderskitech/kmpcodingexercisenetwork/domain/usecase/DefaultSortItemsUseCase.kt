package com.maderskitech.kmpcodingexercisenetwork.domain.usecase

import com.maderskitech.kmpcodingexercisenetwork.domain.model.Item
import com.maderskitech.kmpcodingexercisenetwork.domain.respository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultSortItemsUseCase(private val repository: ItemRepository) : SortedItemsUseCase {
    override fun getSortedItemsFlow(): Flow<Result<Map<Int, List<Item>>>> = flow {
        repository.getAllItems()
            .onSuccess { items ->
                /*
                I suspected the Kotlin StdLib had to have the functionality to sort the results the
                way I wanted.  Since I am not supposed to use AI, I searched stack
                overflow and found an answer to get me heading in the right direction and made some
                modifications and wrote a test to verify.

                link: https://stackoverflow.com/questions/61021088/kotlin-sort-list-of-objects-by-their-id-and-parentid
                 */
                val sortedItemsMap = items
                    .sortedWith(compareBy({ it.listId }, { it.name.length }, { it.name })) // Sorts the list of items by ListId followed by the name length, and finally by the name.
                    .groupBy { it.listId } // Groups the items by listId, thus creating a map with the listId as the key and a list of items with the same listId as the value.
                emit(Result.success(sortedItemsMap))
            }.onFailure { throwable ->
                emit(Result.failure(throwable))
            }
    }
}