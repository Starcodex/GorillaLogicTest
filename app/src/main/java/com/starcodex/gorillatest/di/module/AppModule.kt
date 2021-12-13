package com.starcodex.gorillatest.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.starcodex.gorillatest.BuildConfig.DB_NAME
import com.starcodex.gorillatest.data.AppDatabase
import com.starcodex.gorillatest.data.local.CartItemDao
import com.starcodex.gorillatest.di.IceApplication
import com.starcodex.gorillatest.util.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideAppContext(app: IceApplication) : Context = app.applicationContext

    @Provides
    @Singleton
    fun provideSchedulerProvider() = SchedulerProvider(Schedulers.io(), AndroidSchedulers.mainThread())

    @Provides
    @Singleton
    fun providesAppDatabase(app: IceApplication): AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, DB_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideCartItemDao(appDatabase: AppDatabase): CartItemDao{
        return appDatabase.cartItemDao()
    }
}