package com.same.alarm.data.model.alarm

data class AlarmRemoteEntity (
    val title: String,
    val statusMessage: String,
    val category: String,
    val time: String,
    val daysOfWeek: List<Int>,
    val isRepeating: Boolean
)