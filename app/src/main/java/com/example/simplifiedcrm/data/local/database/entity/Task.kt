package com.example.simplifiedcrm.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.util.Date

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val client: Client = Client("","","",""),
    val timestamp: Date = Date(),
    val statusTask: String = "",
    val description: String = "",
    val productName: String = "",
    val productPrice: Long = 0L,
    val delivery: Delivery = Delivery("",0L),
    val endTime: Date = Date(Date().time + 24 * 60 * 60 * 1000),
    val notificationFlag: Boolean = false
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
