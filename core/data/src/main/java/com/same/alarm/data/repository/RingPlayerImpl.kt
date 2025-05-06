package com.same.alarm.data.repository

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import com.same.alarm.domain.repository.RingPlayer
import javax.inject.Inject

class RingPlayerImpl @Inject constructor(
    private val context: Context
) : RingPlayer {
    private lateinit var ringtone: Ringtone

    override fun playRing() {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        audioManager.setStreamVolume(
            AudioManager.STREAM_ALARM,
            audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM),
            0
        )

        val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(context, uri)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ringtone.volume = 1.0f
        }

        ringtone.audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ALARM)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        ringtone.play()
    }

    override fun stopRing() {
        if (::ringtone.isInitialized) {
            ringtone.stop()
        }
    }
}