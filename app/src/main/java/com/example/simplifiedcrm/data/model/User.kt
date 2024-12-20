package com.example.simplifiedcrm.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String = "",
    val login: String = "",
    val password: String = ""
)
