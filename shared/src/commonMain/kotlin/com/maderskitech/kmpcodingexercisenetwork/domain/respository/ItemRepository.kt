package com.maderskitech.kmpcodingexercisenetwork.domain.respository

import com.maderskitech.kmpcodingexercisenetwork.domain.model.Item

interface ItemRepository {
    suspend fun getAllItems(): Result<List<Item>>
}