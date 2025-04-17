package com.maderskitech.kmpcodingexercisenetwork.data.remote.api

import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.model.ItemDto
import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.network.NetworkError
import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.network.Response

interface ItemsApi {
    suspend fun getItems(): Response<List<ItemDto?>?, NetworkError>
}