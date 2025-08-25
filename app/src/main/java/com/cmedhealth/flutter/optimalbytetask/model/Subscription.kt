package com.cmedhealth.flutter.optimalbytetask.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "subscriptions")
data class Subscription(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val amount: Double,
    val currency: String = "USD",
    val frequency: String,
    val nextDueDate: Long,
    val isPaid: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
