package com.example.simplifiedcrm.common.extension

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Date.getFormattedDate(): String =
    SimpleDateFormat("EE, dd MMMM yyyy, HH:mm", Locale.getDefault())
        .format(this)

fun Date.getFormattedDay(): String {
    return SimpleDateFormat("dd", Locale.getDefault()).format(this)
}
fun Date.getFormattedMonth(): String {
    return SimpleDateFormat("MMMM", Locale.getDefault()).format(this)
}
fun Date.getToLocalDateTime(): LocalDateTime {
    return toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
}
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


fun Date.getStartOfDay(): Date {
    val calendar = Calendar.getInstance().apply {
        time = this@getStartOfDay
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    return calendar.time
}
fun Date.getEndOfDay(): Date {
    val calendar = Calendar.getInstance().apply {
        time = this@getEndOfDay
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 999)
    }
    return calendar.time
}
fun Date.getStartOfMonth(): Date {
    val calendar = Calendar.getInstance().apply {
        time = this@getStartOfMonth
        set(Calendar.DAY_OF_MONTH, 1)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    return calendar.time
}
fun Date.getEndOfMonth(): Date {
    val calendar = Calendar.getInstance().apply {
        time = this@getEndOfMonth
        set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 999)
    }
    return calendar.time
}
fun Date.getDaysInMonth(): List<Date> {
    val localDate = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    val firstDayOfMonth = localDate.withDayOfMonth(1)
    val lastDayOfMonth = localDate.withDayOfMonth(localDate.lengthOfMonth())
    return (firstDayOfMonth.dayOfMonth..lastDayOfMonth.dayOfMonth).map {
        Date.from(firstDayOfMonth.withDayOfMonth(it).atStartOfDay(ZoneId.systemDefault()).toInstant())
    }
}