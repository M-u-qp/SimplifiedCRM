package com.example.simplifiedcrm.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.simplifiedcrm.data.repository.AppRepository
import com.example.simplifiedcrm.ui.screens.component.TaskByStatusSortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {
private val _sortOrder = MutableStateFlow(TaskByStatusSortOrder.ACTIVE)

    @OptIn(ExperimentalCoroutinesApi::class)
    val taskList = _sortOrder.flatMapLatest {
        appRepository.getSortedPagedTaskByStatus(it.name)
            .flow
            .cachedIn(viewModelScope)
    }
}