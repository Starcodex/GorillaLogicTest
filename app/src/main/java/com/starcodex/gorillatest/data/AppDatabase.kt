package com.starcodex.gorillatest.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.starcodex.gorillatest.data.local.CartItemDao
import com.starcodex.gorillatest.data.local.CartItemEntity


@Database(entities = arrayOf(
    CartItemEntity::class
), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartItemDao() : CartItemDao
}