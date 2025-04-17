package com.maderskitech.kmpcodingexercisenetwork.domain.respository

import com.maderskitech.kmpcodingexercisenetwork.domain.model.Item

/**
 * After getting all items from the service, it maps the List of ItemDtos to a List of Items.
 */
interface ItemRepository {
    suspend fun getAllItems(): Result<Map<Int, List<Item>>>
}