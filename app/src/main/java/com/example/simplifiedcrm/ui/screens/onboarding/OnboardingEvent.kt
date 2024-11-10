package com.example.simplifiedcrm.ui.screens.onboarding

import android.content.Context

interface OnboardingEvent {
    fun registerNewUser(context: Context)
    fun signIn(login: String, password: String)
    fun updateName(newName: String)
    fun updateLogin(newLogin: String)
    fun updatePassword(newPassword: String)
    fun resetError()
    fun resetUser()
}
