package com.same.alarm.edit

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.same.alarm.common.AlarmConfig.DAYS_OF_WEEK
import com.same.alarm.designsystem.component.CategoryTabs
import com.same.alarm.edit.model.EditState
import com.same.alarm.model.alarm.Category
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun EditDetailRoute(
    alarmId: Int,
    onShowSnackBar: (String) -> Unit,
    editViewModel: EditViewModel
) {
    val selectedDays by editViewModel.selectedDays.collectAsStateWithLifecycle()
    val selectCategory by editViewModel.selectedCategory.collectAsStateWithLifecycle()
    val isAlarmRepeated by editViewModel.isAlarmRepeated.collectAsStateWithLifecycle()

    LaunchedEffect(alarmId) {
        editViewModel.loadAlarm(alarmId)
    }

    LaunchedEffect(Unit) {
        editViewModel.updateEvent.collect {
            when (it) {
                is EditState.Success -> onShowSnackBar("추가 성공")
                is EditState.Failure -> onShowSnackBar(it.error)
            }
        }
    }

    EditDetailScreen(
        selectedDays = selectedDays,
        selectedCategory = selectCategory,
        isAlarmRepeated = isAlarmRepeated,
        onDaySelected = editViewModel::toggleDay,
        onCategorySelected = editViewModel::toggleCategory,
        onRepeatChange = editViewModel::toggleRepeat
    )
}

@Composable
fun EditDetailScreen(
    selectedDays: List<Int>,
    selectedCategory: String,
    isAlarmRepeated: Boolean,
    onDaySelected: (Int) -> Unit,
    onCategorySelected: (String) -> Unit,
    onRepeatChange: (Boolean) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(33.dp))

        Text(
            text = "반복",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 39.dp),
            color = Color.White,
        )

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DAYS_OF_WEEK.forEachIndexed { index, day ->
                val isSelected = index in selectedDays

                Text(
                    text = day.getDisplayName(TextStyle.SHORT, Locale.KOREA),
                    fontSize = 16.sp,
                    color = if (isSelected) Color.White else Color(0xFF484848),
                    fontWeight = if(isSelected) FontWeight.SemiBold else FontWeight.Medium,
                    fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) Color(0xFFFF7542) else Color.White)
                        .clickable { onDaySelected(index) }
                        .wrapContentSize(Alignment.Center)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "카테고리",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 39.dp),
            color = Color.White,
        )

        Spacer(modifier = Modifier.height(15.dp))

        CategoryTabs(
            categories = Category.entries.map { it.displayName },
            selectedCategory = selectedCategory,
            onCategorySelected = onCategorySelected
        )

        Spacer(modifier = Modifier.height(39.dp))

        Text(
            text = "다시 알림",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 39.dp),
            color = Color.White,
        )

        Switch(
            checked = isAlarmRepeated,
            onCheckedChange = onRepeatChange
        )

        Spacer(modifier = Modifier.height(19.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun EditDetailScreenPreview() {
    EditDetailScreen(
        selectedDays = emptyList(),
        selectedCategory = "",
        isAlarmRepeated = false,
        onDaySelected = {},
        onCategorySelected = {},
        onRepeatChange = {}
    )
}