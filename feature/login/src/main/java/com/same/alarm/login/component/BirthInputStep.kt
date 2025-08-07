package com.same.alarm.login.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.same.alarm.designsystem.R

@Composable
fun BirthInputStep(
    onNext: () -> Unit,
    onAgeChanged: (Int) -> Unit
) {
    val years = listOf(null, null) + (1925..2025).toList() + listOf(null, null)
    val months = listOf(null, null) + (1..12).toList() + listOf(null, null)
    val days = listOf(null, null) + (1..31).toList() + listOf(null, null)

    var selectedYear by remember { mutableIntStateOf(2000) }
    var selectedMonth by remember { mutableIntStateOf(1) }
    var selectedDay by remember { mutableIntStateOf(1) }

    LaunchedEffect(selectedYear, selectedMonth, selectedDay) {
        if (selectedYear in 1925..2025 && selectedMonth in 1..12 && selectedDay in 1..31) {
            val age = calculateAge(selectedYear, selectedMonth, selectedDay)
            onAgeChanged(age)
        }
    }

    Column(
        modifier = Modifier
            .padding(top = 39.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "생일은 언제인가요?",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight.Bold,
                fontSize = 23.sp,
                lineHeight = 34.sp,
                letterSpacing = (-0.51).sp,
                color = Color.White
            ),
            modifier = Modifier.padding(start = 29.dp)
        )

        Spacer(Modifier.height(77.dp))

        Box(
            modifier = Modifier
                .height(203.dp)
                .fillMaxWidth()
                .padding(horizontal = 21.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(Color.White.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .height(33.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color(0xFFFF7542)),
                contentAlignment = Alignment.Center
            ) {
            }

            Row(
                modifier = Modifier.fillMaxSize().padding(horizontal = 63.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                PickerColumn(
                    items = years,
                    selectedItem = selectedYear,
                    onItemSelected = { selectedYear = it },
                    label = "년",
                    modifier = Modifier.weight(1f)
                )
                PickerColumn(
                    items = months,
                    selectedItem = selectedMonth,
                    onItemSelected = { selectedMonth = it },
                    label = "월",
                    modifier = Modifier.weight(1f)
                )
                PickerColumn(
                    items = days,
                    selectedItem = selectedDay,
                    onItemSelected = { selectedDay = it },
                    label = "일",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun PickerColumn(
    items: List<Int?>,
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
) {
    val offset = 2
    val visibleItemsCount = 5
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = (items.indexOf(selectedItem) - offset).coerceAtLeast(0)
    )
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    LaunchedEffect(listState.isScrollInProgress.not()) {
        val centerIndex = listState.firstVisibleItemIndex + visibleItemsCount / 2
        val centerItem = items.getOrNull(centerIndex)

        centerItem?.let {
            if (it != selectedItem) {
                Log.d("PickerColumn", "[$label] 선택됨: $it (centerIndex: $centerIndex)")
                onItemSelected(it)
            }
        }
    }

    LazyColumn(
        state = listState,
        flingBehavior = flingBehavior,
        modifier = modifier.height(170.dp).padding(vertical = 2.5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        items(items.size) { index ->
            val value = items[index]
            val isSelected = value == selectedItem

            Text(
                text = value?.let { "$it$label" } ?: "",
                style = TextStyle(
                    color = Color.White.copy(alpha = if (isSelected) 1f else 0.7f),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.51).sp,
                    fontFamily = FontFamily(Font(R.font.inter))
                ),
                modifier = Modifier.padding(vertical = 6.5.dp)
            )
        }
    }
}