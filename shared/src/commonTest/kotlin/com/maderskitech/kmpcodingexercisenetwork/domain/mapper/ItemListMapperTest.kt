package com.maderskitech.kmpcodingexercisenetwork.domain.mapper

import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.model.ItemDto
import com.maderskitech.kmpcodingexercisenetwork.domain.model.Item
import kotlin.test.Test
import kotlin.test.assertEquals

class ItemListMapperTest {
    private val mapper = ItemListMapper()

    @Test
    fun `test itemDtoList with null name or empty name values are filtered out`() {
        val itemDtoList = listOf<ItemDto>(
            ItemDto(100, 1, ""),
            ItemDto(101, 1, "Item1"),
            ItemDto(102, 1, "Item2"),
            ItemDto(103, 1, null),
        )
        val mappedItems = mapper.toItemList(itemDtoList)
        val expectedItemList = listOf(
            Item(101, 1, "Item1"),
            Item(102, 1, "Item2"),
        )
        assertEquals(expectedItemList, mappedItems)
    }

    @Test
    fun `test itemDtoList is null and should return an empty list`() {
        val mappedItems = mapper.toItemList(null)
        assertEquals(emptyList<Item>(), mappedItems)
    }

    @Test
    fun `test a list of all ItemDto that are null should return an empty list`() {
        val nullItemDtoList = listOf<ItemDto?>(null, null, null)
        val mappedItems = mapper.toItemList(nullItemDtoList)
        assertEquals(emptyList<Item>(), mappedItems)
    }

    @Test
    fun `test a list of ItemDto with some that have null ids`() {
        val itemDtoList = listOf<ItemDto>(
            ItemDto(null, 1, "Item0"),
            ItemDto(null, 1, "Item1"),
            ItemDto(102, 1, "Item2"),
            ItemDto(103, 1, null),
        )
        val mappedItems = mapper.toItemList(itemDtoList)
        val expectedItems = listOf(
            Item(id=-1, listId=1, name="Item0"),
            Item(id=-1, listId=1, name="Item1"),
            Item(id=102, listId=1, name="Item2")
        )
        assertEquals(expectedItems, mappedItems)
    }

    @Test
    fun `test a list of ItemDto with some that have null ListIds`() {
        val itemDtoList = listOf<ItemDto>(
            ItemDto(100, 1, "Item0"),
            ItemDto(101, null, "Item1"),
            ItemDto(102, null, "Item2"),
            ItemDto(103, 1, "Item3"),
        )
        val mappedItems = mapper.toItemList(itemDtoList)
        val expectedItems = listOf(
            Item(id=100, listId=1, name="Item0"),
            Item(id=101, listId=-1, name="Item1"),
            Item(id=102, listId=-1, name="Item2"),
            Item(id=103, listId=1, name="Item3")
        )
        assertEquals(expectedItems, mappedItems)
    }
}