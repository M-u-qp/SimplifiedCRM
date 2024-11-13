package com.example.simplifiedcrm.ui.screens.task_creation

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplifiedcrm.R
import com.example.simplifiedcrm.data.local.database.entity.Task
import com.example.simplifiedcrm.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TaskCreationViewModel @Inject constructor(
    private val appRepository: AppRepository,
    @ApplicationContext context: Context
) : ViewModel(), TaskCreationEvent {
    private val _task = MutableStateFlow(Task())
    val task = _task.asStateFlow()

    private val _navigateToHome = MutableStateFlow(false)
    val navigateToHome: StateFlow<Boolean> = _navigateToHome

    private val _error = mutableStateOf("")
    val error: State<String> = _error

    private val cannotBeEmpty = context.getString(R.string.fields_cannot_be_empty)
    override fun createTask() {
        viewModelScope.launch {
            if (task.value.productName.isBlank() || task.value.productPrice == 0L) {
                _error.value = cannotBeEmpty
            } else {
                appRepository.insertTask(task.value)
                _navigateToHome.value = true
            }
        }
    }

    override fun deleteTask(task: Task) {
        viewModelScope.launch {
            appRepository.deleteTask(task)
        }
    }

    override fun resetError() {
        _error.value = ""
    }

    override fun updateClientName(name: String) {
        _task.update { it.copy(client = it.client.copy(name = name)) }
    }

    override fun updateClientPhone(phone: String) {
        _task.update { it.copy(client = it.client.copy(phone = phone)) }
    }

    override fun updateClientEmail(email: String) {
        _task.update { it.copy(client = it.client.copy(email = email)) }
    }

    override fun updateClientMarking(marking: String) {
        _task.update { it.copy(client = it.client.copy(marking = marking)) }
    }

    override fun updateDescription(description: String) {
        _task.update { it.copy(description = description) }
    }

    override fun updateProductName(productName: String) {
        _task.update { it.copy(productName = productName) }
    }

    override fun updateProductPrice(productPrice: Long) {
        _task.update { it.copy(productPrice = productPrice) }
    }

    override fun updateDeliveryName(name: String) {
        _task.update { it.copy(delivery = it.delivery.copy(name = name)) }
    }

    override fun updateDeliveryPrice(price: Long) {
        _task.update { it.copy(delivery = it.delivery.copy(price = price)) }
    }

    override fun updateStatusTask(statusTask: String) {
        _task.update { it.copy(statusTask = statusTask) }
    }

    override fun updateTimestamp(date: Date) {
        _task.update { it.copy(timestamp = date) }
    }
}