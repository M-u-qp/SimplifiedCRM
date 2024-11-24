package com.example.simplifiedcrm.background.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.simplifiedcrm.R
import com.example.simplifiedcrm.domain.notification.Notifications

class NotificationsImpl : Notifications {

    companion object {
        const val CHANNEL_ID = "channel_id"
        const val CHANNEL_NAME = "channel_name"
    }

    override fun sendTaskExpirationNotification(
        context: Context,
        taskId: Int,
        taskName: String,
        isExpired: Boolean
    ) {
        val taskExpiredText = context.getString(R.string.task_expired)
        val taskWillExpireSoonText = context.getString(R.string.task_will_expire_soon)
        val taskWillExpireSoon24hoursText = context.getString(R.string.task_expired_24_hours)
        val taskText = context.getString(R.string.task)
        val notificationDisabled = context.getString(R.string.notifications_disabled)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (!areNotificationsEnabled(context)) {
            Log.w("Notification", notificationDisabled)
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            val channel = notificationManager.getNotificationChannel(CHANNEL_ID)
            if (channel == null) {
                val newChannel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                )
                notificationManager.createNotificationChannel(newChannel)
            }
        }
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.icons8_tasks)
            .setContentTitle(if (isExpired) taskExpiredText else taskWillExpireSoonText)
            .setContentText("$taskText [$taskName]. ${if (isExpired) taskExpiredText else taskWillExpireSoon24hoursText}")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        notificationManager.notify(taskId, notificationBuilder.build())
    }

    private fun areNotificationsEnabled(context: Context): Boolean {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            val channel = notificationManager.getNotificationChannel(CHANNEL_ID)
            channel?.importance != NotificationManager.IMPORTANCE_NONE
        } else {
            true
        }
    }
}