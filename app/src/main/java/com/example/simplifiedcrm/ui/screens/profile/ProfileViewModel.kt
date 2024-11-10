package com.example.simplifiedcrm.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplifiedcrm.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    private val _navigateToLogin = MutableStateFlow(true)
    val navigateToLogin: StateFlow<Boolean> = _navigateToLogin
    fun signOut() {
        viewModelScope.launch {
            userRepository.signOut()
            _navigateToLogin.value = false
        }
    }
}