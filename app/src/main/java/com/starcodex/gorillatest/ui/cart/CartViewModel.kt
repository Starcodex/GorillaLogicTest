package com.starcodex.gorillatest.ui.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.starcodex.gorillatest.data.IceRepository
import com.starcodex.gorillatest.data.local.CartItemEntity
import com.starcodex.gorillatest.data.remote.IceItem
import com.starcodex.gorillatest.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CartViewModel @Inject constructor(
    var iceRepository: IceRepository,
    var schedulerProvider: SchedulerProvider
) : ViewModel() {

    private val compositeDisposable =  CompositeDisposable()
    val totalPrice = iceRepository.getTotalPrice()

    var cartItems = Transformations.map(iceRepository.getCartItemsGroupedByType()){

        Log.d("CARTFRAGMENT","VIEW MODEL ON CHANGE")

        it.map {
            CartItem(
                name = it.name,
                quantity = it.quantity,
                price = it.price,
                type = it.type
            )
        }
    }


    fun removeItem(item: CartItem) {
        compositeDisposable.addAll(
            iceRepository.deleteCartItem(CartItemEntity(
                name = item.name,
                price = item.price,
                type = item.type,
                quantity = item.quantity
            )).compose(schedulerProvider.getSchedulersForSingle<Long>()).subscribe(
                {

                },
                {
                    Log.e("Error",it.message ?: it.toString())
                }
            )
        )

    }

}