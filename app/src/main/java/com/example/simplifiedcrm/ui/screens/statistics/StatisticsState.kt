package com.example.simplifiedcrm.ui.screens.statistics

import com.example.simplifiedcrm.common.extension.getDaysInMonth
import java.util.Date

data class StatisticsState(
    val salesList: List<Long> = MutableList(Date().getDaysInMonth().size) { 0L },
    val totalSales: Long = 0L,
    val selectMonth: String = "",
    val selectedPercentage: Float = 0f,
    val earnedInMonth: Long = 0L
)
