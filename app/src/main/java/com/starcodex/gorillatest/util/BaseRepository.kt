package com.starcodex.gorillatest.util


import retrofit2.Call

abstract class BaseRepository {

    protected suspend fun <T> getResult(call: suspend () -> Call<T>): Resource<T> {
        try {
            val response = call.invoke().execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.error("Network call has failed for a following reason: $message")
    }

}