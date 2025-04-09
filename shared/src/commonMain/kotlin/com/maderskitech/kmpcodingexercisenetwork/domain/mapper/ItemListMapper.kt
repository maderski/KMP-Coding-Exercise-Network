package com.maderskitech.kmpcodingexercisenetwork.domain.mapper

import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.model.ItemDto
import com.maderskitech.kmpcodingexercisenetwork.domain.model.Item

class ItemListMapper {
    fun toItemList(itemDtoList: List<ItemDto?>?): List<Item>? =
        itemDtoList?.filterNotNull()?.mapNotNull { itemDto ->
            if (!itemDto.name.isNullOrEmpty()) {
                Item(itemDto.id ?: -1, itemDto.listId ?: -1, itemDto.name)
            } else {
                null
            }
        }
}