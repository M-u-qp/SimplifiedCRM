package com.example.simplifiedcrm.common.extension

import java.text.SimpleDateFormat
import java.time.LocalDate
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
fun Date.getFormattedDate2(): String {
    return SimpleDateFormat("dd.MM.yy", Locale.getDefault()).format(this)
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

fun Date.getStartOfMonth(year: Int, month: Int): Date {
    val startOfMonth = LocalDate.of(year, month, 1)
    return Date.from(startOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant())
}

fun Date.getEndOfMonth(year: Int, month: Int): Date {
    val endOfMonth = LocalDate
        .of(year, month, 1)
        .withDayOfMonth(LocalDate.of(year, month, 1).lengthOfMonth())
    return Date.from(
        endOfMonth
            .atTime(23, 59, 59, 999_999_999)
            .atZone(ZoneId.systemDefault())
            .toInstant()
    )
}

fun Date.getDaysInMonth(year: Int, month: Int): List<Date> {
    val firstDayOfMonth = LocalDate.of(year, month, 1)
    val lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth())
    return (0 until lastDayOfMonth.dayOfMonth + 1).map {
        Date.from(
            firstDayOfMonth.plusDays(it.toLong()).atStartOfDay(ZoneId.systemDefault()).toInstant()
        )
    }
}

fun Date.getDayOfMonth(): Int {
    return this.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .dayOfMonth
}

fun Date.getCurrentMonth(): Int {
    return this.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .monthValue
}

fun Date.getCurrentYear(): Int {
    return this.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .year
}