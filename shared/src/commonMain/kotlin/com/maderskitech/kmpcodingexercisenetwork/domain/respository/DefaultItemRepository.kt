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
    override suspend fun getAllItems(): Result<List<Item>> = withContext(Dispatchers.IO) {
        val response = api.getItems()
        return@withContext when (response) {
            is Response.Success -> {
                val items = mapper.toItemList(response.data)
                if (!items.isNullOrEmpty()) {
                    Result.success(items)
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
}