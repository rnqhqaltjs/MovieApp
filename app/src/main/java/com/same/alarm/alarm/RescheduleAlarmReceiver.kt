package com.same.alarm.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_BOOT_COMPLETED
import android.content.Intent.ACTION_LOCKED_BOOT_COMPLETED

class RescheduleAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        intent.action?.let { action ->
            if (action == ACTION_BOOT_COMPLETED || action == ACTION_LOCKED_BOOT_COMPLETED) {
                rescheduleWorker(context)
            }
        }
    }

    private fun rescheduleWorker(context: Context) {
        Intent(context, ForegroundAlarmService::class.java).apply {
            context.startService(this)
        }
    }
}