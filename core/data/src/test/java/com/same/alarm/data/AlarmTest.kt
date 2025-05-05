package com.same.alarm.data

import com.same.alarm.model.Alarm
import org.junit.Test
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class AlarmTest {

    @Test
    fun 알람_첫_번째_트리거_시간_계산() {
        val alarm = Alarm(
            id = 1,
            time = LocalTime.of(9, 0),
            title = "Test Alarm",
            statusMessage = "Test",
            daysOfWeek = listOf(1, 3, 5, 6),
            category = "Test",
            isRepeating = true
        )

        alarm.daysOfWeek.forEach { dayOfWeek ->
            val millis = alarm.getAlarmFirstTriggerMillis(dayOfWeek)
            println("First trigger millis for day $dayOfWeek: ${formatMillisToDate(millis)}")
        }

        val nowMillis = System.currentTimeMillis()
        alarm.daysOfWeek.forEach { dayOfWeek ->
            val millis = alarm.getAlarmFirstTriggerMillis(dayOfWeek)
            assert(millis > nowMillis)
        }
    }
}

fun formatMillisToDate(millis: Long): String {
    val instant = Instant.ofEpochMilli(millis)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss E")
    return instant.atZone(ZoneId.systemDefault()).format(formatter)
}