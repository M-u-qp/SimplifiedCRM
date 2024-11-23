package com.example.simplifiedcrm.ui.screens.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplifiedcrm.common.extension.getDaysInMonth
import com.example.simplifiedcrm.common.extension.getEndOfDay
import com.example.simplifiedcrm.common.extension.getStartOfDay
import com.example.simplifiedcrm.data.repository.AppRepository
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
            val daysInMonth = Date().getDaysInMonth()
            val salesList = mutableListOf<Long>()
            var totalSales = 0L

            daysInMonth.forEach { day ->
                val startOfDay = day.getStartOfDay()
                val endOfDay = day.getEndOfDay()
                val totalPricePerDay = appRepository.getTotalPrice(startOfDay, endOfDay).first()
                salesList.add(totalPricePerDay)
                totalSales += totalPricePerDay
            }
            _state.value = StatisticsState(
                salesList = salesList,
                totalSales = totalSales
            )
        }
    }
}