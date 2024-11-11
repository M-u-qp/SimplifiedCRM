package com.example.simplifiedcrm.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.util.Date

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val client: Client,
    val timestamp: Date,
    val description: String,
    val productName: String,
    val productPrice: Long,
    val delivery: Delivery
)

@Serializable
data class Client(
    val name: String,
    val phone: String,
    val email: String,
    val marking: String
)
@Serializable
data class Delivery(
    val name: String,
    val price: Long
)
