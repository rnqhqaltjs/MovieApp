package com.same.alarm.alarm

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.media.AudioManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay

@Composable
fun AlarmRoute(
    alarmViewModel: AlarmViewModel = hiltViewModel()
) {
    AlarmScreen(
        startAlarm = alarmViewModel::startAlarm,
        stopAlarm = alarmViewModel::stopAlarm
    )
}

@SuppressLint("InvalidColorHexValue")
@Composable
fun AlarmScreen(
    startAlarm: () -> Unit,
    stopAlarm: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        startAlarm()
        delay(60_000)
        stopAlarmAndFinish(context, stopAlarm)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "이 시간 다른 사람들은..?",
            style = TextStyle(fontSize = 37.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        DrawDot(center = Offset(0f, 0f), size = 16f)

        DrawDot(center = Offset(0f, 0f), size = 12f)

        DrawDot(center = Offset(0f, 0f), size = 10f)

        DrawDot(center = Offset(0f, 0f), size = 8f)
        

        Button(
            onClick = {
                stopAlarmAndFinish(context, stopAlarm)
            },
            modifier = Modifier
                .width(241.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF0CF963))
        ) {
            Text(
                text = "당장 하러가기",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 20.sp
            )
        }

        Button(
            onClick = {
                stopAlarmAndFinish(context, stopAlarm)
            },
            modifier = Modifier
                .width(197.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFF7060A))
        ) {
            Text(
                text = "인생 뒤쳐지기",
                color = Color.Black,
                fontSize = 20.sp
            )
        }
    }
}

fun stopAlarmAndFinish(context: Context, stopAlarm: () -> Unit) {
    stopAlarm()
    (context as? Activity)?.finishAndRemoveTask()
}

@Composable
fun DrawDot(center: Offset, size: Float, color: Color = Color.Black) {
    Canvas(modifier = Modifier) {
        drawCircle(
            color = color,
            radius = size,
            center = center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AlarmScreen(
        startAlarm = {},
        stopAlarm = {}
    )
}