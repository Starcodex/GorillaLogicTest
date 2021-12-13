package com.starcodex.gorillatest.data


import androidx.lifecycle.LiveData
import com.starcodex.gorillatest.data.local.CartItemDao
import com.starcodex.gorillatest.data.local.CartItemEntity
import com.starcodex.gorillatest.data.remote.ConfigApiClient
import com.starcodex.gorillatest.data.remote.FlavorsApiClient
import com.starcodex.gorillatest.data.remote.ToppingsApiClient
import com.starcodex.gorillatest.util.BaseRepository
import io.reactivex.Single

import javax.inject.Inject

class IceRepositoryImpl @Inject constructor(
    var apiClient: ConfigApiClient,
    var flavorsApiClient: FlavorsApiClient,
    var toppingsApiClient: ToppingsApiClient,
    var cartItemDao : CartItemDao

): BaseRepository(), IceRepository {

    override fun getConfig() = apiClient.getConfig()

    override fun getFlavorsList() = flavorsApiClient.getFlavors()

    override fun getToppingsList() = toppingsApiClient.getToppings()

    override fun getByName(name: String) = cartItemDao.getByName(name)

    override fun insertCartItem(itm: CartItemEntity) : Single<Long> = Single.fromCallable {
        val insert = cartItemDao.insertIgnore(itm)
        if (insert == -1L) {
            val current = getByName(itm.name)
            current.quantity ++
            return@fromCallable cartItemDao.updateCartItem(current).toLong()
        }else{
            return@fromCallable insert
        }
    }

    override fun getCartItemsGroupedByType() = cartItemDao.getCartItemsGroupedByType()

    override fun getTotalPrice() = cartItemDao.getTotalPrice()

    override fun deleteCartItem(itm: CartItemEntity) : Single<Long> = Single.fromCallable{
        val current = getByName(itm.name)
        if(current.quantity==1){
            return@fromCallable cartItemDao.deleteCartItem(current).toLong()
        }else{
            current.quantity --
            return@fromCallable cartItemDao.updateCartItem(current).toLong()
        }
    }

    override fun deleteAllFromTable() = cartItemDao.deleteAllFromTable()
}