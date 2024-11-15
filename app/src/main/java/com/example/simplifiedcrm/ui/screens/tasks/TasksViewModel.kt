package com.example.simplifiedcrm.ui.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.simplifiedcrm.data.local.database.entity.Task
import com.example.simplifiedcrm.data.repository.AppRepository
import com.example.simplifiedcrm.ui.screens.component.TaskByStatusSortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {
    private val _sortOrder = MutableStateFlow(TaskByStatusSortOrder.DONE)

    private val _dialog = MutableStateFlow<Task?>(null)
    val dialog = _dialog.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val taskList = _sortOrder.flatMapLatest {
        appRepository.getSortedPagedTaskByStatus(it.name)
            .flow
            .cachedIn(viewModelScope)
    }

    fun setSortOrder(sortOrder: TaskByStatusSortOrder) {
        _sortOrder.value = sortOrder
    }

    fun setDialog(task: Task?) {
        _dialog.value = task
    }
}