package com.maderskitech.kmpcodingexercisenetwork.domain.repository

import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.ItemsApi
import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.model.ItemDto
import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.network.NetworkError
import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.network.Response
import com.maderskitech.kmpcodingexercisenetwork.domain.mapper.ItemListMapper
import com.maderskitech.kmpcodingexercisenetwork.domain.model.Item
import com.maderskitech.kmpcodingexercisenetwork.domain.respository.DefaultItemRepository
import com.maderskitech.kmpcodingexercisenetwork.domain.respository.ItemRepository
import dev.mokkery.answering.calls
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class DefaultItemRespositoryTest {
    val items = listOf(
        ItemDto(684, 1, "Item 684"),
        ItemDto(276, 1, "Item 276"),
        ItemDto(531, 5, "Item 531"),
        ItemDto(808, 4, "Item 808"),
        ItemDto(680, 3, "Item 680"),
        ItemDto(53, 5, "Item 63"),
        ItemDto(534, 4, "Item 534"),
        ItemDto(906, 2, "Item 906"),
        ItemDto(735, 1, "Item 735"),
        ItemDto(681, 4, "Item 681"),
        ItemDto(137, 3, "Item 137"),
        ItemDto(5, 5, "Item 5")
    )

    val api = mock<ItemsApi> {
        everySuspend { getItems() } returns Response.Success(items)
    }

    val repository = DefaultItemRepository(api, ItemListMapper())

    @Test
    fun `test items are first sorted by listId and then sorted by name`() = runBlocking {
        val sortedItemsMap = repository.getAllItems().getOrNull()
        sortedItemsMap?.forEach { entry ->
            println(entry.key)
            entry.value.forEach {
                println(it.name)
            }
        }

        val expectedItems = mapOf(
            1 to listOf(
                Item(276, 1, "Item 276"),
                Item(684, 1, "Item 684"),
                Item(735, 1, "Item 735")
            ),
            2 to listOf(Item(906, 2, "Item 906")),
            3 to listOf(Item(137, 3, "Item 137"), Item(680, 3, "Item 680")),
            4 to listOf(
                Item(534, 4, "Item 534"),
                Item(681, 4, "Item 681"),
                Item(808, 4, "Item 808")
            ),
            5 to listOf(Item(5, 5, "Item 5"), Item(53, 5, "Item 63"), Item(531, 5, "Item 531"))
        )
        assertEquals(expectedItems, sortedItemsMap)
    }

    @Test
    fun `test failure is received`() = runBlocking {
        val apiAlwaysFails = mock<ItemsApi> {
            everySuspend { getItems() } returns Response.Failure(NetworkError.REQUEST_TIMEOUT)
        }
        val repository = DefaultItemRepository(apiAlwaysFails, ItemListMapper())
        val result = repository.getAllItems()
        assertTrue(result.isFailure)
        assertNotNull(result.exceptionOrNull())
        val message = result.exceptionOrNull()?.message
        assertEquals("Error Occurred: REQUEST_TIMEOUT", message)
    }
}