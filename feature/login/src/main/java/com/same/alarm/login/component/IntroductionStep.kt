package com.same.alarm.login.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.same.alarm.designsystem.R

@Composable
fun IntroductionStep(
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 29.dp, end = 58.dp, top = 39.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = """
                안녕하세요!
                만나서 반갑습니다.
                원활한 알람 서비스 제공을 위해
                몇 가지 여쭤볼게요!
            """.trimIndent(),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight.Bold,
                fontSize = 23.sp,
                lineHeight = 34.sp,
                letterSpacing = (-0.51).sp,
                color = Color.White
            )
        )
    }
}