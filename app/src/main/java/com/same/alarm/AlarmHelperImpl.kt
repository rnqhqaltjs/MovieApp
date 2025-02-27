package com.same.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.same.alarm.data.datasource.AlarmHelper
import com.same.alarm.model.Alarm
import javax.inject.Inject

class AlarmHelperImpl @Inject constructor(
    private val context: Context
) : AlarmHelper {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun scheduleAlarm(alarm: Alarm) {
        when {
            alarm.isRepeating && alarm.daysOfWeek.isNotEmpty() -> {
            }
            alarm.isRepeating -> {
            }
            alarm.daysOfWeek.isNotEmpty() -> {
                setDayOfWeekRepeatingAlarm(alarm)
            }
            else -> {
                setNonRepeatingAlarm(alarm)
            }
        }
    }

    override fun unScheduleAlarm(alarm: Alarm) {
        if(alarm.daysOfWeek.isEmpty()) {
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context, alarm.id, intent, PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.cancel(pendingIntent)
        } else {
            alarm.daysOfWeek.forEach { dayOfWeek ->
                val intent = Intent(context, AlarmReceiver::class.java)

                val uniqueRequestId = alarm.id * 10 + dayOfWeek.ordinal

                val pendingIntent = PendingIntent.getBroadcast(
                    context, uniqueRequestId, intent, PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
                )

                alarmManager.cancel(pendingIntent)
            }
        }
    }

    private fun setNonRepeatingAlarm(alarm: Alarm) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = AlarmReceiver.ACTION_NAME
            putExtra(AlarmReceiver.BUNDLE_KEY_ALARM_ID, alarm.id)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context, alarm.id, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(alarm.getAlarmFirstTriggerMillis(), pendingIntent),
            pendingIntent
        )
    }

    private fun setDayOfWeekRepeatingAlarm(alarm: Alarm) {
        alarm.daysOfWeek.forEach { dayOfWeek ->
            val firstAlarmTriggerMillis = alarm.getAlarmFirstTriggerMillis(dayOfWeek.ordinal)

            val intent = Intent(context, AlarmReceiver::class.java).apply {
                action = AlarmReceiver.ACTION_NAME
                putExtra(AlarmReceiver.BUNDLE_KEY_ALARM_ID, alarm.id)
            }

            val uniqueRequestId = alarm.id * 10 + dayOfWeek.ordinal

            val pendingIntent = PendingIntent.getBroadcast(
                context, uniqueRequestId, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                firstAlarmTriggerMillis,
                AlarmConstants.WEEK_INTERVAL_MILLIS,
                pendingIntent
            )
        }
    }

    private fun setIntervalRepeatingAlarm(alarm: Alarm) {
        repeat(5) { repeatCount ->
            val firstAlarmTriggerMillis = alarm.getAlarmFirstTriggerMillis() + (repeatCount * AlarmConstants.ALARM_INTERVAL_MILLS)

            val intent = Intent(context, AlarmReceiver::class.java).apply {
                action = AlarmReceiver.ACTION_NAME
                putExtra(AlarmReceiver.BUNDLE_KEY_ALARM_ID, alarm.id)
            }

            val uniqueRequestId = alarm.id * 100 + repeatCount

            val pendingIntent = PendingIntent.getBroadcast(
                context, uniqueRequestId, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(firstAlarmTriggerMillis, pendingIntent),
                pendingIntent
            )
        }
    }
}