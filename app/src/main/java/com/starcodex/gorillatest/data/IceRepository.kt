package com.starcodex.gorillatest.data


import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.starcodex.gorillatest.data.local.CartItemEntity
import com.starcodex.gorillatest.data.remote.ConfigItem
import com.starcodex.gorillatest.data.remote.IceItem
import io.reactivex.Observable
import io.reactivex.Single


interface IceRepository {
    fun getConfig(): Observable<ConfigItem>
    fun getFlavorsList(): Observable<List<IceItem>>
    fun getToppingsList(): Observable<List<IceItem>>
    fun getByName(name: String) : CartItemEntity?
    fun insertCartItem(itm: CartItemEntity): Single<Long>
    fun getCartItemsGroupedByType(): LiveData<List<CartItemEntity>>
    fun getTotalPrice() : LiveData<Long>
    fun deleteCartItem(itm: CartItemEntity): Single<Long>
    fun deleteAllFromTable()
}