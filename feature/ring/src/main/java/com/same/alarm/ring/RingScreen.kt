package com.same.alarm.ring

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

@Composable
fun RingScreen(
    startRing: () -> Unit,
    stopRingAll: () -> Unit,
    stopRingCurrent: () -> Unit,
    alarmMessage: String
) {
    val animationStates = remember { List(9) { mutableStateOf(false) } }

    LaunchedEffect(Unit) {
        startRing()
        animationStates.take(4).forEachIndexed { index, state ->
            delay(150L * index)
            state.value = true
        }
        animationStates.subList(4, 7).forEachIndexed { index, state ->
            delay(50L * index)
            state.value = true
        }
        animationStates.drop(7).forEachIndexed { index, state ->
            delay(150L * index)
            state.value = true
        }

        delay(60_000)
        stopRingCurrent()
    }

    LaunchedEffect("animate") {
        animationStates.forEachIndexed { index, state ->
            delay(150L * index)
            state.value = true
        }
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
        Spacer(modifier = Modifier.height(54.dp))

        AnimatedVisibility(
            visible = animationStates[0].value,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "지금 이 시간 다른 사람들은..?",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter))
                    )
                )
                Spacer(modifier = Modifier.weight(2f))
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        repeat(3) { index ->
            AnimatedVisibility(
                visible = animationStates[index + 1].value,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn()
            ) {
                Card(
                    shape = RoundedCornerShape(30.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.3f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp, vertical = 6.dp)
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
        }

        Spacer(modifier = Modifier.height(31.dp))

        Column {
            repeat(3) { index ->
                AnimatedVisibility(
                    visible = animationStates[4 + index].value,
                    enter = slideInVertically(initialOffsetY = { it }) + fadeIn()
                ) {
                    DrawDot(size = 11f)
                }
            }
        }

        Spacer(modifier = Modifier.height(37.dp))

        AnimatedVisibility(
            visible = animationStates[7].value,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn()
        ) {
            Card(
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
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "18:30",
                        style = TextStyle(
                            fontSize = 26.sp,
                            lineHeight = 29.sp,
                            letterSpacing = (-0.51).sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter))
                        )
                    )

                    Text(
                        text = "국문과 세미나 참석",
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

        Spacer(modifier = Modifier.height(35.dp))

        AnimatedVisibility(
            visible = animationStates[8].value,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                Button(
                    onClick = stopRingAll,
                    modifier = Modifier
                        .weight(1f)
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

                Button(
                    onClick = stopRingAll,
                    modifier = Modifier
                        .weight(1f)
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