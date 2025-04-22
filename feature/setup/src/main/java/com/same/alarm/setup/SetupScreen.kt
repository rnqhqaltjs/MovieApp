package com.same.alarm.setup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.same.alarm.designsystem.noRippleClickable
import com.same.alarm.model.Alarm
import com.same.alarm.setup.component.TodayDateHeader
import com.same.alarm.ui.TimeWheelPicker
import java.time.DayOfWeek
import java.time.LocalTime

@Composable
fun SetupRoute(
    onDetailClick: () -> Unit,
    onShowSnackBar: (String) -> Unit,
    setupViewModel: SetupViewModel = hiltViewModel()
) {
    val selectedDays by setupViewModel.selectedDays.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        setupViewModel.addEvent.collect {
            onShowSnackBar("추가 성공")
        }
    }

    SetupScreen(
        onAddAlarm = setupViewModel::addAlarm,
        selectedDays = selectedDays,
        onDaySelected = setupViewModel::toggleDay,
        onDetailClick = onDetailClick
    )
}

@Composable
fun SetupScreen(
    onAddAlarm: (Alarm) -> Unit,
    selectedDays: List<Int>,
    onDaySelected: (Int) -> Unit,
    onDetailClick: () -> Unit
) {
    var time by remember { mutableStateOf(LocalTime.of(9, 0)) }
    var isAlarmRepeated by remember { mutableStateOf(true) }
    val categories = listOf("일반", "중요", "기타")
    var selectedCategory by remember { mutableStateOf(categories[0]) }
    var statusMessage by remember { mutableStateOf("") }
    val days = DayOfWeek.entries.toTypedArray()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize()
    ) {
        TodayDateHeader(
            onRefreshClick = {},
            onConfirmClick = {
                onAddAlarm(
                    Alarm(
                        time = time,
                        statusMessage = statusMessage,
                        daysOfWeek = selectedDays,
                        category = selectedCategory,
                        isRepeating = isAlarmRepeated,
                    )
                )
            }
        )

//        Spacer(modifier = Modifier.height(83.dp))

        TimeWheelPicker { selectedTime ->
            time = selectedTime
        }
//
//        Spacer(modifier = Modifier.height(144.dp))

        Text(
            text = "기업디 회의",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter))
        )

//        Spacer(modifier = Modifier.heightIn(31.dp))

        Text(
            text = "발표문 프린트 챙기기",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter))
        )

//        Spacer(modifier = Modifier.height(31.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_down),
            contentDescription = "arrow_down",
            modifier = Modifier.noRippleClickable { onDetailClick() }
        )

        Spacer(modifier = Modifier.height(19.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SetupScreenPreview() {
    SetupScreen(
        onAddAlarm = {},
        selectedDays = emptyList(),
        onDaySelected = {},
        onDetailClick = {}
    )
}