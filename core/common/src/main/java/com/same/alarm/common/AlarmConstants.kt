package com.same.alarm.common

object AlarmConstants {
    const val WEEK_INTERVAL_MILLIS: Long = 7 * 24 * 60 * 60 * 1000
    const val ALARM_INTERVAL_MILLS: Long = 5 * 60 * 1000
    const val BUNDLE_KEY_ALARM_ID = "BUNDLE_KEY_ALARM_ID"
    const val BUNDLE_KEY_REPEAT_COUNT = "BUNDLE_KEY_REPEAT_COUNT"
    const val ACTION_NAME = "ADD_ALARM"
    const val WAKE_LOCK_TIME_OUT = 5_000L
    const val WAKE_LOCK_TAG = "MyApp:WakeLockTag"
    const val REPEAT_COUNT = 3
    const val NOTIFICATION_ID = 1
}