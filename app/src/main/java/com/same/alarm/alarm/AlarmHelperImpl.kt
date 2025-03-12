package com.same.alarm.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import com.same.alarm.alarm.AlarmConstants.Companion.ACTION_NAME
import com.same.alarm.alarm.AlarmConstants.Companion.ALARM_INTERVAL_MILLS
import com.same.alarm.alarm.AlarmConstants.Companion.BUNDLE_KEY_ALARM_ID
import com.same.alarm.alarm.AlarmConstants.Companion.REPEAT_COUNT
import com.same.alarm.domain.repository.AlarmHelper
import com.same.alarm.model.Alarm
import javax.inject.Inject

class AlarmHelperImpl @Inject constructor(
    private val context: Context
) : AlarmHelper {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun scheduleAlarm(alarm: Alarm) {
        when {
            alarm.isRepeating && alarm.daysOfWeek.isNotEmpty() -> setDayOfWeekAndIntervalRepeatingAlarm(alarm)
            alarm.isRepeating -> setIntervalRepeatingAlarm(alarm)
            alarm.daysOfWeek.isNotEmpty() -> setDayOfWeekRepeatingAlarm(alarm)
            else -> setNonRepeatingAlarm(alarm)
        }
    }

    override fun unScheduleAlarm(alarm: Alarm) {
        when {
            alarm.isRepeating && alarm.daysOfWeek.isNotEmpty() -> unSetDayOfWeekAndIntervalRepeatingAlarm(alarm)
            alarm.isRepeating -> unSetIntervalRepeatingAlarm(alarm)
            alarm.daysOfWeek.isNotEmpty() -> unSetDayOfWeekRepeatingAlarm(alarm)
            else -> unSetNonRepeatingAlarm(alarm)
        }
    }

    private fun setNonRepeatingAlarm(alarm: Alarm) {
        val pendingIntent = getPendingIntent(alarm.id, alarm.id)

        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(alarm.getAlarmFirstTriggerMillis(), pendingIntent),
            pendingIntent
        )
    }

    private fun unSetNonRepeatingAlarm(alarm: Alarm) {
        val pendingIntent = getPendingIntent(alarm.id, alarm.id)

        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
    }

    private fun setDayOfWeekRepeatingAlarm(alarm: Alarm) {
        alarm.daysOfWeek.forEach { dayOfWeek ->
            val firstAlarmTriggerMillis = alarm.getAlarmFirstTriggerMillis(dayOfWeek)
            val pendingIntent = getPendingIntent(alarm.id, alarm.id * 10 + dayOfWeek)

            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                firstAlarmTriggerMillis,
                AlarmConstants.WEEK_INTERVAL_MILLIS,
                pendingIntent
            )
        }
    }

    private fun unSetDayOfWeekRepeatingAlarm(alarm: Alarm) {
        alarm.daysOfWeek.forEach { dayOfWeek ->
            val pendingIntent = getPendingIntent(alarm.id, alarm.id * 10 + dayOfWeek)

            pendingIntent.cancel()
            alarmManager.cancel(pendingIntent)
        }
    }

    private fun setIntervalRepeatingAlarm(alarm: Alarm) {
        repeat(REPEAT_COUNT) { repeatCount ->
            val firstAlarmTriggerMillis =
                alarm.getAlarmFirstTriggerMillis() + (repeatCount * ALARM_INTERVAL_MILLS)
            val pendingIntent = getPendingIntent(alarm.id, alarm.id * 100 + repeatCount)

            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(firstAlarmTriggerMillis, pendingIntent),
                pendingIntent
            )
        }
    }

    private fun unSetIntervalRepeatingAlarm(alarm: Alarm) {
        repeat(REPEAT_COUNT) { repeatCount ->
            val pendingIntent = getPendingIntent(alarm.id, alarm.id * 100 + repeatCount)

            pendingIntent.cancel()
            alarmManager.cancel(pendingIntent)
        }
    }

    private fun setDayOfWeekAndIntervalRepeatingAlarm(alarm: Alarm) {
        alarm.daysOfWeek.forEach { dayOfWeek ->
            repeat(REPEAT_COUNT) { repeatCount ->
                val firstAlarmTriggerMillis = alarm.getAlarmFirstTriggerMillis(dayOfWeek) + (repeatCount * ALARM_INTERVAL_MILLS)
                val pendingIntent = getPendingIntent(alarm.id, alarm.id * 1000 + dayOfWeek * 10 + repeatCount)

                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    firstAlarmTriggerMillis,
                    AlarmConstants.WEEK_INTERVAL_MILLIS,
                    pendingIntent
                )
            }
        }
    }

    private fun unSetDayOfWeekAndIntervalRepeatingAlarm(alarm: Alarm) {
        alarm.daysOfWeek.forEach { dayOfWeek ->
            repeat(REPEAT_COUNT) { repeatCount ->
                val pendingIntent = getPendingIntent(alarm.id, alarm.id * 1000 + dayOfWeek * 10 + repeatCount)

                pendingIntent.cancel()
                alarmManager.cancel(pendingIntent)
            }
        }
    }

    private fun getPendingIntent(alarmId: Int, requestId: Int): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = ACTION_NAME
            putExtra(BUNDLE_KEY_ALARM_ID, alarmId)
        }

        return PendingIntent.getBroadcast(
            context, requestId, intent, FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
        )
    }
}