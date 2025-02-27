package com.same.alarm

class AlarmConstants {
    companion object {
        const val WEEK_INTERVAL_MILLIS: Long = 7 * 24 * 60 * 60 * 1000
        const val ALARM_DURATION_MILLIS: Long = 30 * 1000
        const val ALARM_INTERVAL_MILLS: Long = 1 * 60 * 1000

        val DAYS = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    }
}