package com.starcodex.gorillatest.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import android.provider.SyncStateContract.Helpers.update

import android.database.sqlite.SQLiteConstraintException

import android.provider.SyncStateContract.Helpers.insert





@Dao
interface CartItemDao {

    @Query("SELECT *  FROM CART_ITEMS WHERE name = :name")
    fun getByName(name: String) : CartItemEntity

    @Query("SELECT * FROM CART_ITEMS ORDER BY type")
    fun getCartItemsGroupedByType(): LiveData<List<CartItemEntity>>

    @Delete
    fun deleteCartItem(cartItemEntity: CartItemEntity) : Int

    @Query("DELETE FROM CART_ITEMS")
    fun deleteAllFromTable()

    @Query("SELECT SUM(quantity*price) FROM CART_ITEMS")
    fun getTotalPrice() : LiveData<Long>

    @Update()
    fun updateCartItem(entity: CartItemEntity) : Int


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIgnore(entity: CartItemEntity) : Long



}