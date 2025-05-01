package com.same.alarm.setup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.same.alarm.common.Constants.CATEGORIES
import com.same.alarm.common.Constants.DAYS_OF_WEEK
import com.same.alarm.setup.component.RepeatSwitchLabel
import com.same.alarm.setup.model.SetupState
import com.same.alarm.ui.CategoryTabs
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun SetupDetailRoute(
    onShowSnackBar: (String) -> Unit,
    setupViewModel: SetupViewModel
) {
    val selectedDays by setupViewModel.selectedDays.collectAsStateWithLifecycle()
    val selectCategory by setupViewModel.selectedCategory.collectAsStateWithLifecycle()
    val isAlarmRepeated by setupViewModel.isAlarmRepeated.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        setupViewModel.addEvent.collect {
            when (it) {
                is SetupState.Success -> onShowSnackBar("추가 성공")
                is SetupState.Failure -> onShowSnackBar(it.error)
            }
        }
    }

    SetupDetailScreen(
        selectedDays = selectedDays,
        selectedCategory = selectCategory,
        isAlarmRepeated = isAlarmRepeated,
        onDaySelected = setupViewModel::toggleDay,
        onCategorySelected = setupViewModel::toggleCategory,
        onRepeatChange = setupViewModel::toggleRepeat
    )
}

@Composable
fun SetupDetailScreen(
    selectedDays: List<Int>,
    selectedCategory: String,
    isAlarmRepeated: Boolean,
    onDaySelected: (Int) -> Unit,
    onCategorySelected: (String) -> Unit,
    onRepeatChange: (Boolean) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "반복",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
            modifier = Modifier.align(Alignment.Start).padding(start = 39.dp)
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
                    color = if (isSelected) Color.White else Color.Black,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) Color.Blue else Color.LightGray)
                        .clickable { onDaySelected(index) }
                        .wrapContentSize(Alignment.Center)
                )
            }
        }

        Text(
            text = "카테고리",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
            modifier = Modifier.align(Alignment.Start).padding(start = 39.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        CategoryTabs(
            categories = CATEGORIES,
            selectedCategory = selectedCategory,
            onCategorySelected = onCategorySelected
        )

        RepeatSwitchLabel(
            label = "다시 알림",
            isChecked = isAlarmRepeated,
            onCheckedChange = onRepeatChange
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
        isAlarmRepeated = false,
        onDaySelected = {},
        onCategorySelected = {},
        onRepeatChange = {}
    )
}