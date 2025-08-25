package com.cmedhealth.flutter.optimalbytetask.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cmedhealth.flutter.optimalbytetask.database.dao.SubscriptionDao
import com.cmedhealth.flutter.optimalbytetask.model.Subscription


@Database(
    entities = [Subscription::class],
    version = 1,
    exportSchema = false
)
abstract class BillsDatabase : RoomDatabase() {
    abstract fun subscriptionDao(): SubscriptionDao

    companion object {
        @Volatile
        private var INSTANCE: BillsDatabase? = null

        fun getDatabase(context: Context): BillsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BillsDatabase::class.java,
                    "bills_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}