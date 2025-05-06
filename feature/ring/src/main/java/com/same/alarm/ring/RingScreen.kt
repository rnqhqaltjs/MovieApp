package com.same.alarm.ring

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay

@Composable
fun RingRoute(
    ringViewModel: RingViewModel = hiltViewModel(),
    stopRingAll: () -> Unit,
    stopRingCurrent: () -> Unit
) {
    RingScreen(
        startRing = ringViewModel::startPlayer,
        stopRingCurrent = stopRingCurrent,
        stopRingAll = stopRingAll
    )
}

@SuppressLint("InvalidColorHexValue")
@Composable
fun RingScreen(
    startRing: () -> Unit,
    stopRingAll: () -> Unit,
    stopRingCurrent: () -> Unit
) {
    LaunchedEffect(Unit) {
        startRing()
        delay(60_000)
        stopRingCurrent()
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
            onClick = stopRingAll,
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
            onClick = stopRingCurrent,
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
fun RingScreenPreview() {
    RingScreen(
        startRing = {},
        stopRingCurrent = {},
        stopRingAll = {}
    )
}