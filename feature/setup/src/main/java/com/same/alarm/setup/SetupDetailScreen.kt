package com.same.alarm.setup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.same.alarm.setup.component.RepeatSwitchLabel
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun SetupDetailRoute(
    setupViewModel: SetupViewModel
) {
    val selectedDays by setupViewModel.selectedDays.collectAsStateWithLifecycle()
    val selectCategory by setupViewModel.selectedCategory.collectAsStateWithLifecycle()

    SetupDetailScreen(
        selectedDays = selectedDays,
        selectedCategory = selectCategory,
        onDaySelected = setupViewModel::toggleDay,
        onCategorySelected = setupViewModel::toggleCategory
    )
}

@Composable
fun SetupDetailScreen(
    selectedDays: List<Int>,
    selectedCategory: String,
    onDaySelected: (Int) -> Unit,
    onCategorySelected: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()
    ) {
        var isAlarmRepeated by remember { mutableStateOf(true) }
        val days = DayOfWeek.entries.toTypedArray()
        val categories = listOf("스터디", "기상", "미팅", "집안일")

        Text(
            text = "반복",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
            modifier = Modifier.align(Alignment.Start).padding(start = 39.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            days.forEachIndexed { index, day ->
                val isSelected = index in selectedDays

                Text(
                    text = day.getDisplayName(TextStyle.SHORT, Locale.KOREA),
                    fontSize = 20.sp,
                    color = if (isSelected) Color.White else Color.Black,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) Color.Blue else Color.LightGray)
                        .clickable { onDaySelected(index) }
                        .wrapContentSize(Alignment.Center)
                )
            }
        }

        Text(
            text = "카테고리",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
            modifier = Modifier.align(Alignment.Start).padding(start = 39.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            categories.forEach { category ->
                val isSelected = selectedCategory == category

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(if (isSelected) Color.Blue else Color.LightGray)
                        .clickable { onCategorySelected(category) }
                        .height(40.dp)
                        .padding(horizontal = 3.dp)
                ) {
                    Text(
                        text = category,
                        fontSize = 20.sp,
                        color = if (isSelected) Color.White else Color.Black,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }


        RepeatSwitchLabel(
            label = "다시 알림",
            isChecked = isAlarmRepeated,
            onCheckedChange = { isAlarmRepeated = it }
        )

        Spacer(modifier = Modifier.height(19.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SetupDetailScreenPreview() {
    SetupDetailScreen(
        selectedDays = emptyList(),
        selectedCategory = "",
        onDaySelected = {},
        onCategorySelected = {}
    )
}