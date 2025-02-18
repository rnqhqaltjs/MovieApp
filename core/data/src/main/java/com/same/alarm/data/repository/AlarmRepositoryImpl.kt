package com.same.alarm.data.repository

import android.app.AlarmManager
import android.content.Context
import com.same.alarm.data.datasource.AlarmDataSource
import com.same.alarm.data.mapper.AlarmMapper.toEntity
import com.same.alarm.domain.repository.AlarmRepository
import com.same.alarm.model.Alarm
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val context: Context,
    private val alarmDataSource: AlarmDataSource
) : AlarmRepository {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override suspend fun addAlarm(alarm: Alarm) {
        alarmDataSource.addAlarm(alarm.toEntity())

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