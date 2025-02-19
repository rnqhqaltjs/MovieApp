package com.same.alarm.database.typeconverter

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class LocalTimeConverter {
    @TypeConverter
    fun fromLocalTime(localTime: LocalTime): String {
        return localTime.format(DateTimeFormatter.ISO_LOCAL_TIME)
    }

    @TypeConverter
    fun toLocalTime(timeString: String): LocalTime {
        return LocalTime.parse(timeString, DateTimeFormatter.ISO_LOCAL_TIME)
    }
}