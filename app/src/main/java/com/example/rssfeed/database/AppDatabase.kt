package com.example.rssfeed.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rssfeed.database.dao.SubscriptionDao
import com.example.rssfeed.database.entities.Subscription

private const val DB_NAME = "app-db"

@Database(entities = [Subscription::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun subscriptionDao(): SubscriptionDao

    companion object {
        fun newInstance(applicationContext: Context) =
            Room.databaseBuilder(applicationContext, AppDatabase::class.java, DB_NAME).build()
    }
}