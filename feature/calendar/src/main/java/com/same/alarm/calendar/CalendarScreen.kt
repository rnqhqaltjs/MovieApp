package com.same.alarm.calendar

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CalendarScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            val alarm = AlarmVo(
                id = System.currentTimeMillis().toInt(),
                name = "테스트 알람",
                timeStamp = System.currentTimeMillis() + 10_000
            )
            addBroadCastAlarmManager(context, alarm)
        }) {
            Text("10초 뒤 알람 울리기")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

fun addBroadCastAlarmManager(context: Context, alarm: AlarmVo) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java).apply {
        action = AlarmReceiver.ACTION_NAME
        putExtra(AlarmReceiver.BUNDLE_KEY_ALARM_ID, alarm.id)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        alarm.id,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    alarmManager.setAlarmClock(
        AlarmManager.AlarmClockInfo(alarm.timeStamp, pendingIntent),
        pendingIntent
    )
}

data class AlarmVo(
    val id: Int,
    val name: String,
    val timeStamp: Long
)


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
}