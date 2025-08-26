package com.cmedhealth.flutter.optimalbytetask.model

data class ConvertedSubscription(
    val subscription: Subscription,
    val bdtAmount: Double,
    val exchangeRate: Double
)