package com.example.giangnguyen.data.remote


sealed class NetworkResult<out T> {

    data class Error(val error: NetworkError) : NetworkResult<Nothing>()

    data class Success<out T>(val data: T) : NetworkResult<T>()
}


enum class ErrorType {
    ResponseError, NetworkError, ServerError
}

class NetworkError(val errorType: ErrorType, errorMessage: String) : Exception(errorMessage)

