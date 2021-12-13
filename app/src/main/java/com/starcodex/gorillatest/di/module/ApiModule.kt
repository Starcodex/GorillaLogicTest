package com.starcodex.gorillatest.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.starcodex.gorillatest.data.remote.ConfigApiClient
import com.starcodex.gorillatest.data.remote.FlavorsApiClient
import com.starcodex.gorillatest.data.remote.ToppingsApiClient
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class ApiModule {

    @Provides
    internal fun provideOkHttpClient(): OkHttpClient.Builder {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
    }

    @Provides
    internal fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create()
    }



    @Provides
    internal fun provideRetrofit(client: OkHttpClient.Builder, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gorilla-challenges.herokuapp.com/icecream/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(client.build())
            .build()
    }




    @Provides
    fun provideConfigApiClient(retrofit: Retrofit) = retrofit.create(ConfigApiClient::class.java)


    @Provides
    fun provideFlavorsApiClient(retrofit: Retrofit) = retrofit.create(FlavorsApiClient::class.java)

    @Provides
    fun provideToppingsApiClient(retrofit: Retrofit) = retrofit.create(ToppingsApiClient::class.java)

}