package com.maderskitech.kmpcodingexercisenetwork.domain.mapper

import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.model.ItemDto
import com.maderskitech.kmpcodingexercisenetwork.domain.model.Item

/**
 * Only maps an ItemDto to an Item if it is not null AND the name in the ItemDto is not null or empty.
 */
class ItemListMapper {
    fun toItemList(itemDtoList: List<ItemDto?>?): List<Item>? =
        itemDtoList?.filterNotNull()?.mapNotNull { itemDto ->
            val id = itemDto.id ?: -1
            val listId = itemDto.listId ?: -1
            val name = itemDto.name

            // TODO: Figure out if invalid listId and/or invalid id
            //  are just logged or if the user should be told something is wrong.
            if (!name.isNullOrEmpty()) {
                Item(id, listId, name)
            } else {
                null
            }
        }
}