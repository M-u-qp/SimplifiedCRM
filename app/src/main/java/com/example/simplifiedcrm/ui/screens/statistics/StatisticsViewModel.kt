package com.example.simplifiedcrm.ui.screens.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplifiedcrm.common.extension.getDayOfMonth
import com.example.simplifiedcrm.common.extension.getDaysInMonth
import com.example.simplifiedcrm.common.extension.getEndOfMonth
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

    init {
        getSalesInMonth()
    }

    private fun getSalesInMonth() {
        viewModelScope.launch {
            val startOfMonth = Date().getStartOfMonth()
            val endOfMonth = Date().getEndOfMonth()
            val salesList = MutableList(Date().getDaysInMonth().size) { 0L }
            appRepository.getTotalPricePerDay(startOfMonth, endOfMonth).first().onEach { task ->
                val dayOfMonth = task.timestamp.getDayOfMonth()
                if (dayOfMonth in salesList.indices && task.statusTask == TaskByStatusSortOrder.DONE.name) {
                    salesList[dayOfMonth] += task.productPrice
                }
            }
            val totalSales = salesList.sum()
            _state.value = StatisticsState(
                salesList = salesList,
                totalSales = totalSales
            )
        }
    }
}