package com.example.simplifiedcrm.ui.screens.onboarding

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplifiedcrm.R
import com.example.simplifiedcrm.data.model.User
import com.example.simplifiedcrm.data.repository.UserRepository
import com.example.simplifiedcrm.data.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel(), OnboardingEvent {

    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    private val _navigateToHome = MutableStateFlow(false)
    val navigateToHome: StateFlow<Boolean> = _navigateToHome

    private val _error = mutableStateOf("")
    val error: State<String> = _error

    init {
        checkUserSignInStatus()
    }

    private fun checkUserSignInStatus() {
        viewModelScope.launch {
            val isSignIn = userRepository.isUserSignedIn()
            if (isSignIn) {
                _navigateToHome.value = true
            }
        }
    }

   override fun resetUser() {
        _user.update {
            it.copy(
                name = "",
                login = "",
                password = ""
            )
        }
    }

    override fun registerNewUser(context: Context) {
        viewModelScope.launch {
            if (user.value.name.isBlank() || user.value.login.isBlank() || user.value.password.isBlank()) {
                _error.value = context.getString(R.string.fields_cannot_be_empty)
            } else {
                when (val result = userRepository.registerNewUser(user.value)) {
                    is Result.Success -> { _navigateToHome.value = true }
                    is Result.Error -> { _error.value = result.message }
                }
            }
        }
    }

    override fun signIn(login: String, password: String) {
        viewModelScope.launch {
            when (val result = userRepository.signIn(login, password)) {
                is Result.Success -> { _navigateToHome.value = true }
                is Result.Error -> { _error.value = result.message }
            }
        }
    }

    override fun updateName(newName: String) {
        _user.update { it.copy(name = newName) }
    }

    override fun updateLogin(newLogin: String) {
        _user.update { it.copy(login = newLogin) }
    }

    override fun updatePassword(newPassword: String) {
        _user.update { it.copy(password = newPassword) }
    }

    override fun resetError() {
        _error.value = ""
    }
}