package com.same.alarm.common

import android.media.AudioAttributes

object PlayerUtils {
    fun createAlarmAttributes(): AudioAttributes {
        return AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ALARM)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
    }
}