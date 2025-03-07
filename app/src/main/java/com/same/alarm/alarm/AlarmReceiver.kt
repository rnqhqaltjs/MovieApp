package com.same.alarm.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.view.WindowManager

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: ""
        if (action == ACTION_NAME) {
            wakeLock(context)
            moveAlarmActivity(context)
        }
    }

    private fun wakeLock(context: Context) {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock: PowerManager.WakeLock =
            powerManager.newWakeLock(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WAKE_LOCK_TAG
            )
        wakeLock.acquire(WAKE_LOCK_TIME_OUT)
    }

    private fun moveAlarmActivity(context: Context) {
        val alarmIntent = Intent(context, AlarmActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        context.startActivity(alarmIntent)
    }

    companion object {
        const val BUNDLE_KEY_ALARM_ID = "BUNDLE_KEY_ALARM_ID"
        const val ACTION_NAME = "ADD_ALARM"
        private const val WAKE_LOCK_TIME_OUT = 5_000L
        private const val WAKE_LOCK_TAG = "MyApp:WakeLockTag"
    }
}