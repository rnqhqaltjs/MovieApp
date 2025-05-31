package com.same.alarm.infra.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.view.WindowManager
import com.same.alarm.common.AlarmConstants.ACTION_NAME
import com.same.alarm.common.AlarmConstants.BUNDLE_KEY_ALARM_ID
import com.same.alarm.common.AlarmConstants.BUNDLE_KEY_REPEAT_COUNT
import com.same.alarm.common.AlarmConstants.WAKE_LOCK_TAG
import com.same.alarm.common.AlarmConstants.WAKE_LOCK_TIME_OUT
import com.same.alarm.ring.RingActivity

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        intent.action?.let { action ->
            if (action == ACTION_NAME) {
                val alarmId = intent.getIntExtra(BUNDLE_KEY_ALARM_ID, -1)
                val repeatCount = intent.getIntExtra(BUNDLE_KEY_REPEAT_COUNT, -1)
                moveAlarmActivity(context, alarmId, repeatCount)
            }
        }
    }

    private fun moveAlarmActivity(context: Context, alarmId: Int, repeatCount: Int) {
        val alarmIntent = Intent(context, RingActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            putExtra(BUNDLE_KEY_ALARM_ID, alarmId)
            putExtra(BUNDLE_KEY_REPEAT_COUNT, repeatCount)
        }
        context.startActivity(alarmIntent)
    }
}