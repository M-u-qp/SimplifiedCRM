package com.example.simplifiedcrm.ui.screens.onboarding

interface OnboardingEvent {
    fun registerNewUser()
    fun signIn(login: String, password: String)
    fun updateName(newName: String)
    fun updateLogin(newLogin: String)
    fun updatePassword(newPassword: String)
    fun resetError()
    fun resetUser()
}
