package com.example.simplifiedcrm.background.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.simplifiedcrm.R
import com.example.simplifiedcrm.background.notification.NotificationReceiver.Companion.EXTRA_IS_EXPIRED
import com.example.simplifiedcrm.background.notification.NotificationReceiver.Companion.EXTRA_TASK_ID
import com.example.simplifiedcrm.background.notification.NotificationReceiver.Companion.EXTRA_TASK_NAME
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

    override fun scheduleTaskExpirationNotification(
        context: Context,
        taskId: Int,
        taskName: String,
        isExpired: Boolean,
        triggerAtMillis: Long
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                context.startActivity(intent)
                return
            }
        }
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra(EXTRA_TASK_ID, taskId)
            putExtra(EXTRA_TASK_NAME, taskName)
            putExtra(EXTRA_IS_EXPIRED, isExpired)
        }
        val pendingIntent =
            PendingIntent.getBroadcast(
                context, taskId, intent, PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )
        try {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
        } catch (e: SecurityException) {
            Toast.makeText(context, "Разрешения не предоставлены", Toast.LENGTH_SHORT).show()
        }
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