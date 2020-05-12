package com.example.notificationchannel

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi

class NotificationHelper(context: Context): ContextWrapper(context) {

    private val manager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chan1 = NotificationChannel(FIRST_CHANNEL, "First Channel", NotificationManager.IMPORTANCE_DEFAULT)
            chan1.lightColor = Color.GREEN
            chan1.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            manager.createNotificationChannel(chan1)

            val chan2 = NotificationChannel(SECOND_CHANNEL, "Second Channel", NotificationManager.IMPORTANCE_DEFAULT)
            chan2.lightColor = Color.GREEN
            chan2.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            manager.createNotificationChannel(chan2)

            val chan3 = NotificationChannel(THIRD_CHANNEL, "Third Channel", NotificationManager.IMPORTANCE_DEFAULT)
            chan3.lightColor = Color.GREEN
            chan3.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            manager.createNotificationChannel(chan3)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getNotify1(title: String, body: String): Notification.Builder {
        return Notification.Builder(applicationContext, FIRST_CHANNEL)
            .setContentText(body)
            .setContentTitle(title)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setAutoCancel(false)
    }

    fun notify(id: Int, notification: Notification.Builder) {
        manager.notify(id, notification.build())
    }

    companion object {
        const val FIRST_CHANNEL = "first"
        const val SECOND_CHANNEL = "second"
        const val THIRD_CHANNEL = "third"
    }
}