package com.same.alarm.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.same.alarm.edit.component.CategoryDropdown
import com.same.alarm.edit.component.TimeWheelPicker
import com.same.alarm.edit.component.TodayDateText
import com.same.alarm.model.Alarm
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun EditRoute(
    onConfirmClick: () -> Unit,
    editViewModel: EditViewModel = hiltViewModel(),
) {
    val alarmState by editViewModel.alarmState.collectAsStateWithLifecycle()
    val selectedDays by editViewModel.selectedDays.collectAsStateWithLifecycle()

    EditScreen(
        alarm = alarmState,
        selectedDays = selectedDays,
        onDaySelected = editViewModel::toggleDay,
        onUpdateAlarm = editViewModel::updateAlarm,
        onConfirmClick = onConfirmClick
    )
}

@Composable
fun EditScreen(
    alarm: Alarm?,
    selectedDays: List<Int>,
    onDaySelected: (Int) -> Unit,
    onUpdateAlarm: (Alarm) -> Unit,
    onConfirmClick: () -> Unit
) {
    if(alarm != null) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            TodayDateText()

            var time by remember { mutableStateOf(alarm.time) }
            var isAlarmRepeated by remember { mutableStateOf(alarm.isRepeating) }
            val days = DayOfWeek.entries.toTypedArray()
            val categories = listOf("일반", "중요", "기타")
            var selectedCategory by remember { mutableStateOf(alarm.category) }
            var statusMessage by remember { mutableStateOf(alarm.statusMessage) }

            TimeWheelPicker(
                time = time
            ) { selectedTime ->
                time = selectedTime
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "알람 반복",
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = isAlarmRepeated,
                    onCheckedChange = { isAlarmRepeated = it }
                )
            }

            Text(
                text = "요일 반복",
                fontSize = 18.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                days.forEachIndexed { index, day ->
                    val isSelected = index in selectedDays

                    Text(
                        text = day.getDisplayName(TextStyle.SHORT, Locale.KOREA),
                        fontSize = 16.sp,
                        color = if (isSelected) Color.White else Color.Black,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) Color.Blue else Color.LightGray)
                            .clickable { onDaySelected(index) }
                            .padding(8.dp)
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "상태 메시지",
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )

                CategoryDropdown(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )
            }

            BasicTextField(
                value = statusMessage,
                onValueChange = { statusMessage = it },
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 18.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray)
                    .padding(8.dp)
            )

            Button(
                onClick = {
                    onUpdateAlarm(
                        Alarm(
                            id = alarm.id,
                            time = time,
                            statusMessage = statusMessage,
                            daysOfWeek = selectedDays,
                            category = selectedCategory,
                            isRepeating = isAlarmRepeated,
                        )
                    )
                    onConfirmClick()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "확인")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditScreenPreview() {
    EditScreen(
        alarm = Alarm(0, LocalTime.of(9, 0), "", emptyList(), "", true),
        selectedDays = emptyList(),
        onDaySelected = {},
        onUpdateAlarm = {},
        onConfirmClick = {}
    )
}