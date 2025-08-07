package com.same.alarm.login.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.same.alarm.designsystem.R

@Composable
fun JobInputStep(
    selectedJob: String?,
    toggleJob: (String) -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(top = 39.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "현재 하고 있는 일은 무엇인가요?",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight.Bold,
                fontSize = 23.sp,
                lineHeight = 34.sp,
                letterSpacing = (-0.51).sp,
                color = Color.White
            ),
            modifier = Modifier.padding(start = 29.dp)
        )

        Spacer(Modifier.height(78.dp))

        Row(
            modifier = Modifier.padding(start = 29.dp)
        ) {
            JobOption(
                text = "직장인",
                isSelected = selectedJob == "직장인",
                onClick = { toggleJob("직장인") }
            )

            Spacer(Modifier.width(12.dp))

            JobOption(
                text = "프리랜서",
                isSelected = selectedJob == "프리랜서",
                onClick = { toggleJob("프리랜서") }
            )

            Spacer(Modifier.width(12.dp))

            JobOption(
                text = "주부",
                isSelected = selectedJob == "주부",
                onClick = { toggleJob("주부") }
            )
        }

        Spacer(Modifier.height(15.dp))

        Row(
            modifier = Modifier.padding(start = 29.dp)
        ) {
            JobOption(
                text = "취업준비생",
                isSelected = selectedJob == "취업준비생",
                onClick = { toggleJob("취업준비생") }
            )

            Spacer(Modifier.width(12.dp))

            JobOption(
                text = "대학생",
                isSelected = selectedJob == "대학생",
                onClick = { toggleJob("대학생") }
            )
        }

        Spacer(Modifier.height(15.dp))

        Row(
            modifier = Modifier.padding(start = 29.dp)
        ) {
            JobOption(
                text = "청소년",
                isSelected = selectedJob == "청소년",
                onClick = { toggleJob("청소년") }
            )

            Spacer(Modifier.width(12.dp))

            JobOption(
                text = "무직",
                isSelected = selectedJob == "무직",
                onClick = { toggleJob("무직") }
            )
        }
    }
}

@Composable
fun JobOption(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    val backgroundColor = if (isSelected) Color(0xFFFF7542) else Color.White.copy(alpha = 0.3f)

    Box(
        modifier = modifier
            .height(40.dp)
            .wrapContentWidth()
            .clip(RoundedCornerShape(50.dp))
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 30.dp),
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.inter)),
            fontWeight = FontWeight.Bold,
            lineHeight = 17.sp,
            letterSpacing = (-0.51).sp
        )
    }
}