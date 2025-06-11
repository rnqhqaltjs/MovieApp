package com.same.alarm.list.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.same.alarm.designsystem.noRippleClickable
import com.same.alarm.list.R
import com.same.alarm.model.Alarm
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun AlarmItem(
    alarm: Alarm,
    onTogglePin: (Alarm) -> Unit,
    onToggleAlarm: (Alarm) -> Unit
) {
    val daysOfWeekMap = listOf("월", "화", "수", "목", "금", "토", "일")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.3f))
            .padding(vertical = 11.dp),
        horizontalAlignment = Alignment.End,
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(end = 13.dp)
        ) {
            Image(
                painter = painterResource(
                    id = if (alarm.isPinned) R.drawable.ic_pin_filled else R.drawable.ic_pin_outline
                ),
                contentDescription = "고정 핀",
                modifier = Modifier
                    .padding(top = 3.dp, start = 5.dp, end = 5.dp)
                    .noRippleClickable { onTogglePin(alarm) }
            )

            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = alarm.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
                )

                Text(
                    text = alarm.statusMessage,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
                )
            }

            CustomSwitch(
                checked = alarm.isActive,
                onCheckedChange = { isActive ->
                    onToggleAlarm(alarm.copy(isActive = isActive))
                },
            )
        }

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = buildAnnotatedString {
                daysOfWeekMap.forEachIndexed { index, day ->
                    if (alarm.daysOfWeek.contains(index)) {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        ) {
                            append("$day ")
                        }
                    } else {
                        append("$day ")
                    }
                }
            },
            color = Color.Gray,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
            modifier = Modifier.padding(end = 16.dp),
            letterSpacing = (-0.5).sp
        )

        Row(
            modifier = Modifier.padding(top = 2.dp, end = 13.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = alarm.time.format(DateTimeFormatter.ofPattern("a")),
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 1.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = alarm.time.format(DateTimeFormatter.ofPattern("hh:mm")),
                fontSize = 26.sp,
                fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlarmItemPreview() {
    AlarmItem(
        alarm = Alarm(
            id = 1,
            title = "기업디 회의",
            statusMessage = "발표문 프린트 챙기기",
            time = LocalTime.of(9, 0),
            isActive = true,
            isPinned = true,
            daysOfWeek = listOf(0, 1, 2, 3, 4, 5 ,6),
            category = "Work",
            isRepeating = true
        ),
        onTogglePin = {},
        onToggleAlarm = {}
    )
}