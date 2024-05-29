package com.beapps.thedoctorapp.core.domain

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.beapps.thedoctorapp.R

object NotificationHelper {
     fun createNotification(context: Context , notificationItem: NotificationItem , notificationChannelItem: NotificationChannelItem): Notification {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(context , notificationChannelItem)
        }

        return NotificationCompat.Builder(context, notificationChannelItem.id)
            .setContentTitle(notificationItem.title)
            .setContentText(notificationItem.content)
            .setSmallIcon(notificationItem.smallIcon)
            .setCategory(notificationItem.category) // Set category for foreground service
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel (context: Context, notificationChannelItem: NotificationChannelItem) {
        val channel = NotificationChannel(
            notificationChannelItem.id,
            notificationChannelItem.name,
            notificationChannelItem.importance
        ).apply {
            description = notificationChannelItem.description
        }
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

data class NotificationItem (
    val title : String,
    val content : String ,
    @DrawableRes val smallIcon : Int ,
    val category: String
)

data class NotificationChannelItem (
    val id : String,
    val name : String,
    val description : String,
    val importance : Int
)