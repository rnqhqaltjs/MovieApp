package com.same.alarm.setup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.same.alarm.model.Alarm
import com.same.alarm.setup.component.CategoryDropdown
import com.same.alarm.setup.component.ConfirmButton
import com.same.alarm.setup.component.RepeatSwitchLabel
import com.same.alarm.setup.component.StatusMessageInput
import com.same.alarm.setup.component.TimeWheelPicker
import com.same.alarm.setup.component.TodayDateText
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun SetupRoute(
    setupViewModel: SetupViewModel = hiltViewModel(),
    onShowSnackBar: (String) -> Unit,
) {
    val selectedDays by setupViewModel.selectedDays.collectAsStateWithLifecycle()

    SetupScreen(
        onAddAlarm = setupViewModel::addAlarm,
        selectedDays = selectedDays,
        onDaySelected = setupViewModel::toggleDay,
        onShowSnackBar = onShowSnackBar
    )
}

@Composable
fun SetupScreen(
    onAddAlarm: (Alarm) -> Unit,
    selectedDays: List<Int>,
    onDaySelected: (Int) -> Unit,
    onShowSnackBar: (String) -> Unit,
) {
    var time by remember { mutableStateOf(LocalTime.of(9, 0)) }

    var isAlarmRepeated by remember { mutableStateOf(true) }

    val categories = listOf("일반", "중요", "기타")
    var selectedCategory by remember { mutableStateOf(categories[0]) }
    var statusMessage by remember { mutableStateOf("") }

    val days = DayOfWeek.entries.toTypedArray()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.statusBarsPadding()
    ) {
        TodayDateText()

        TimeWheelPicker { selectedTime ->
            time = selectedTime
        }

        RepeatSwitchLabel(
            label = "알람 반복",
            isChecked = isAlarmRepeated,
            onCheckedChange = { isAlarmRepeated = it }
        )

        Text(
            text = "요일 반복",
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.Start).padding(start = 16.dp)
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

        StatusMessageInput(
            statusMessage = statusMessage,
            onStatusMessageChange = { statusMessage = it }
        )

        ConfirmButton {
            onAddAlarm(
                Alarm(
                    time = time,
                    statusMessage = statusMessage,
                    daysOfWeek = selectedDays,
                    category = selectedCategory,
                    isRepeating = isAlarmRepeated,
                )
            )
            onShowSnackBar("추가 성공")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SetupScreenPreview() {
    SetupScreen(
        onAddAlarm = {},
        selectedDays = emptyList(),
        onDaySelected = {},
        onShowSnackBar = {}
    )
}