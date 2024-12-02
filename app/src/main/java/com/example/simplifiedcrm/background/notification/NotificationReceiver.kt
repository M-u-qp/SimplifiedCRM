package com.example.simplifiedcrm.background.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    companion object {
        const val EXTRA_TASK_ID = "task_id"
        const val EXTRA_TASK_NAME = "task_name"
        const val EXTRA_IS_EXPIRED = "is_expired"
    }
    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getIntExtra(EXTRA_TASK_ID, -1)
        val taskName = intent.getStringExtra(EXTRA_TASK_NAME) ?: ""
        val isExpired = intent.getBooleanExtra(EXTRA_IS_EXPIRED, false)

        NotificationsImpl().sendTaskExpirationNotification(context, taskId, taskName, isExpired)
    }
}