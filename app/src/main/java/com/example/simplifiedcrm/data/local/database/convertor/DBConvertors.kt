package com.example.simplifiedcrm.data.local.database.convertor

import androidx.room.TypeConverter
import com.example.simplifiedcrm.data.local.database.entity.Client
import com.example.simplifiedcrm.data.local.database.entity.Delivery
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Date

object DBConvertors {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromClient(client: Client): String {
        return json.encodeToString(client)
    }

    @TypeConverter
    fun toClient(clientString: String): Client {
        return json.decodeFromString(clientString)
    }

    @TypeConverter
    fun fromDelivery(delivery: Delivery): String {
        return json.encodeToString(delivery)
    }

    @TypeConverter
    fun toDelivery(deliveryString: String): Delivery {
        return json.decodeFromString(deliveryString)
    }

    @TypeConverter
    fun fromTimeInMillisToDate(timeInMillis: Long): Date {
        return Date(timeInMillis)
    }

    @TypeConverter
    fun fromDateToTimeInMillis(date: Date?): Long? {
        return date?.time
    }
}