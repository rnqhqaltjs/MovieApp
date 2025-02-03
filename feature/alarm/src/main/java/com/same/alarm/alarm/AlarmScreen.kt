package com.same.alarm.alarm

import android.app.Activity
import android.content.Context
import android.media.AudioManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AlarmScreen() {
    val context = LocalContext.current
    var ringtone: Ringtone? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        // AudioManager를 사용하여 진동 모드일 경우 소리 모드로 변경
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        // 진동 모드 체크 후, 소리 모드로 변경
        if (audioManager.ringerMode == AudioManager.RINGER_MODE_VIBRATE) {
            audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
        }

        // 알람 URI 설정
        val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(context, uri)

        // UI 스레드에서 play() 호출
        ringtone?.play()
    }

    // 화면 전체 배경을 어두운 색으로 설정
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            // 알림 제목
            Text(
                text = "ALARM",
                style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            // 알림 내용
            Text(
                text = "It's time to wake up!",
                style = TextStyle(fontSize = 20.sp, color = Color.White),
                modifier = Modifier.padding(bottom = 24.dp)
            )
            // 알람 끄기 버튼
            Button(onClick = {
                ringtone?.stop()
                (context as? Activity)?.finish()
            }) {
                Text(text = "Dismiss", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AlarmScreen()
}