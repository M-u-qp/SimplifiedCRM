package com.example.simplifiedcrm.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.simplifiedcrm.R
import com.example.simplifiedcrm.data.model.User
import com.example.simplifiedcrm.data.utils.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStore: DataStore<Preferences>
) {
    private val errorRegisterText = context.getString(R.string.login_already_exists)
    private val errorLoginText = context.getString(R.string.username_or_password_was_entered_incorrectly)
    companion object {
        val USER_DATA = stringPreferencesKey("user_data")
        val USER_SIGN_IN = booleanPreferencesKey("user_sign_in")
    }

    //Получить всех пользователей
    private suspend fun getAllUsers(): Map<String, User> {
        val userDataJson = dataStore.data.map {
            it[USER_DATA] ?: "{}"
        }.first()
        return jsonToUserData(userDataJson)
    }

    //Регистрация нового пользователя
    suspend fun registerNewUser(user: User): Result<Boolean> {
        val users = getAllUsers()
        if (users.containsKey(user.login)) {
            return Result.Error(errorRegisterText)
        }
        val updatedUsers = users.toMutableMap()
        updatedUsers[user.login] = user
        saveUsers(updatedUsers)
        saveSignInState(true)
        return Result.Success(true)
    }

    //Вход в систему
    suspend fun signIn(login: String, password: String): Result<Boolean> {
        val users = getAllUsers()
        val user = users[login]
        return if (user != null && user.password == password) {
            saveSignInState(true)
            Result.Success(true)
        } else {
            Result.Error(errorLoginText)
        }
    }

    //Флаг входа в систему
    private suspend fun saveSignInState(isSignedIn: Boolean) {
        dataStore.edit { it[USER_SIGN_IN] = isSignedIn }
    }

    //Выход из системы
    suspend fun signOut() {
        saveSignInState(false)
    }

    //Проверка флага, в системе ли пользователь?
    suspend fun isUserSignedIn(): Boolean {
        return dataStore.data.map { it[USER_SIGN_IN] ?: false }.first()
    }

    //Сохранение пользователей в памяти в Json
    private suspend fun saveUsers(users: Map<String, User>) {
        val userDataJson = userDataToJson(users)
        dataStore.edit { it[USER_DATA] = userDataJson }
    }

    //Конвертация Json в UserData
    private fun jsonToUserData(json: String): Map<String, User> {
        return Json.decodeFromString<Map<String, User>>(json)
    }

    //Конвертация UserData в Json
    private fun userDataToJson(users: Map<String, User>): String {
        return Json.encodeToString(users)
    }
}