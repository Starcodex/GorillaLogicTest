package com.starcodex.gorillatest.di.module

import com.starcodex.gorillatest.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = arrayOf(
    ViewModelModule::class
))
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [StepsModule::class])
    abstract fun bindMainActivity(): MainActivity

}