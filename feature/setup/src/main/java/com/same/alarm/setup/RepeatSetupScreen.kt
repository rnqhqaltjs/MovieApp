package com.same.alarm.setup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

@Composable
fun RepeatSetupRoute(
    repeatSetupViewModel: RepeatSetupViewModel = hiltViewModel(),
    onConfirmClick: (List<Int>) -> Unit,
    restoredSelectedDays: List<Int>
) {
    LaunchedEffect(restoredSelectedDays) {
        repeatSetupViewModel.setSelectedDays(restoredSelectedDays)
    }

    val selectedDays by repeatSetupViewModel.selectedDays.collectAsStateWithLifecycle()

    RepeatSetupScreen(
        onConfirmClick = onConfirmClick,
        selectedDays = selectedDays,
        onDaySelected = repeatSetupViewModel::toggleDay
    )
}

@Composable
fun RepeatSetupScreen(
    onConfirmClick: (List<Int>) -> Unit,
    selectedDays: List<Int>,
    onDaySelected: (Int) -> Unit
) {
    var isRepeatEnabled by remember { mutableStateOf(true) }
    val days = listOf("월", "화", "수", "목", "금", "토", "일")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "요일 반복", fontSize = 18.sp, modifier = Modifier.weight(1f))

            Switch(
                checked = isRepeatEnabled,
                onCheckedChange = { isChecked ->
                    isRepeatEnabled = isChecked
                    if (!isChecked) {
                        onConfirmClick(emptyList())
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            days.forEachIndexed { index, day ->
                val isSelected = index in selectedDays

                Text(
                    text = day,
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

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onConfirmClick(selectedDays) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("확인")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RepeatSetupScreenPreview() {
    RepeatSetupScreen(
        onConfirmClick = {},
        selectedDays = listOf(0, 2, 4),
        onDaySelected = {}
    )
}