package com.example.firebasedemoapp

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class Notifications() {

    val notifyTag = "New request"

    fun notify(context: Context, message: String, number: Int) {
        val intent = Intent(context, MainActivity::class.java)
        val builder = NotificationCompat.Builder(context, "0")
            .setDefaults(Notification.DEFAULT_ALL)
            .setContentTitle("New request")
            .setContentText(message)
            .setNumber(number)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))
            .setAutoCancel(false)

        val nM = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nM.notify(notifyTag, 0, builder.build())
    }
}