package com.maderskitech.kmpcodingexercisenetwork.domain.respository

import com.maderskitech.kmpcodingexercisenetwork.domain.model.Item
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

/**
 * This wrapper class was created to solve several iOS interop issues.
 * 1) Needed a way to inject the ItemRepository, so I could use it in Swift
 * 2) Swift doesn’t see getAllItems() on ItemRepository — interface + suspend isn’t exposed,
 *    regardless of asyncFunction vs asyncResult.
 * 3) Kotlin Result Generics where not being properly typed in iOS, so the wrapper does not
 * return a result, but rather just a map.
 */
class ItemRepositoryWrapper(private val repo: ItemRepository)  {
    @NativeCoroutines
    suspend fun getAllItems(): Map<Int, List<Item>> =
        repo
            .getAllItems()
            .getOrThrow()
}