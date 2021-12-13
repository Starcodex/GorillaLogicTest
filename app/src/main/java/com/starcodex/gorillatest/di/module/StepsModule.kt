package com.starcodex.gorillatest.di.module

import androidx.lifecycle.ViewModel
import com.starcodex.gorillatest.data.IceRepository
import com.starcodex.gorillatest.data.IceRepositoryImpl
import com.starcodex.gorillatest.di.annotations.ViewModelKey
import com.starcodex.gorillatest.ui.MainViewModel
import com.starcodex.gorillatest.ui.cart.CartFragment
import com.starcodex.gorillatest.ui.cart.CartViewModel
import com.starcodex.gorillatest.ui.receipt.ReceiptFragment
import com.starcodex.gorillatest.util.BaseIceFragment
import com.starcodex.gorillatest.util.BaseViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class StepsModule {


    //--------- Bind Fragments -----------


    @ContributesAndroidInjector
    abstract fun contributeCartFragment(): CartFragment

    @ContributesAndroidInjector
    abstract fun contributeBaseIceFragment(): BaseIceFragment

    @ContributesAndroidInjector
    abstract fun contributeReceiptFragment(): ReceiptFragment

    //-------- Bind ViewModels ------------

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(CartViewModel::class)
    abstract fun bindCartViewModel(toppingViewModel: CartViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BaseViewModel::class)
    abstract fun bindBaseViewModel(toppingViewModel: BaseViewModel): ViewModel

    //-------- Bind Repositories ---------------
    @Binds
    abstract fun bindIceRepository(repositoryImpl: IceRepositoryImpl): IceRepository


}