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
import com.example.simplifiedcrm.data.repository.UserRepository
import com.example.simplifiedcrm.ui.screens.component.TaskByStatusSortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _state = MutableStateFlow(StatisticsState())
    val state = _state.asStateFlow()

    private var currentMonth = Date().getCurrentMonth()
    private var currentYear = Date().getCurrentYear()

    init {
        getSalesInMonth()
        getSelectedPercentage()
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

    private fun getSelectedPercentage() {
        viewModelScope.launch {
            userRepository.getSelectedPercentage().first().apply {
                _state.update {
                    it.copy(
                        selectedPercentage = this
                    )
                }
            }
        }
    }

    private fun getSalesInMonth() {
        viewModelScope.launch {
            val startOfMonth = Date().getStartOfMonth(currentYear, currentMonth)
            val endOfMonth = Date().getEndOfMonth(currentYear, currentMonth)
            val salesList =
                MutableList(Date().getDaysInMonth(currentYear, currentMonth).size) { 0L }
            appRepository.getTotalPricePerDay(startOfMonth, endOfMonth).first().onEach { task ->
                val dayOfMonth = task.timestamp.getDayOfMonth()
                if (dayOfMonth in salesList.indices && task.statusTask == TaskByStatusSortOrder.DONE.name) {
                    salesList[dayOfMonth] += task.productPrice
                }
            }

            val totalSales = salesList.sum()
            _state.update {
                it.copy(
                    salesList = salesList,
                    totalSales = totalSales,
                    selectMonth = "${startOfMonth.getFormattedDate2()} - ${endOfMonth.getFormattedDate2()}"
                )
            }
            updateEarnedInMonth()
        }
    }

    suspend fun updateSelectedPercentage(selectedPercentage: Float) {
        _state.update {
            it.copy(
                selectedPercentage = selectedPercentage
            )
        }
        userRepository.saveSelectedPercentage(selectedPercentage)
        updateEarnedInMonth()
    }

    private fun updateEarnedInMonth() {
        if (state.value.totalSales != 0L && state.value.selectedPercentage != 0f) {
            _state.update {
                it.copy(
                    earnedInMonth = (state.value.totalSales * state.value.selectedPercentage / 100).toLong()
                )
            }
        } else {
            _state.update {
                it.copy(
                    earnedInMonth = 0L
                )
            }
        }
    }
}