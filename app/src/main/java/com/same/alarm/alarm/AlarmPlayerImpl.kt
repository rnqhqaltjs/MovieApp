package com.same.alarm.alarm

import android.app.Activity
import android.content.Context
import android.media.AudioManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import com.same.alarm.domain.repository.AlarmPlayer
import javax.inject.Inject

class AlarmPlayerImpl @Inject constructor(
    private val context: Context
) : AlarmPlayer {
    private lateinit var ringtone: Ringtone

    override fun playAlarm() {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        if (audioManager.ringerMode == AudioManager.RINGER_MODE_VIBRATE ||
            audioManager.ringerMode == AudioManager.RINGER_MODE_SILENT) {
            audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
        }

        val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(context, uri)

        ringtone.play()
    }

    override fun stopAlarm() {
        if (::ringtone.isInitialized) {
            ringtone.stop()
        }
    }
}