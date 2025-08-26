package com.cmedhealth.flutter.optimalbytetask.model

data class ExchangeRateResponse(
    val result: String,
    val base_code: String,
    val rates: Map<String, Double>
)