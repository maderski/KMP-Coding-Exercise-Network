package com.maderskitech.kmpcodingexercisenetwork.domain.respository

import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.ItemsApi
import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.network.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import com.maderskitech.kmpcodingexercisenetwork.domain.mapper.ItemListMapper
import com.maderskitech.kmpcodingexercisenetwork.domain.model.Item

class DefaultItemRepository(
    private val api: ItemsApi,
    private val mapper: ItemListMapper
) : ItemRepository {
    override suspend fun getAllItems(): Result<Map<Int, List<Item>>> = withContext(Dispatchers.IO) {
        val response = api.getItems()
        return@withContext when (response) {
            is Response.Success -> {
                val items = mapper.toItemList(response.data)
                if (!items.isNullOrEmpty()) {
                    Result.success(items.toSortedItemGroups())
                } else {
                    val failureType = if (items == null) {
                        "NULL"
                    } else {
                        "Empty"
                    }
                    Result.failure(IllegalStateException("Items are $failureType"))
                }
            }

            is Response.Failure -> Result.failure(IllegalStateException("Error Occurred: ${response.error}"))
        }
    }

    /*
        I suspected the Kotlin StdLib had to have the functionality to sort the results the
        way I wanted.  Since I am not supposed to use AI, I searched stack
        overflow and found an answer to get me heading in the right direction and made some
        modifications and wrote a test to verify.

        link: https://stackoverflow.com/questions/61021088/kotlin-sort-list-of-objects-by-their-id-and-parentid
    */
    private fun List<Item>.toSortedItemGroups() = this
        .sortedWith(
            compareBy(
                { it.listId },
                { it.name.length },
                { it.name })
        ) // Sorts the list of items by ListId followed by the name length, and finally by the name.
        .groupBy { it.listId } // Groups the items by listId, thus creating a map with the listId as the key and a list of items with the same listId as the value.
}