package com.same.alarm.data.datasource

import com.same.alarm.model.Alarm

interface AlarmHelper {
    fun scheduleAlarm(alarm: Alarm)
    fun unScheduleAlarm(alarm: Alarm)
}