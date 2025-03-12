package com.same.alarm.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_BOOT_COMPLETED
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class RescheduleAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: ""
        if (action == ACTION_BOOT_COMPLETED) {
            rescheduleWorker(context)
        }
    }

    private fun rescheduleWorker(context: Context) {
        val workManager = WorkManager.getInstance(context)
        val workRequest = OneTimeWorkRequestBuilder<RescheduleAlarmWorker>().build()
        workManager.enqueue(workRequest)
    }
}