package com.same.alarm.data.model

import java.time.DayOfWeek
import java.time.LocalTime

data class AlarmEntity (
    val id: String,
    val time: LocalTime,
    val title: String,
    val daysOfWeek: Set<DayOfWeek>
)