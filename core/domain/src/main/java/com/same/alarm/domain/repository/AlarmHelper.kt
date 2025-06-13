package com.same.alarm.domain.repository

import com.same.alarm.model.alarm.Alarm

interface AlarmHelper {
    fun scheduleAlarm(alarm: Alarm)
    fun unScheduleAlarm(alarm: Alarm)
    fun unSetTodayAlarms(alarm: Alarm)
}