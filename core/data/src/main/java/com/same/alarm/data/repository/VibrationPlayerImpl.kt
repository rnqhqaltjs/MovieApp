package com.same.alarm.data.repository

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresApi
import com.same.alarm.common.PlayerUtils.createAlarmAttributes
import com.same.alarm.domain.repository.RingPlayer
import com.same.alarm.domain.repository.VibrationPlayer
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.S)
class VibrationPlayerImpl @Inject constructor(
    private val context: Context
) : VibrationPlayer {
    private val vibrator: Vibrator = context.getSystemService(VibratorManager::class.java).defaultVibrator

    override fun start() {
        val pattern = longArrayOf(0, 500, 1000)
        val effect = VibrationEffect.createWaveform(pattern, 0)

        vibrator.vibrate(effect, createAlarmAttributes())
    }

    override fun stop() {
        vibrator.cancel()
    }
}