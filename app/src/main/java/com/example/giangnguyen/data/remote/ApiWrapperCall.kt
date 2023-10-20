package com.example.giangnguyen.data.remote

import android.util.Log
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T> safeCallApi(
    api: suspend () -> Response<T>
): NetworkResult<T> {
    return try {
        val response = api.invoke()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            NetworkResult.Success(body)
        } else {
            throw HttpException(response)
        }
    } catch (throwable: Throwable) {
        val errorType = when (throwable) {
            is HttpException -> {
                val response = throwable.response()
                NetworkError(
                    ErrorType.NetworkError,
                    response?.message() ?: throwable.localizedMessage
                )
            }

            is IOException -> {
                NetworkError(ErrorType.NetworkError, throwable.localizedMessage ?: "Something went wrong")
            }

            else -> {
                NetworkError(ErrorType.ResponseError, throwable.localizedMessage ?: "Something went wrong")
            }
        }
        NetworkResult.Error(errorType)
    }
}