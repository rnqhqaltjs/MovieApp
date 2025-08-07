package com.same.alarm.model.alarm

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

data class Alarm(
    val id: Int = 0,
    val time: LocalTime,
    val title: String,
    val statusMessage: String,
    val daysOfWeek: List<Int>,
    val category: String,
    val isRepeating: Boolean,
    val isActive: Boolean = true,
    val isPinned: Boolean = false
) {
    private fun getAlarmFirstTrigger(
        dayOfWeek: Int? = null,
    ): LocalDateTime {
        var alarmDateTime = LocalDateTime.of(LocalDate.now(), time)

        val nowDateTime = LocalDateTime.now()
        val nowDayOfWeek = nowDateTime.dayOfWeek.ordinal  // 0 = Monday, 6 = Sunday
        val nowTime = nowDateTime.toLocalTime()

        val isTimeBefore = time < nowTime

        if (dayOfWeek != null) {
            val isRequiredDayBefore = dayOfWeek < nowDayOfWeek
            val isRequiredDaySame = dayOfWeek == nowDayOfWeek

            if (isRequiredDayBefore or (isRequiredDaySame and isTimeBefore)) {
                val daysToAdd = 7 - (nowDayOfWeek - dayOfWeek)
                alarmDateTime = alarmDateTime.plusDays(daysToAdd.toLong())
            } else {
                val daysToAdd = dayOfWeek - nowDayOfWeek
                alarmDateTime = alarmDateTime.plusDays(daysToAdd.toLong())
            }
        } else {
            if (isTimeBefore) {
                alarmDateTime = alarmDateTime.plusDays(1)
            }
        }
        return alarmDateTime
    }

    fun getAlarmFirstTriggerMillis(
        dayOfWeek: Int? = null,
    ): Long {
        return getAlarmFirstTrigger(dayOfWeek).atZone(ZoneId.systemDefault())
            .toEpochSecond() * 1000
    }
}