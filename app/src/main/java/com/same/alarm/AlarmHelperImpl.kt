package com.same.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.same.alarm.data.datasource.AlarmHelper
import com.same.alarm.model.Alarm
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import javax.inject.Inject

class AlarmHelperImpl @Inject constructor(
    private val context: Context
) : AlarmHelper {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun setAlarm(alarm: Alarm) {

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = AlarmReceiver.ACTION_NAME
            putExtra(AlarmReceiver.BUNDLE_KEY_ALARM_ID, alarm.id)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context, alarm.id.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(localTimeToLong(alarm.time), pendingIntent),
            pendingIntent
        )
    }

    override fun cancelAlarm(alarm: Alarm) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, alarm.id.toInt(), intent, PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )

        pendingIntent?.let { alarmManager.cancel(it) }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun localTimeToLong(localTime: LocalTime): Long {
    val today = LocalDate.now()
    val localDateTime = today.atTime(localTime)
    return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}