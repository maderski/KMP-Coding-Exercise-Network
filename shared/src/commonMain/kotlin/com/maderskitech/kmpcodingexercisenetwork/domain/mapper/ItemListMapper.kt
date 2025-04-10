package com.maderskitech.kmpcodingexercisenetwork.domain.mapper

import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.model.ItemDto
import com.maderskitech.kmpcodingexercisenetwork.domain.model.Item

class ItemListMapper {
    fun toItemList(itemDtoList: List<ItemDto?>?): List<Item>? =
        itemDtoList?.filterNotNull()?.mapNotNull { itemDto ->
            val id = itemDto.id ?: -1
            val listId = itemDto.listId ?: -1
            val name = itemDto.name

            // TODO: Check with product how to handle null id or null listId.  The requirements
            //  only state to filter out null or empty names.  Need to find out if we are only
            //  logging these or if the user should be told something is wrong.
            if (!name.isNullOrEmpty()) {
                Item(id, listId, name)
            } else {
                null
            }
        }
}