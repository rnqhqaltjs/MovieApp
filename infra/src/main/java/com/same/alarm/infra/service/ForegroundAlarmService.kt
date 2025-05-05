package com.same.alarm.infra.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.same.alarm.common.AlarmConstants.NOTIFICATION_ID
import com.same.alarm.domain.repository.AlarmHelper
import com.same.alarm.domain.repository.AlarmRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class ForegroundAlarmService : Service() {

    @Inject
    lateinit var alarmRepository: AlarmRepository

    @Inject
    lateinit var alarmHelper: AlarmHelper

    override fun onCreate() {
        super.onCreate()
        startForegroundService()
        rescheduleAlarms()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun startForegroundService() {
        val channelId = "alarm_reschedule_channel"
        val notificationManager = getSystemService(NotificationManager::class.java)

        val channel = NotificationChannel(
            channelId,
            "Alarm Rescheduler",
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Alarm Rescheduling")
            .setContentText("We are rescheduling your alarms.")
            .build()
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun rescheduleAlarms() {
        CoroutineScope(Dispatchers.IO).launch {
            alarmRepository.getAllAlarms()
                .collect { alarms ->
                    alarms.filter { it.isActive }
                        .forEach { alarmHelper.scheduleAlarm(it) }
                    withContext(Dispatchers.Main) {
                        stopForeground(STOP_FOREGROUND_DETACH)
                    }
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSelf()
    }
}