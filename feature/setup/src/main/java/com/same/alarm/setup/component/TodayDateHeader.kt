package com.same.alarm.setup.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.same.alarm.designsystem.noRippleClickable
import com.same.alarm.setup.R
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun TodayDateHeader(
    onRefreshClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    val today = LocalDate.now()
    val dayOfWeek = today.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN)
    val month = today.monthValue
    val day = today.dayOfMonth

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.White)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_refresh),
            contentDescription = "refresh",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 15.dp)
                .noRippleClickable { onRefreshClick() }
        )

        Text(
            text = "${month}월 ${day}일 $dayOfWeek",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
            modifier = Modifier.align(Alignment.Center)
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_confirm),
            contentDescription = "confirm",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 11.dp)
                .noRippleClickable { onConfirmClick() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TodayDateTextPreview() {
    TodayDateHeader(
        onRefreshClick = {},
        onConfirmClick = {}
    )
}