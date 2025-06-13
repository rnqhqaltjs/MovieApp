package com.same.alarm.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.same.alarm.designsystem.noRippleClickable
import com.same.alarm.list.component.ActionIcon
import com.same.alarm.list.component.AlarmItem
import com.same.alarm.list.component.SwipeableItemWithActions
import com.same.alarm.list.component.TodayDateHeader
import com.same.alarm.model.alarm.Alarm
import com.same.alarm.designsystem.component.CategoryTabs
import java.time.LocalTime

@Composable
fun ListRoute(
    listViewModel: ListViewModel = hiltViewModel(),
    onEditClicked: (Alarm) -> Unit
) {
    val alarmListState by listViewModel.alarmListState.collectAsStateWithLifecycle()
    val selectedCategory by listViewModel.selectedCategory.collectAsStateWithLifecycle()
    val revealedState by listViewModel.revealedState.collectAsStateWithLifecycle()

    ListScreen(
        alarmList = alarmListState,
        categories = listViewModel.categories,
        selectedCategory = selectedCategory,
        onCategorySelected = listViewModel::selectCategory,
        revealedState = revealedState,
        onRemoveAlarm = listViewModel::removeAlarm,
        onToggleAlarm = listViewModel::toggleAlarm,
        onEditAlarm = onEditClicked,
        onSwipeRevealed = listViewModel::swipeRevealed,
        resetRevealedState = listViewModel::resetRevealedState,
        onTogglePin = listViewModel::togglePin
    )
}

@Composable
fun ListScreen(
    alarmList: List<Alarm>,
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    revealedState: Map<Int, Boolean>,
    onRemoveAlarm: (Alarm) -> Unit,
    onToggleAlarm: (Alarm) -> Unit,
    onEditAlarm: (Alarm) -> Unit,
    onSwipeRevealed: (Int, Boolean) -> Unit,
    resetRevealedState: () -> Unit,
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
            .noRippleClickable { resetRevealedState() }
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
                AlarmRow(
                    alarm = alarm,
                    revealedState = revealedState,
                    onRemoveAlarm = onRemoveAlarm,
                    onToggleAlarm = onToggleAlarm,
                    onEditAlarm = onEditAlarm,
                    onSwipeRevealed = onSwipeRevealed,
                    resetRevealedState = resetRevealedState,
                    onTogglePin = onTogglePin
                )
            }

            items(
                items = normal,
                key = { it.id }
            ) { alarm ->
                AlarmRow(
                    alarm = alarm,
                    revealedState = revealedState,
                    onRemoveAlarm = onRemoveAlarm,
                    onToggleAlarm = onToggleAlarm,
                    onEditAlarm = onEditAlarm,
                    onSwipeRevealed = onSwipeRevealed,
                    resetRevealedState = resetRevealedState,
                    onTogglePin = onTogglePin
                )
            }
        }
    }
}

@Composable
fun AlarmRow(
    alarm: Alarm,
    revealedState: Map<Int, Boolean>,
    onRemoveAlarm: (Alarm) -> Unit,
    onToggleAlarm: (Alarm) -> Unit,
    onEditAlarm: (Alarm) -> Unit,
    onSwipeRevealed: (Int, Boolean) -> Unit,
    resetRevealedState: () -> Unit,
    onTogglePin: (Alarm) -> Unit
) {
    SwipeableItemWithActions(
        isRevealed = revealedState[alarm.id] == true,
        onLeftExpanded = {
            onSwipeRevealed(alarm.id, true)
        },
        onRightExpanded = {
            onSwipeRevealed(alarm.id, true)
        },
        onCollapsed = {
            onSwipeRevealed(alarm.id, false)
        },
        leftActions = {
        },
        rightActions = {
            ActionIcon(
                onClick = {
                    onEditAlarm(alarm)
                    resetRevealedState()
                },
                backgroundColor = Color.Green,
                icon = Icons.Default.Edit,
                modifier = Modifier.fillMaxHeight()
            )
            ActionIcon(
                onClick = {
                    onRemoveAlarm(alarm)
                },
                backgroundColor = Color.Red,
                icon = Icons.Default.Delete,
                modifier = Modifier.fillMaxHeight()
            )
        }
    ) {
        AlarmItem(
            alarm = alarm,
            onTogglePin = onTogglePin,
            onToggleAlarm = onToggleAlarm
        )
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
        revealedState = mapOf(),
        onRemoveAlarm = {},
        onToggleAlarm = {},
        onEditAlarm = {},
        onSwipeRevealed = { _, _ -> },
        resetRevealedState = {},
        onTogglePin = {}
    )
}