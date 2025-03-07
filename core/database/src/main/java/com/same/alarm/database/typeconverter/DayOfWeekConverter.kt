package com.same.alarm.database.typeconverter

import androidx.room.TypeConverter
import java.time.DayOfWeek

class DayOfWeekConverter {
    @TypeConverter
    fun fromDayOfWeekList(daysOfWeek: List<Int>): String {
        return daysOfWeek.joinToString(",")
    }

    @TypeConverter
    fun toDayOfWeekList(data: String): List<Int> {
        return data.split(",").map { it.toInt() }
    }
}