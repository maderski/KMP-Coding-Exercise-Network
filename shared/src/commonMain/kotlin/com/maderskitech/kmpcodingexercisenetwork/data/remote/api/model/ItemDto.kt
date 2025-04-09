package com.maderskitech.kmpcodingexercisenetwork.data.remote.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ItemDto(
    val id: Int?,
    val listId: Int?,
    val name: String?
)