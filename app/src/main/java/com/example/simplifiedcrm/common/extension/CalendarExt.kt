package com.example.simplifiedcrm.common.extension

import java.util.Calendar
import java.util.Date

fun Date.toCalendar(): Date {
    val date = Calendar.getInstance().apply {
        time = this@toCalendar
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time
    return date
}