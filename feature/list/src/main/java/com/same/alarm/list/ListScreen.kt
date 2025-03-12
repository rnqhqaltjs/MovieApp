package com.same.alarm.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.same.alarm.list.component.ActionIcon
import com.same.alarm.list.component.SwipeableItemWithActions
import com.same.alarm.model.Alarm
import java.time.format.DateTimeFormatter

@Composable
fun ListRoute(
    listViewModel: ListViewModel = hiltViewModel()
) {
    val alarmList by listViewModel.alarmList.collectAsStateWithLifecycle()

    ListScreen(
        alarmList = alarmList,
        onRemoveAlarm = listViewModel::removeAlarm,
        onUpdateAlarm = listViewModel::updateAlarm
    )
}

@Composable
fun ListScreen(
    alarmList: List<Alarm>,
    onRemoveAlarm: (Alarm) -> Unit,
    onUpdateAlarm: (Alarm) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "알람 리스트",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(
                items = alarmList
            ) { alarm ->
                var isRevealed by remember { mutableStateOf(false) }

                SwipeableItemWithActions(
                    isRevealed = isRevealed,
                    onLeftExpanded = { isRevealed = true },
                    onRightExpanded = { isRevealed = true },
                    onCollapsed = { isRevealed = false },
                    leftActions = {
                        ActionIcon(
                            onClick = {
                                onRemoveAlarm(alarm)
                            },
                            backgroundColor = Color.Blue,
                            icon = Icons.Default.Check,
                            modifier = Modifier.fillMaxHeight()
                        )
                    },
                    rightActions = {
                        ActionIcon(
                            onClick = {
                                onRemoveAlarm(alarm)
                            },
                            backgroundColor = Color.Red,
                            icon = Icons.Default.Delete,
                            modifier = Modifier.fillMaxHeight()
                        )
                    },
                ) {
                    AlarmItem(
                        alarm = alarm,
                        onUpdateAlarm = onUpdateAlarm
                    )
                }
            }
        }
    }
}

@Composable
fun AlarmItem(
    alarm: Alarm,
    onUpdateAlarm: (Alarm) -> Unit
) {
    val daysOfWeekMap = listOf("월", "화", "수", "목", "금", "토", "일")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "알람 ID: ${alarm.id}", style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "시간: ${alarm.time.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "상태 메시지: ${alarm.statusMessage}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(text = "카테고리: ${alarm.category}", style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "반복 여부: ${if (alarm.isRepeating) "반복" else "단일"}",
                style = MaterialTheme.typography.bodyMedium
            )

            val daysOfWeekText = alarm.daysOfWeek.joinToString(", ") { day ->
                daysOfWeekMap.getOrNull(day) ?: ""
            }
            Text(text = "반복 요일: $daysOfWeekText", style = MaterialTheme.typography.bodyMedium)
        }

        Switch(
            checked = alarm.isActive,
            onCheckedChange = { isChecked ->
                onUpdateAlarm(alarm.copy(isActive = isChecked))
            },
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}