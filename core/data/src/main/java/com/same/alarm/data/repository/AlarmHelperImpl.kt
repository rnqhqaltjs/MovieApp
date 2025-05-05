package com.same.alarm.data.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import com.same.alarm.common.AlarmConstants.ACTION_NAME
import com.same.alarm.common.AlarmConstants.ALARM_INTERVAL_MILLS
import com.same.alarm.common.AlarmConstants.BUNDLE_KEY_ALARM_ID
import com.same.alarm.common.AlarmConstants.REPEAT_COUNT
import com.same.alarm.common.AlarmConstants.WEEK_INTERVAL_MILLIS
import com.same.alarm.domain.repository.AlarmHelper
import com.same.alarm.infra.receiver.AlarmReceiver
import com.same.alarm.model.Alarm
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDate
import javax.inject.Inject

class AlarmHelperImpl @Inject constructor(
    @ApplicationContext private val context: Context
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

    override fun unSetTodayAlarms(alarm: Alarm) {
        val todayDayOfWeek = LocalDate.now().dayOfWeek.value

        when {
            alarm.isRepeating && alarm.daysOfWeek.contains(todayDayOfWeek) -> {
                repeat(REPEAT_COUNT) { repeatCount ->
                    val requestId = generateUniqueId(alarm.id, todayDayOfWeek, repeatCount)
                    val pendingIntent = getPendingIntent(alarm.id, requestId)

                    pendingIntent.cancel()
                    alarmManager.cancel(pendingIntent)
                }
            }
            alarm.isRepeating -> {
                repeat(REPEAT_COUNT) { repeatCount ->
                    val requestId = generateUniqueId(alarm.id, repeatCount = repeatCount)
                    val pendingIntent = getPendingIntent(alarm.id, requestId)

                    pendingIntent.cancel()
                    alarmManager.cancel(pendingIntent)
                }
            }
        }
    }

    private fun setNonRepeatingAlarm(alarm: Alarm) {
        val requestId = generateUniqueId(alarm.id)
        val pendingIntent = getPendingIntent(alarm.id, requestId)

        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(alarm.getAlarmFirstTriggerMillis(), pendingIntent),
            pendingIntent
        )
    }

    private fun unSetNonRepeatingAlarm(alarm: Alarm) {
        val requestId = generateUniqueId(alarm.id)
        val pendingIntent = getPendingIntent(alarm.id, requestId)

        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
    }

    private fun setDayOfWeekRepeatingAlarm(alarm: Alarm) {
        alarm.daysOfWeek.forEach { dayOfWeek ->
            val firstAlarmTriggerMillis = alarm.getAlarmFirstTriggerMillis(dayOfWeek)
            val requestId = generateUniqueId(alarm.id, dayOfWeek = dayOfWeek)
            val pendingIntent = getPendingIntent(alarm.id, requestId)

            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                firstAlarmTriggerMillis,
                WEEK_INTERVAL_MILLIS,
                pendingIntent
            )
        }
    }

    private fun unSetDayOfWeekRepeatingAlarm(alarm: Alarm) {
        alarm.daysOfWeek.forEach { dayOfWeek ->
            val requestId = generateUniqueId(alarm.id, dayOfWeek = dayOfWeek)
            val pendingIntent = getPendingIntent(alarm.id, requestId)

            pendingIntent.cancel()
            alarmManager.cancel(pendingIntent)
        }
    }

    private fun setIntervalRepeatingAlarm(alarm: Alarm) {
        repeat(REPEAT_COUNT) { repeatCount ->
            val firstAlarmTriggerMillis =
                alarm.getAlarmFirstTriggerMillis() + (repeatCount * ALARM_INTERVAL_MILLS)
            val requestId = generateUniqueId(alarm.id, repeatCount = repeatCount)
            val pendingIntent = getPendingIntent(alarm.id, requestId)

            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(firstAlarmTriggerMillis, pendingIntent),
                pendingIntent
            )
        }
    }

    private fun unSetIntervalRepeatingAlarm(alarm: Alarm) {
        repeat(REPEAT_COUNT) { repeatCount ->
            val requestId = generateUniqueId(alarm.id, repeatCount = repeatCount)
            val pendingIntent = getPendingIntent(alarm.id, requestId)

            pendingIntent.cancel()
            alarmManager.cancel(pendingIntent)
        }
    }

    private fun setDayOfWeekAndIntervalRepeatingAlarm(alarm: Alarm) {
        alarm.daysOfWeek.forEach { dayOfWeek ->
            repeat(REPEAT_COUNT) { repeatCount ->
                val firstAlarmTriggerMillis = alarm.getAlarmFirstTriggerMillis(dayOfWeek) + (repeatCount * ALARM_INTERVAL_MILLS)
                val requestId = generateUniqueId(alarm.id, dayOfWeek, repeatCount)
                val pendingIntent = getPendingIntent(alarm.id, requestId)

                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    firstAlarmTriggerMillis,
                    WEEK_INTERVAL_MILLIS,
                    pendingIntent
                )
            }
        }
    }

    private fun unSetDayOfWeekAndIntervalRepeatingAlarm(alarm: Alarm) {
        alarm.daysOfWeek.forEach { dayOfWeek ->
            repeat(REPEAT_COUNT) { repeatCount ->
                val requestId = generateUniqueId(alarm.id, dayOfWeek, repeatCount)
                val pendingIntent = getPendingIntent(alarm.id, requestId)

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

    private fun generateUniqueId(alarmId: Int, dayOfWeek: Int? = null, repeatCount: Int? = null): Int {
        return when {
            dayOfWeek != null && repeatCount != null -> "$alarmId-$dayOfWeek-$repeatCount".hashCode()
            dayOfWeek != null -> "$alarmId-$dayOfWeek".hashCode()
            repeatCount != null -> "$alarmId-$repeatCount".hashCode()
            else -> alarmId.hashCode()
        }
    }
}