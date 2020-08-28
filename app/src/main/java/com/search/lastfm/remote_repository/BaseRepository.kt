package com.search.lastfm.remote_repository

import android.util.Log
import retrofit2.Response
import java.io.IOException

open class BaseRepository {

    suspend fun <T : Any> makeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {

        val result: Result<T> = getApiResult(call, errorMessage)
        var data: T? = null

        when (result) {
            is Result.Success -> {
                data = result.data
                Log.d(BaseRepository::class.simpleName, data.toString())
            }
            is Result.Error -> {
                Log.d(
                    BaseRepository::class.simpleName,
                    "$errorMessage & Exception - ${result.exception}"
                )
            }
        }

        return data

    }

    private suspend fun <T : Any> getApiResult(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): Result<T> {
        val response = call.invoke()
        if (response.isSuccessful) return Result.Success(response.body()!!)

        return Result.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
    }

    sealed class Result<out T : Any> {
        data class Success<out T : Any>(val data: T) : Result<T>()
        data class Error(val exception: Exception) : Result<Nothing>()
    }
}

