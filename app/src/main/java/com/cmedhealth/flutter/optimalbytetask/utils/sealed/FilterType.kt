package com.cmedhealth.flutter.optimalbytetask.utils.sealed

sealed class FilterType {
    object All : FilterType()
    object DueSoon : FilterType()
    object Overdue : FilterType()
}