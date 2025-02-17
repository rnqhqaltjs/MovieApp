package com.same.alarm.data.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.same.alarm.domain.repository.AlarmRepository
import com.same.alarm.model.Alarm
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val context: Context
) : AlarmRepository {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun addAlarm(alarm: Alarm) {

//        val intent = Intent(context, AlarmReceiver::class.java).apply {
//            action = AlarmReceiver.ACTION_NAME
//            putExtra(AlarmReceiver.BUNDLE_KEY_ALARM_ID, alarm.id)
//        }
//
//        val pendingIntent = PendingIntent.getBroadcast(
//            context,
//            alarm.id,
//            intent,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//
//        alarmManager.setAlarmClock(
//            AlarmManager.AlarmClockInfo(alarm.timeStamp, pendingIntent),
//            pendingIntent
//        )
    }
}