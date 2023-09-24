package com.krshashi.imageoftheday.network

sealed class ResponseState<T> {
    data class Success<T>(val data: T) : ResponseState<T>()
    data class Failure<T>(val error: String? = null) : ResponseState<T>()
}