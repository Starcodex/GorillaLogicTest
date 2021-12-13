package com.starcodex.gorillatest.data.remote

import io.reactivex.Observable
import retrofit2.http.GET

interface FlavorsApiClient {
    @GET("flavors")
    fun getFlavors() : Observable<List<IceItem>>
}