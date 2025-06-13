package com.same.alarm.data.model.alarm

import java.time.LocalTime

data class AlarmRemoteEntity (
    val title: String,
    val statusMessage: String,
    val category: String,
    val time: LocalTime,
    val daysOfWeek: List<Int>,
    val isRepeating: Boolean
)