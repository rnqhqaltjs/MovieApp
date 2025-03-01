package com.same.alarm.setup

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.same.alarm.model.Alarm
import com.same.alarm.setup.component.CategoryDropdown
import com.same.alarm.setup.component.ConfirmButton
import com.same.alarm.setup.component.RepeatSwitchLabel
import com.same.alarm.setup.component.StatusMessageInput
import com.same.alarm.setup.component.TimeWheelPicker
import com.same.alarm.setup.component.TodayDateText
import java.time.LocalTime

@Composable
fun SetupRoute(
    setUpViewModel: SetUpViewModel = hiltViewModel(),
    onRepeatClick: (List<Int>) -> Unit,
    restoredSelectedDays: List<Int>,
) {
    Log.d("okay", restoredSelectedDays.toString())
    SetupScreen(
        onRepeatClick = onRepeatClick,
        onAddAlarm = setUpViewModel::addAlarm,
        restoredSelectedDays = restoredSelectedDays,
    )
}

@Composable
fun SetupScreen(
    onRepeatClick: (List<Int>) -> Unit,
    onAddAlarm: (Alarm) -> Unit,
    restoredSelectedDays: List<Int>,
) {
    var time by remember { mutableStateOf(LocalTime.of(9, 0)) }

    var isAlarmRepeated by remember { mutableStateOf(true) }
    var isDayOfWeekRepeated by remember { mutableStateOf(restoredSelectedDays.isNotEmpty()) }

    val categories = listOf("일반", "중요", "기타")
    var selectedCategory by remember { mutableStateOf(categories[0]) }
    var statusMessage by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
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

        RepeatSwitchLabel(
            label = "요일 반복",
            isChecked = isDayOfWeekRepeated,
            onCheckedChange = { isChecked ->
                isDayOfWeekRepeated = isChecked
                if (isChecked)  {
                    onRepeatClick(restoredSelectedDays)
                }
            }
        )

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

        Text(
            text = restoredSelectedDays.joinToString(" "),
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )

        ConfirmButton {
            onAddAlarm(
                Alarm(
                    0,
                    time,
                    statusMessage,
                    restoredSelectedDays,
                    selectedCategory,
                    isAlarmRepeated
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SetupScreenPreview() {
    SetupScreen(
        onRepeatClick = {},
        onAddAlarm = {},
        restoredSelectedDays = emptyList()
    )
}