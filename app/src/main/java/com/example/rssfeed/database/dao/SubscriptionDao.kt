package com.example.rssfeed.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.rssfeed.database.entities.Subscription

@Dao
interface SubscriptionDao {
    @Insert
    fun insert(subscription: Subscription)

    @Delete
    fun delete(subscription: Subscription)

    @Query("SELECT * FROM subscriptions")
    fun getAll(): List<Subscription>
}