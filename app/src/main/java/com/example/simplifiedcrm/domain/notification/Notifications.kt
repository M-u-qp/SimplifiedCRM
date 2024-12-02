package com.example.simplifiedcrm.domain.notification

import android.content.Context

interface Notifications {
    fun sendTaskExpirationNotification(
        context: Context,
        taskId: Int,
        taskName: String,
        isExpired: Boolean
    )

    fun scheduleTaskExpirationNotification(
        context: Context,
        taskId: Int,
        taskName: String,
        isExpired: Boolean,
        triggerAtMillis: Long
    )
}