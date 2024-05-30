package com.beapps.thedoctorapp.core.presentation

import android.app.NotificationManager
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.beapps.thedoctorapp.R
import com.beapps.thedoctorapp.core.domain.AuthCredentialsManager
import com.beapps.thedoctorapp.core.domain.NotificationChannelItem
import com.beapps.thedoctorapp.core.domain.NotificationHelper
import com.beapps.thedoctorapp.core.domain.NotificationItem
import com.beapps.thedoctorapp.core.domain.SyncManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SyncingForegroundService : LifecycleService() {

    @Inject
    lateinit var syncManager: SyncManager

    @Inject
    lateinit var authCredentialsManager: AuthCredentialsManager
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("ab_do", "onStartCommand")

        super.onStartCommand(intent, flags, startId)

        val notificationItem = NotificationItem(
            "Syncing graphs .. ",
            "The app is Currently Syncing graphs of Your Patients.",
            R.drawable.ic_launcher_foreground,
            NotificationCompat.CATEGORY_SERVICE
        )

        val notificationChannelItem = NotificationChannelItem(
            "SyncingChannel",
            "Syncing Data Channel",
            "syncing the graphs of Doctor Patients",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notification =
            NotificationHelper.createNotification(this, notificationItem, notificationChannelItem)

        startForeground(NOTIFICATION_ID, notification)

        // Your service logic here, e.g., starting a long-running task in a new thread or coroutine
        lifecycleScope.launch {
            authCredentialsManager.getCurrentLoggedInDoctor()?.let {
                val result = syncManager.syncGraphsForDoctorPatients(it)
                Log.d("ab_do", "syncing result: $result")
                stopSelf()
            }
        }


        return START_NOT_STICKY
    }


    companion object {
        const val NOTIFICATION_ID = 1
    }
}
