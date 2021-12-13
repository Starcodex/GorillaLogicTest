package com.starcodex.gorillatest.data.remote


import io.reactivex.Observable
import retrofit2.http.GET

interface ConfigApiClient {
    @GET("config")
    fun getConfig() : Observable<ConfigItem>
}