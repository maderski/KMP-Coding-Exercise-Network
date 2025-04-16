package com.maderskitech.kmpcodingexercisenetwork.data.remote.api

import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.model.ItemDto
import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.network.NetworkError
import com.maderskitech.kmpcodingexercisenetwork.data.remote.api.network.Response
import com.maderskitech.kmpcodingexercisenetwork.dependencies.NetworkClient
import com.maderskitech.kmpcodingexercisenetwork.testingsupport.OpenForMokkery
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

/**
 * Gets a list of Items with an id, listId, and name. Some Items may have names that are null or empty.
 *
 * Example of the JSON returned:
 * [
 *     {"id": 755, "listId": 2, "name": ""},
 *     {"id": 203, "listId": 2, "name": ""},
 *     {"id": 684, "listId": 1, "name": "Item 684"},
 *     {"id": 276, "listId": 1, "name": "Item 276"},
 *     {"id": 736, "listId": 3, "name": null},
 *     ....
 * ]
 */
@OpenForMokkery
class ItemsApi(private val networkClient: NetworkClient) {

    suspend fun getItems(): Response<List<ItemDto?>?, NetworkError> {
        val response = try {
            networkClient.httpClient.get(
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