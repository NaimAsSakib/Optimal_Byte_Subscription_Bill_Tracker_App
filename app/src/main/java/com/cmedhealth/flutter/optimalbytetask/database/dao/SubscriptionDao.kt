package com.cmedhealth.flutter.optimalbytetask.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cmedhealth.flutter.optimalbytetask.model.Subscription
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriptionDao {
    @Query("SELECT * FROM subscriptions ORDER BY nextDueDate ASC")
    fun getAllSubscriptions(): Flow<List<Subscription>>

    @Query("SELECT * FROM subscriptions WHERE nextDueDate <= :date AND isPaid = 0")
    fun getOverdueSubscriptions(date: Long = System.currentTimeMillis()): Flow<List<Subscription>>

    @Query("SELECT * FROM subscriptions WHERE nextDueDate <= :endDate AND nextDueDate >= :startDate AND isPaid = 0")
    fun getDueSoonSubscriptions(startDate: Long, endDate: Long): Flow<List<Subscription>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscription(subscription: Subscription)

    @Update
    suspend fun updateSubscription(subscription: Subscription)

    @Delete
    suspend fun deleteSubscription(subscription: Subscription)
}