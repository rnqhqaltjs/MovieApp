package com.same.alarm.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.same.alarm.designsystem.component.CategoryTabs
import com.same.alarm.list.component.AlarmItem
import com.same.alarm.list.component.TodayDateHeader
import com.same.alarm.model.alarm.Alarm
import java.time.LocalTime

@Composable
fun ListRoute(
    listViewModel: ListViewModel = hiltViewModel(),
    onEditClicked: (Int) -> Unit
) {
    val alarmListState by listViewModel.alarmListState.collectAsStateWithLifecycle()
    val selectedCategory by listViewModel.selectedCategory.collectAsStateWithLifecycle()
    val revealedState by listViewModel.revealedState.collectAsStateWithLifecycle()

    ListScreen(
        alarmList = alarmListState,
        categories = listViewModel.categories,
        selectedCategory = selectedCategory,
        onCategorySelected = listViewModel::selectCategory,
        onRemoveClicked = listViewModel::removeAlarm,
        onToggleAlarm = listViewModel::toggleAlarm,
        onEditClicked = onEditClicked,
        onTogglePin = listViewModel::togglePin
    )
}

@Composable
fun ListScreen(
    alarmList: List<Alarm>,
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    onRemoveClicked: (Alarm) -> Unit,
    onToggleAlarm: (Alarm) -> Unit,
    onEditClicked: (Int) -> Unit,
    onTogglePin: (Alarm) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(255, 106, 51, 204),
                        Color(255, 211, 115, 204)
                    )
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        TodayDateHeader()

        Spacer(modifier = Modifier.height(2.5.dp))

        CategoryTabs(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = onCategorySelected
        )

        Spacer(modifier = Modifier.height(31.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            val (pinned, normal) = alarmList.partition { it.isPinned }

            items(
                items = pinned,
                key = { it.id }
            ) { alarm ->
                AlarmItem(
                    alarm = alarm,
                    onTogglePin = onTogglePin,
                    onToggleAlarm = onToggleAlarm,
                    onEditClicked = onEditClicked
                )
            }

            items(
                items = normal,
                key = { it.id }
            ) { alarm ->
                AlarmItem(
                    alarm = alarm,
                    onTogglePin = onTogglePin,
                    onToggleAlarm = onToggleAlarm,
                    onEditClicked = onEditClicked
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    ListScreen(
        alarmList = listOf(
            Alarm(
                id = 1,
                title = "기업디 회의",
                statusMessage = "발표문 프린트 챙기기",
                time = LocalTime.of(9, 0),
                isActive = true,
                isPinned = true,
                daysOfWeek = listOf(0, 1, 2, 3, 4, 5 ,6),
                category = "Work",
                isRepeating = true
            )
        ),
        categories = listOf("스터디", "기상"),
        selectedCategory = "기상",
        onCategorySelected = {},
        onRemoveClicked = {},
        onToggleAlarm = {},
        onEditClicked = {},
        onTogglePin = {}
    )
}