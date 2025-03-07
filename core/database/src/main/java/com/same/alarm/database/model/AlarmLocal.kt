package com.same.alarm.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime

@Entity(tableName = "alarms")
data class AlarmLocal (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val time: LocalTime,
    val statusMessage: String,
    val daysOfWeek: List<Int>,
    val category: String,
    val isRepeating: Boolean,
    val isActive: Boolean
)