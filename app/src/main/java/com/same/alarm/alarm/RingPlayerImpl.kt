package com.same.alarm.alarm

import android.content.Context
import android.media.AudioManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import com.same.alarm.domain.repository.RingPlayer
import javax.inject.Inject

class RingPlayerImpl @Inject constructor(
    private val context: Context
) : RingPlayer {
    private lateinit var ringtone: Ringtone

    override fun playRing() {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        if (audioManager.ringerMode == AudioManager.RINGER_MODE_VIBRATE ||
            audioManager.ringerMode == AudioManager.RINGER_MODE_SILENT) {
            audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
        }

        val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(context, uri)

        ringtone.play()
    }

    override fun stopRing() {
        if (::ringtone.isInitialized) {
            ringtone.stop()
        }
    }
}