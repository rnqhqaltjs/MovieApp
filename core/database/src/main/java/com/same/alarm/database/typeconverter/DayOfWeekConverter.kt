package com.same.alarm.database.typeconverter

import androidx.room.TypeConverter
import java.time.DayOfWeek

class DayOfWeekConverter {
    @TypeConverter
    fun fromDayOfWeekSet(daysOfWeek: Set<DayOfWeek>): String {
        return daysOfWeek.joinToString(",")
    }

    @TypeConverter
    fun toDayOfWeekSet(data: String): Set<DayOfWeek> {
        return data.split(",").map { DayOfWeek.valueOf(it) }.toSet()
    }
}