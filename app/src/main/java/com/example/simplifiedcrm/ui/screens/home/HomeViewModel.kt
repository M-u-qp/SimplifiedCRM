package com.example.simplifiedcrm.ui.screens.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.simplifiedcrm.R
import com.example.simplifiedcrm.common.extension.getToLocalDateTime
import com.example.simplifiedcrm.data.local.database.entity.Task
import com.example.simplifiedcrm.data.repository.AppRepository
import com.example.simplifiedcrm.data.repository.UserRepository
import com.example.simplifiedcrm.domain.notification.Notifications
import com.example.simplifiedcrm.ui.screens.component.TaskByStatusSortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val userRepository: UserRepository,
    private val notifications: Notifications
) : ViewModel(), HomeEvent {
    private val _task = MutableStateFlow(Task())
    val task = _task.asStateFlow()

    private val _activeTaskStatus = MutableStateFlow(TaskByStatusSortOrder.ACTIVE)
    private val _sortOrder = MutableStateFlow("")

    private val _dialog = MutableStateFlow<Task?>(null)
    val dialog = _dialog.asStateFlow()

    private val _navigateToLogin = MutableStateFlow(false)
    val navigateToLogin: StateFlow<Boolean> = _navigateToLogin

    private val _navigateToSettings = MutableStateFlow(false)
    val navigateToSettings: StateFlow<Boolean> = _navigateToSettings

    @OptIn(ExperimentalCoroutinesApi::class)
    val taskList = _activeTaskStatus.flatMapLatest {
        appRepository.getSortedPagedTaskByStatus(it.name)
            .flow
            .cachedIn(viewModelScope)
    }

    suspend fun checkTasksStatus(
        context: Context,
        task: Task
    ) {
        val currentTime = Date()
        if (currentTime.after(task.endTime)) {
            notifications.sendTaskExpirationNotification(
                context = context,
                isExpired = true,
                taskId = task.id,
                taskName = task.productName
            )
            updateTaskStatus(
                task,
                TaskByStatusSortOrder.EXPIRED.name
            )
            replaceTask()
        } else if (
            currentTime.getToLocalDateTime().hour == task.timestamp.getToLocalDateTime().hour &&
            currentTime.getToLocalDateTime().hour == task.timestamp.getToLocalDateTime().minute &&
            currentTime.getToLocalDateTime().hour == task.timestamp.getToLocalDateTime().second &&
            currentTime.after(Date(task.endTime.time - 24 * 60 * 60 * 1000))
        ) {
            notifications.sendTaskExpirationNotification(
                context = context,
                isExpired = false,
                taskId = task.id,
                taskName = task.productName
            )
        }

    }

    private suspend fun replaceTask() {
        appRepository.insertTask(_task.value)
    }

    private fun updateTaskStatus(
        task: Task,
        statusTask: String
    ) {
        _task.update {
            it.copy(
                id = task.id,
                client = task.client,
                timestamp = task.timestamp,
                statusTask = statusTask,
                description = task.description,
                productName = task.productName,
                productPrice = task.productPrice,
                delivery = task.delivery,
                endTime = task.endTime
            )
        }
    }

    fun dropDownItemSelected(context: Context) {
        viewModelScope.launch {
            val signOut = context.getString(R.string.sign_out)
            val settings = context.getString(R.string.settings)
            when (_sortOrder.value) {
                signOut -> {
                    _sortOrder.value = ""
                    userRepository.signOut()
                    _navigateToLogin.value = true
                }

                settings -> {
                    _sortOrder.value = ""
                    _navigateToSettings.value = true
                }
            }
        }
    }

    fun setSortOrder(sortOrder: String) {
        _sortOrder.value = sortOrder
    }

    fun setDialog(task: Task?) {
        _dialog.value = task
    }

    fun resetNavigation() {
        _navigateToLogin.value = false
        _navigateToSettings.value = false
    }

    override fun deleteTask(task: Task) {
        viewModelScope.launch {
            appRepository.deleteTask(task)
        }
    }

    override fun finishTask(task: Task) {
        viewModelScope.launch {
            updateTaskStatus(
                task,
                TaskByStatusSortOrder.DONE.name
            )
            replaceTask()
        }
    }
}