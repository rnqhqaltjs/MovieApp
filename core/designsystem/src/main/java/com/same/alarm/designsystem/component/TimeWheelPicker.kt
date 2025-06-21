package com.same.alarm.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.same.alarm.designsystem.R
import com.same.alarm.designsystem.noRippleClickable
import java.time.LocalTime

@Composable
fun TimeWheelPicker(
    time: LocalTime = LocalTime.of(9, 0),
    onTimeSelected: (LocalTime) -> Unit
) {
    val initialHour = time.hour - 1
    val initialMinute = time.minute

    val hourSize = 12
    val minuteSize = 60
    val infiniteScrollOffset = Int.MAX_VALUE / 2

    val initialHourOffset = infiniteScrollOffset - (infiniteScrollOffset % hourSize) + initialHour
    val initialMinuteOffset = infiniteScrollOffset - (infiniteScrollOffset % minuteSize) + initialMinute
    val initialAmPm = if (initialHour < 12) 0 else 1

    val hourListState = rememberLazyListState(initialHourOffset)
    val minuteListState = rememberLazyListState(initialMinuteOffset)

    val hour by remember { derivedStateOf { (hourListState.firstVisibleItemIndex % hourSize + hourSize) % hourSize } }
    val minute by remember { derivedStateOf { (minuteListState.firstVisibleItemIndex % minuteSize + minuteSize) % minuteSize } }
    var amPm by remember { mutableIntStateOf(initialAmPm) }

    var prevHour by remember { mutableIntStateOf(hour) }

    val selectedTime by remember {
        derivedStateOf {
            val currentHour = when {
                amPm == 0 -> {
                    if (hour == 11) 0 else hour + 1
                }

                amPm == 1 -> {
                    if (hour == 11) 12 else hour + 13
                }

                else -> hour
            }

            LocalTime.of(currentHour, minute)
        }
    }

    LaunchedEffect(hour, prevHour) {
        if (prevHour != hour) {
            if ((prevHour == 10 && hour == 11) || (prevHour == 0 && hour == 11)) {
                amPm = 1 - amPm
            }
            prevHour = hour
        }
    }

    LaunchedEffect(selectedTime) {
        onTimeSelected(selectedTime)
    }

    hourListState.SnapToNearestItem()
    minuteListState.SnapToNearestItem()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(75.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            LazyColumn(
                state = hourListState,
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 73.dp),
                flingBehavior = maxScrollSpeedFlingBehavior(),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.height(240.dp).width(121.dp)
            ) {
                items(Int.MAX_VALUE) { index ->
                    val displayHour = (index % hourSize) + 1
                    val hour12: (Int) -> Int = { if (it % 12 == 0) 12 else it % 12 }

                    val isNearFocusedHour = displayHour in listOf(
                        hour12(hour),
                        hour12(hour + 1),
                        hour12(hour + 2)
                    )

                    val color = if(displayHour == hour12(hour + 1)) {
                        Color.White
                    } else if(displayHour ==  hour12(hour) || displayHour ==  hour12(hour + 2)) {
                        Color(0xFFF9F9F9)
                    } else {
                        Color(0xFFF3F3F3)
                    }

                    Text(
                        text = displayHour.toString().padStart(2, '0'),
                        fontSize = if (displayHour == hour + 1) 77.sp else 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        color = color,
                        modifier = Modifier.padding(
                            vertical = if (isNearFocusedHour) 0.dp else 11.dp
                        )
                    )
                }
            }

            Text(
                text = ":",
                fontSize = 77.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily(Font(R.font.inter)),
                color = Color.White,
                modifier = Modifier.offset(y = (-10).dp)
            )

            LazyColumn(
                state = minuteListState,
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 73.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                flingBehavior = maxScrollSpeedFlingBehavior(),
                modifier = Modifier.height(240.dp).width(121.dp)
            ) {
                items(Int.MAX_VALUE) { index ->
                    val displayMinute = index % minuteSize
                    val isNearFocusedMinute = displayMinute in listOf(
                        (minute - 1 + minuteSize) % minuteSize,
                        minute % minuteSize,
                        (minute + 1) % minuteSize
                    )

                    val color = if (displayMinute == minute % minuteSize) {
                        Color.White
                    } else if (displayMinute == (minute - 1 + minuteSize) % minuteSize || displayMinute == (minute + 1) % minuteSize) {
                        Color(0xFFF9F9F9)
                    } else {
                        Color(0xFFF3F3F3)
                    }

                    Text(
                        text =  displayMinute.toString().padStart(2, '0'),
                        fontSize = if(displayMinute == minute) 77.sp else 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        color = color,
                        modifier = Modifier
                            .padding(vertical = if (isNearFocusedMinute) 0.dp else 11.dp)
                    )
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(19.dp)
        ) {
            listOf("오전", "오후").forEachIndexed { index, label ->
                Text(
                    text = label,
                    fontSize = 16.sp,
                    fontWeight = if(index == amPm) FontWeight.ExtraBold else FontWeight.SemiBold,
                    color = if (index == amPm) Color.White else Color(0xFFFACCBC),
                    fontFamily = FontFamily(Font(R.font.inter)),
                    modifier = Modifier
                        .noRippleClickable {
                            amPm = index
                        }
                )
            }
        }
    }
}

@Composable
fun LazyListState.SnapToNearestItem() {
    LaunchedEffect(isScrollInProgress) {
        if (!isScrollInProgress) {
            animateScrollToItem(firstVisibleItemIndex)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimeWheelPickerPreview() {
    TimeWheelPicker(
        time = LocalTime.of(9, 44)
    ) {  }
}