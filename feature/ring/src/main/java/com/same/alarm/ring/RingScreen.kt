package com.same.alarm.ring

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import androidx.compose.runtime.getValue

@Composable
fun RingRoute(
    ringViewModel: RingViewModel = hiltViewModel(),
    stopRingAll: () -> Unit,
    stopRingCurrent: () -> Unit
) {
    val alarmMessage by ringViewModel.alarmMessage.collectAsStateWithLifecycle()

    RingScreen(
        startRing = ringViewModel::startPlayer,
        stopRingCurrent = stopRingCurrent,
        stopRingAll = stopRingAll,
        alarmMessage = alarmMessage
    )
}

@SuppressLint("InvalidColorHexValue")
@Composable
fun RingScreen(
    startRing: () -> Unit,
    stopRingAll: () -> Unit,
    stopRingCurrent: () -> Unit,
    alarmMessage: String
) {
    LaunchedEffect(Unit) {
        startRing()
        delay(60_000)
        stopRingCurrent()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(255, 106, 51, 204),
                        Color(255, 211, 115, 204)
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(66.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "이 시간 다른 사람들은..?",
                style = TextStyle(
                    fontSize = 26.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter))
                )
            )
            Spacer(modifier = Modifier.weight(2f))
        }

        Spacer(modifier = Modifier.height(85.dp))

        repeat(3) {
            Card (
                shape = RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.3f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
                    .padding(bottom = 12.dp)
                    .height(93.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = alarmMessage,
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 29.sp,
                            letterSpacing = (-0.51).sp,
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter))
                        )
                    )
                }
            }
        }

        repeat(4) {
            DrawDot(size = 11f)
        }

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = stopRingAll,
            modifier = Modifier
                .width(255.dp)
                .height(45.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFFF7542))
        ) {
            Text(
                text = "시작하기",
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = stopRingAll,
            modifier = Modifier
                .width(255.dp)
                .height(45.dp),
            colors = ButtonDefaults.buttonColors(Color.White)
        ) {
            Text(
                text = "미루기",
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFFF7542),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
            )
        }
    }
}

@Composable
fun DrawDot(
    size: Float,
    color: Color = Color.White
) {
    Canvas(
        modifier = Modifier.size((size * 2).dp)
    ) {
        drawCircle(
            color = color,
            radius = size
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RingScreenPreview() {
    RingScreen(
        startRing = {},
        stopRingCurrent = {},
        stopRingAll = {},
        alarmMessage = "수원 사는 대학생 홍길동 님은\n피그마 강의 2개 수강을 실천 중입니다."
    )
}