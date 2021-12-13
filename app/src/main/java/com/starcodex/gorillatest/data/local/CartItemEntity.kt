package com.starcodex.gorillatest.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CART_ITEMS")
class CartItemEntity (

    @ColumnInfo(name = "name") @PrimaryKey
    var name: String,

    @ColumnInfo(name = "price")
    var price: String,

    @ColumnInfo(name = "type")
    var type: String,

    @ColumnInfo(name = "quantity")
    var quantity: Int
)