package com.same.alarm.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.same.alarm.data.datasource.AlarmHelper
import com.same.alarm.domain.repository.AlarmRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RescheduleAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repo: AlarmRepository

    @Inject
    lateinit var alarmHelper: AlarmHelper
    override fun onReceive(p0: Context?, p1: Intent?) {
    }

//    override fun onReceive(context: Context?, intent: Intent?) {
//        intent ?: return
//
//        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
//            rescheduleAlarms()
//        }
//    }
//
//    private fun rescheduleAlarms() {
//        CoroutineScope(Dispatchers.IO).launch {
//            repo.getAllAlarms().collect {
//                it.forEach {
//                    if (it.isActive) {
//                        alarmHelper.scheduleAlarm(it)
//                    }
//                }
//            }
//        }
//    }
}