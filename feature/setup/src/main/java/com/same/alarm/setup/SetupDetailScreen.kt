package com.same.alarm.setup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.same.alarm.setup.component.CategoryDropdown
import com.same.alarm.setup.component.RepeatSwitchLabel
import com.same.alarm.setup.component.StatusMessageInput
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun SetupDetailRoute(
    setupViewModel: SetupViewModel
) {
    val selectedDays by setupViewModel.selectedDays.collectAsStateWithLifecycle()

    SetupDetailScreen(
        selectedDays = selectedDays,
        onDaySelected = setupViewModel::toggleDay
    )
}

@Composable
fun SetupDetailScreen(
    selectedDays: List<Int>,
    onDaySelected: (Int) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()
    ) {
        var isAlarmRepeated by remember { mutableStateOf(true) }
        val categories = listOf("일반", "중요", "기타")
        var selectedCategory by remember { mutableStateOf(categories[0]) }
        var statusMessage by remember { mutableStateOf("") }
        val days = DayOfWeek.entries.toTypedArray()

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

        Spacer(modifier = Modifier.height(19.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SetupDetailScreenPreview() {
    SetupDetailScreen(
        onDaySelected = {},
        selectedDays = emptyList()
    )
}