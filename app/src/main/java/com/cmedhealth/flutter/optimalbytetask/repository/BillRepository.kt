package com.cmedhealth.flutter.optimalbytetask.repository


import com.cmedhealth.flutter.optimalbytetask.database.dao.SubscriptionDao
import com.cmedhealth.flutter.optimalbytetask.model.Subscription
import kotlinx.coroutines.flow.Flow


class BillsRepository(private val subscriptionDao: SubscriptionDao) {

    fun getAllSubscriptions() = subscriptionDao.getAllSubscriptions()

    fun getOverdueSubscriptions() = subscriptionDao.getOverdueSubscriptions()

    fun getDueSoonSubscriptions(): Flow<List<Subscription>> {
        val now = System.currentTimeMillis()
        val weekFromNow = now + (7 * 24 * 60 * 60 * 1000L)
        return subscriptionDao.getDueSoonSubscriptions(now, weekFromNow)
    }

    suspend fun insertSubscription(subscription: Subscription) {
        subscriptionDao.insertSubscription(subscription)
    }

    suspend fun updateSubscription(subscription: Subscription) {
        subscriptionDao.updateSubscription(subscription)
    }

    suspend fun deleteSubscription(subscription: Subscription) {
        subscriptionDao.deleteSubscription(subscription)
    }
}