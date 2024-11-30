package com.example.simplifiedcrm.ui.screens.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplifiedcrm.common.extension.getCurrentMonth
import com.example.simplifiedcrm.common.extension.getCurrentYear
import com.example.simplifiedcrm.common.extension.getDayOfMonth
import com.example.simplifiedcrm.common.extension.getDaysInMonth
import com.example.simplifiedcrm.common.extension.getEndOfMonth
import com.example.simplifiedcrm.common.extension.getFormattedDate2
import com.example.simplifiedcrm.common.extension.getStartOfMonth
import com.example.simplifiedcrm.data.repository.AppRepository
import com.example.simplifiedcrm.ui.screens.component.TaskByStatusSortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {
    private val _state = MutableStateFlow(StatisticsState())
    val state = _state.asStateFlow()

    private var currentMonth = Date().getCurrentMonth()
    private var currentYear = Date().getCurrentYear()

    init {
        getSalesInMonth()
    }

    fun goToPreviousMonth() {
        if (currentMonth == 1) {
            currentMonth = 12
            currentYear -= 1
        } else {
            currentMonth -= 1
        }
        getSalesInMonth()
    }

    fun goToNextMonth() {
        if (currentMonth == 12) {
            currentMonth = 1
            currentYear += 1
        } else {
            currentMonth += 1
        }
        getSalesInMonth()
    }

     private fun getSalesInMonth() {
        viewModelScope.launch {
            val startOfMonth = Date().getStartOfMonth(currentYear, currentMonth)
            val endOfMonth = Date().getEndOfMonth(currentYear, currentMonth)
            val salesList =
                MutableList(Date().getDaysInMonth(currentYear, currentMonth).size) { 0L }
            val salesData = appRepository.getTotalPricePerDay(startOfMonth, endOfMonth).first()
                if (salesData.all { it.productPrice == 0L && it.statusTask == "" }) {
                    _state.value = StatisticsState(
                        salesList = MutableList(Date().getDaysInMonth(currentYear, currentMonth).size) { 0L },
                        totalSales = 0L,
                        currentSelectRangeDate = "${startOfMonth.getFormattedDate2()} - ${endOfMonth.getFormattedDate2()}"
                    )
                } else {
                    salesData.onEach { task ->
                        val dayOfMonth = task.timestamp.getDayOfMonth()
                        if (dayOfMonth in salesList.indices && task.statusTask == TaskByStatusSortOrder.DONE.name) {
                            salesList[dayOfMonth] += task.productPrice
                        }
                    }
                }
            val totalSales = salesList.sum()
            _state.value = StatisticsState(
                salesList = salesList,
                totalSales = totalSales,
                currentSelectRangeDate = "${startOfMonth.getFormattedDate2()} - ${endOfMonth.getFormattedDate2()}"
            )
        }
    }
}