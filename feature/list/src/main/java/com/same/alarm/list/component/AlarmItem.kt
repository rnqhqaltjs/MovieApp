package com.same.alarm.list.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.same.alarm.model.alarm.Alarm
import com.same.alarm.model.alarm.Category
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun AlarmItem(
    alarm: Alarm,
    onTogglePin: (Alarm) -> Unit,
    onToggleAlarm: (Alarm) -> Unit,
    onEditClicked: (Int) -> Unit
) {
    val daysOfWeekMap = listOf("월", "화", "수", "목", "금", "토", "일")
    val categoryEnum = Category.fromDisplayName(alarm.category) ?: Category.ETC

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .background(Color.White.copy(alpha = 0.3f))
            .padding(vertical = 12.dp)
            .noRippleClickable { onEditClicked(alarm.id) },
        horizontalAlignment = Alignment.End,
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(start = 23.dp, end = 22.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(
                        id = if (alarm.isPinned) R.drawable.ic_pin_filled else R.drawable.ic_pin_outline
                    ),
                    contentDescription = "고정 핀",
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .noRippleClickable { onTogglePin(alarm) }
                )

                Spacer(modifier = Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(categoryEnum.color)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = alarm.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
                    color = Color.White
                )

                Text(
                    text = alarm.statusMessage,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
                    color = Color.White
                )
            }

            CustomSwitch(
                checked = alarm.isActive,
                onCheckedChange = { isActive ->
                    onToggleAlarm(alarm.copy(isActive = isActive))
                },
                modifier = Modifier.padding(top = 2.dp)
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
                                color = Color.White
                            )
                        ) {
                            append("$day ")
                        }
                    } else {
                        append("$day ")
                    }
                }
            },
            color = Color.White.copy(alpha = 0.6f),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
            modifier = Modifier.padding(end = 24.dp),
            letterSpacing = (-0.5).sp
        )

        Row(
            modifier = Modifier.padding(top = 2.dp, end = 22.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = alarm.time.format(DateTimeFormatter.ofPattern("HH:mm")),
                fontSize = 29.sp,
                fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
                fontWeight = FontWeight.Medium,
                color = Color.White
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
        onToggleAlarm = {},
        onEditClicked = {}
    )
}