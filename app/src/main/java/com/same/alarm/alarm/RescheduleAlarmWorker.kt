package com.same.alarm.alarm

import android.app.Service.STOP_FOREGROUND_DETACH
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.same.alarm.domain.repository.AlarmHelper
import com.same.alarm.domain.repository.AlarmRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltWorker
class RescheduleAlarmWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val alarmRepository: AlarmRepository,
    private val alarmHelper: AlarmHelper
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        CoroutineScope(Dispatchers.IO).launch {
            alarmRepository.getAllAlarms()
                .collect { alarms ->
                    alarms.filter { it.isActive }
                        .forEach { alarmHelper.scheduleAlarm(it) }
                }
        }
        return Result.success()
    }
}