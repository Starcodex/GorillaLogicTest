package com.starcodex.gorillatest.util

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.starcodex.gorillatest.data.IceRepository
import com.starcodex.gorillatest.data.local.CartItemEntity
import com.starcodex.gorillatest.data.remote.ConfigItem
import com.starcodex.gorillatest.data.remote.IceItem
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class BaseViewModel @Inject constructor(
    var iceRepository: IceRepository,
    var schedulerProvider: SchedulerProvider
) : ViewModel() {


    private val compositeDisposable =  CompositeDisposable()

    // ------------ GET FLAVORS ------------
    val flavorPrivateLiveData: MutableLiveData<List<IceItem>> = MutableLiveData()
    val flavorLiveData: LiveData<List<IceItem>> get() = flavorPrivateLiveData

    fun getIceProduct(type : String) = compositeDisposable.addAll(
        when(type){
            "flavor" -> iceRepository.getFlavorsList()
                .compose(schedulerProvider.getSchedulersForObservable())
                .subscribe ({
                    flavorPrivateLiveData.value = it
                },{
                    Log.e("Error",it.message ?: it.toString())
                })
            else -> iceRepository.getToppingsList()
                .compose(schedulerProvider.getSchedulersForObservable())
                .subscribe ({
                    flavorPrivateLiveData.value = it
                },{
                    Log.e("Error",it.message ?: it.toString())
                })
        }

    )

    // ------------ GET CONFIG ----------

    val configFilePrivateLiveData: MutableLiveData<ConfigItem> = MutableLiveData()
    val configFileLiveData: LiveData<ConfigItem> get() = configFilePrivateLiveData

    fun getConfig() = compositeDisposable.addAll(
        iceRepository.getConfig()
            .compose(schedulerProvider.getSchedulersForObservable())
            .subscribe ({
                configFilePrivateLiveData.value = it
            },{
                Log.e("Error",it.message ?: it.toString())
            })
    )

    // ------------ CART INSERTION ----------

    val cartItemPrivateLiveData: MutableLiveData<Long> = MutableLiveData()
    val cartItemLiveData: LiveData<Long> get() = cartItemPrivateLiveData

    fun insertCartItem(itm : IceItem, type : String){
        compositeDisposable.addAll(
            iceRepository.insertCartItem(
                CartItemEntity(
                name = itm.name,
                price = itm.price,
                type = type,
                quantity = 1
            )
            ).compose(schedulerProvider.getSchedulersForSingle<Long>()).subscribe(
                {
                    cartItemPrivateLiveData.value = it
                },
                {
                    Log.e("Error",it.message ?: it.toString())
                }
            )
        )
    }


    fun performInit(type : String){
        getConfig()
        getIceProduct(type)
    }
}