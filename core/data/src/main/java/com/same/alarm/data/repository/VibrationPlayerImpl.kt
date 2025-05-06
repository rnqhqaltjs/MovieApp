package com.same.alarm.data.repository

import android.content.Context
import android.media.AudioAttributes
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresApi
import com.same.alarm.domain.repository.VibrationPlayer
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.S)
class VibrationPlayerImpl @Inject constructor(
    private val context: Context
) : VibrationPlayer {
    private val vibrator: Vibrator = context.getSystemService(VibratorManager::class.java).defaultVibrator

    override fun startVibration() {
        val pattern = longArrayOf(0, 500, 1000) // 0ms 후 시작, 500ms 진동, 1000ms 멈춤
        val effect = VibrationEffect.createWaveform(pattern, 0)

        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_ALARM)
            .build()
            
        vibrator.vibrate(effect, audioAttributes)
    }

    override fun stopVibration() {
        vibrator.cancel()
    }
}