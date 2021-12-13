package com.starcodex.gorillatest.data.remote

import io.reactivex.Observable
import retrofit2.http.GET

interface ToppingsApiClient {
    @GET("toppings")
    fun getToppings() : Observable<List<IceItem>>
}