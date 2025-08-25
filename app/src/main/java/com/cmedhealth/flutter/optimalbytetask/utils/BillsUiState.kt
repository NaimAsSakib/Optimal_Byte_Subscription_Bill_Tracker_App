package com.cmedhealth.flutter.optimalbytetask.utils

import com.cmedhealth.flutter.optimalbytetask.model.Subscription
import com.cmedhealth.flutter.optimalbytetask.utils.sealed.FilterType

data class BillsUiState(
    val subscriptions: List<Subscription> = emptyList(),
    val isLoading: Boolean = false,
    val filterType: FilterType = FilterType.All
)