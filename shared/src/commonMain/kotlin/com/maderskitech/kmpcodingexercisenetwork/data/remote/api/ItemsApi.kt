package com.maderskitech.kmpcodingexercisenetwork.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.model.ItemDto
import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.network.NetworkError
import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.network.Response

class ItemsApi(private val httpClient: HttpClient) {
    suspend fun getItems(): Response<List<ItemDto?>?, NetworkError> {
        val response = try {
            httpClient.get(
                urlString = "https://fetch-hiring.s3.amazonaws.com/hiring.json"
            )
        } catch (e: UnresolvedAddressException) {
            return Response.Failure(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Response.Failure(NetworkError.SERIALIZATION)
        }

        return when (response.status.value) {
            in 200 .. 299 -> {
                val items = response.body<List<ItemDto?>?>()
                Response.Success(items)
            }
            401 -> Response.Failure(NetworkError.UNAUTHORIZED)
            403 -> Response.Failure(NetworkError.FORBIDDEN)
            408 -> Response.Failure(NetworkError.REQUEST_TIMEOUT)
            409 -> Response.Failure(NetworkError.CONFLICT)
            413 -> Response.Failure(NetworkError.PAYLOAD_TOO_LARGE)
            429 -> Response.Failure(NetworkError.TOO_MANY_REQUESTS)
            in 500 .. 599 -> Response.Failure(NetworkError.SERVER_ERROR)
            else -> Response.Failure(NetworkError.UNKNOWN)
        }
    }
}