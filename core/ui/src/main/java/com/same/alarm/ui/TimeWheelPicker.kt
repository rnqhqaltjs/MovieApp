package com.same.alarm.ui

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val amPmListState = rememberLazyListState(initialAmPm)

    val hour by remember { derivedStateOf { (hourListState.firstVisibleItemIndex % hourSize + hourSize) % hourSize } }
    val minute by remember { derivedStateOf { (minuteListState.firstVisibleItemIndex % minuteSize + minuteSize) % minuteSize } }
    val amPm by remember { derivedStateOf { amPmListState.firstVisibleItemIndex % 2 } }

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
            if (prevHour == 10 && hour == 11) {
                amPmListState.scrollToItem(1 - amPmListState.firstVisibleItemIndex)
            } else if (prevHour == 0 && hour == 11) {
                amPmListState.scrollToItem(1 - amPmListState.firstVisibleItemIndex)
            }
            prevHour = hour
        }
    }

    LaunchedEffect(selectedTime) {
        onTimeSelected(selectedTime)
    }

    amPmListState.SnapToNearestItem()
    hourListState.SnapToNearestItem()
    minuteListState.SnapToNearestItem()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .drawBehind {
                    drawLine(
                        Color(0xFFD0D0D0),
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = 0.75.dp.toPx()
                    )
                    drawLine(
                        Color(0xFFD0D0D0),
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 0.75.dp.toPx()
                    )
                }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            LazyColumn(
                state = amPmListState,
                contentPadding = PaddingValues(16.dp, 80.dp),
                flingBehavior = rememberSnapFlingBehavior(amPmListState),
                modifier = Modifier
                    .weight(1f)
                    .height(204.dp)
            ) {
                items(2) { index ->
                    val displayAmPm = if (index == 0) "오전" else "오후"

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                    ) {
                        Text(
                            text = displayAmPm,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Normal,
                            color = if (index == amPm) Color.Black else Color(0xFFD0D0D0)
                        )
                    }
                }
            }

            LazyColumn(
                state = hourListState,
                contentPadding = PaddingValues(16.dp, 80.dp),
                flingBehavior = maxScrollSpeedFlingBehavior(),
                modifier = Modifier
                    .weight(1f)
                    .height(204.dp)
            ) {
                items(Int.MAX_VALUE) { index ->
                    val displayHour = (index % hourSize) + 1

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                    ) {
                        Text(
                            text = "$displayHour 시",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Normal,
                            color = if (displayHour == hour + 1) Color.Black else Color(0xFFD0D0D0)
                        )
                    }
                }
            }

            LazyColumn(
                state = minuteListState,
                contentPadding = PaddingValues(16.dp, 80.dp),
                flingBehavior = maxScrollSpeedFlingBehavior(),
                modifier = Modifier
                    .weight(1f)
                    .height(204.dp)
            ) {
                items(Int.MAX_VALUE) { index ->
                    val displayMinute = index % minuteSize

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                    ) {
                        Text(
                            text = "$displayMinute 분",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Normal,
                            color = if (displayMinute == minute) Color.Black else Color(0xFFD0D0D0)
                        )
                    }
                }
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
        time = LocalTime.of(9, 0)
    ) {  }
}