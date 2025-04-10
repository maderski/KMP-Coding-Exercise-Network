package com.maderskitech.kmpcodingexercisenetwork.domain.usecase

import com.maderskitech.kmpcodingexercisenetwork.domain.model.Item
import com.maderskitech.kmpcodingexercisenetwork.domain.respository.ItemRepository
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DefaultSortItemsUseCaseTest {
    val items = listOf(
        Item(684, 1, "Item 684"),
        Item(276, 1, "Item 276"),
        Item(808, 4, "Item 808"),
        Item(680, 3, "Item 680"),
        Item(534, 4, "Item 534"),
        Item(906, 2, "Item 906"),
        Item(735, 1, "Item 735"),
        Item(681, 4, "Item 681"),
        Item(137, 3, "Item 137"),
    )


    val repo = object : ItemRepository {
        override suspend fun getAllItems(): Result<List<Item>> = Result.success(items)
    }

    val useCase = DefaultSortItemsUseCase(repo)

    @Test
    fun `test items are first sorted by listId and then sorted by name`() = runBlocking {
        useCase.getSortedItemsFlow().collect { result ->
            val sortedItemsMap = result.getOrNull()
            sortedItemsMap?.forEach { entry ->
                println(entry.key)
                entry.value.forEach {
                    println(it.name)
                }
            }

            val expectedItems = mapOf(
                1 to listOf(Item(276, 1, "Item 276"), Item(684, 1, "Item 684"), Item(735, 1, "Item 735")),
                2 to listOf(Item(906, 2, "Item 906")),
                3 to listOf(Item(137, 3, "Item 137"), Item(680, 3, "Item 680")),
                4 to listOf(Item(534, 4, "Item 534"), Item(681, 4, "Item 681"), Item(808, 4, "Item 808"))
            )
            assertEquals(expectedItems, sortedItemsMap)
        }
    }

    @Test
    fun `test failure is received`() = runBlocking {
        val repoWithFailure = object : ItemRepository {
            override suspend fun getAllItems(): Result<List<Item>> = Result.failure(Exception("Failed to get Items!"))
        }
        val useCaseFailure = DefaultSortItemsUseCase(repoWithFailure)
        useCaseFailure.getSortedItemsFlow().collect { result ->
            assertTrue(result.isFailure)
            assertNotNull(result.exceptionOrNull())
            val message = result.exceptionOrNull()?.message
            assertEquals("Failed to get Items!", message)
        }
    }
}