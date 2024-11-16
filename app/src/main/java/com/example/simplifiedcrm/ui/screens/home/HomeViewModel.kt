package com.example.simplifiedcrm.ui.screens.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.simplifiedcrm.R
import com.example.simplifiedcrm.data.local.database.entity.Task
import com.example.simplifiedcrm.data.repository.AppRepository
import com.example.simplifiedcrm.data.repository.UserRepository
import com.example.simplifiedcrm.ui.screens.component.TaskByStatusSortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val userRepository: UserRepository
) : ViewModel() {
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
}