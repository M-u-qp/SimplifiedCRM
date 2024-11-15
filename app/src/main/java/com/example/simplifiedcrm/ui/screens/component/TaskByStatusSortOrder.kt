package com.example.simplifiedcrm.ui.screens.component

import android.content.Context
import com.example.simplifiedcrm.R

enum class TaskByStatusSortOrder {
    ACTIVE,
    EXPIRED,
    DONE;

    fun getStringResource(context: Context): String {
        return when (this) {
            ACTIVE -> context.getString(R.string.active)
            EXPIRED -> context.getString(R.string.expired)
            DONE -> context.getString(R.string.done)
        }
    }
}