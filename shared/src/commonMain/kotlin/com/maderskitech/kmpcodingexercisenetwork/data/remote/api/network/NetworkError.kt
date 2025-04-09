package com.maderskitech.kmpcodingexercisenetwork.data.remote.api.network

import com.maderskitech.kmpcodingexercisenetwork.data.common.ErrorType

enum class NetworkError : ErrorType {
    REQUEST_TIMEOUT,
    UNAUTHORIZED,
    CONFLICT,
    FORBIDDEN,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    PAYLOAD_TOO_LARGE,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN;
}