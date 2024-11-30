package com.example.simplifiedcrm.ui.screens.statistics

data class StatisticsState(
    val salesList: List<Long> = emptyList(),
    val totalSales: Long = 0L,
    val currentSelectRangeDate: String = ""
)
