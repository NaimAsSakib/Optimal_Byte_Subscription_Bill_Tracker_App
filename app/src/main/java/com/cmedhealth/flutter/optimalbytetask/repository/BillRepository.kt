package com.cmedhealth.flutter.optimalbytetask.repository


import com.cmedhealth.flutter.optimalbytetask.apiService.CurrencyApiService
import com.cmedhealth.flutter.optimalbytetask.database.dao.SubscriptionDao
import com.cmedhealth.flutter.optimalbytetask.model.ConvertedSubscription
import com.cmedhealth.flutter.optimalbytetask.model.ExchangeRateResponse
import com.cmedhealth.flutter.optimalbytetask.model.Subscription
import kotlinx.coroutines.flow.Flow


class BillsRepository(
    private val subscriptionDao: SubscriptionDao,
    private val currencyApiService: CurrencyApiService = CurrencyApiService.create()
) {

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


    suspend fun getExchangeRates(): Result<ExchangeRateResponse> {
        return try {
            val response = currencyApiService.getExchangeRates()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun convertToBDT(subscriptions: List<Subscription>): Result<List<ConvertedSubscription>> {
        return try {
            val ratesResponse = getExchangeRates()
            if (ratesResponse.isSuccess) {
                val rates = ratesResponse.getOrNull()?.rates ?: return Result.failure(Exception("No rates available"))

                val convertedList = subscriptions.map { subscription ->
                    val bdtAmount = when (subscription.currency) {
                        "BDT" -> subscription.amount
                        else -> {
                            val rate = rates[subscription.currency] ?: 1.0
                            subscription.amount / rate
                        }
                    }

                    ConvertedSubscription(
                        subscription = subscription,
                        bdtAmount = bdtAmount,
                        exchangeRate = rates[subscription.currency] ?: 1.0
                    )
                }
                Result.success(convertedList)
            } else {
                Result.failure(ratesResponse.exceptionOrNull() ?: Exception("Failed to get exchange rates"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
