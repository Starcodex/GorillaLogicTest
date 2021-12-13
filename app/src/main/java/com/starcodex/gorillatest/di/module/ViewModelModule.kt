package com.starcodex.gorillatest.di.module

import androidx.lifecycle.ViewModelProvider
import com.starcodex.gorillatest.commons.DaggerViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}