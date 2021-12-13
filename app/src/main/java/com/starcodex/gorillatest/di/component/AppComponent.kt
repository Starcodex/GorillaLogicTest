package com.starcodex.gorillatest.di.component

import com.starcodex.gorillatest.data.AppDatabase
import com.starcodex.gorillatest.data.local.CartItemDao
import com.starcodex.gorillatest.di.IceApplication
import com.starcodex.gorillatest.di.module.ActivityBuildersModule
import com.starcodex.gorillatest.di.module.ApiModule
import com.starcodex.gorillatest.di.module.AppModule
import com.starcodex.gorillatest.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBuildersModule::class,
    AppModule::class,
    ApiModule::class,
    ViewModelModule::class])
interface AppComponent : AndroidInjector<IceApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: IceApplication): AppComponent
    }

    fun appDatabase() : AppDatabase
    fun cartItemDao() : CartItemDao
}
