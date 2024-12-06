package com.example.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

@Composable
fun NotificationApp() {
    val context = LocalContext.current

    Button(onClick = {
        sendNotificationWithDelay(context, 10000)
    }) {
        Text("Уведомить")
    }
}

@SuppressLint("MissingPermission")
private fun sendNotificationWithDelay(context: Context, delayMillis: Long) {
    Handler(Looper.getMainLooper()).postDelayed({
        sendNotification(context)
    }, delayMillis)
}

@SuppressLint("MissingPermission")
private fun sendNotification(context: Context) {
    val channelId = "default_channel"
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            "Default Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Канал для уведомлений"
        }
        notificationManager.createNotificationChannel(channel)
    }

    val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("Напоминание")
        .setContentText("Это ваше уведомление!")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()

    NotificationManagerCompat.from(context).notify(1, notification)
}
