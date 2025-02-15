package com.same.alarm.setup

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.same.alarm.setup.component.TimeWheelPicker
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupRoute(
    onRepeatClick: () -> Unit
) {
    SetupScreen(
        onRepeatClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupScreen(
    onRepeatClick: () -> Unit
) {
    val today = LocalDate.now()
    val dayOfWeek = today.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN)
    val month = today.monthValue
    val day = today.dayOfMonth

    var isAlarmRepeated by remember { mutableStateOf(true) }
    var isDayOfWeekRepeated by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.statusBarsPadding()
    ) {
        Text(
            text = "${month}월 ${day}일 $dayOfWeek",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        TimeWheelPicker { time ->

        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "알람 반복",
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = isAlarmRepeated,
                onCheckedChange = { isAlarmRepeated = it }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "요일 반복",
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = isDayOfWeekRepeated,
                onCheckedChange = { isChecked ->
                    isDayOfWeekRepeated = isChecked
                    if (isChecked) onRepeatClick()
                }
            )
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

            val categories = listOf("일반", "중요", "기타")
            var selectedCategory by remember { mutableStateOf(categories[0]) }
            var isDropDownMenuExpanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = isDropDownMenuExpanded,
                onExpandedChange = { isDropDownMenuExpanded = it }
            ) {
                ExposedDropdownMenu(
                    expanded = isDropDownMenuExpanded,
                    onDismissRequest = { isDropDownMenuExpanded = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = {
                                Text(text = category)
                            },
                            onClick = {
                                selectedCategory = category
                                isDropDownMenuExpanded = false
                            }
                        )
                    }
                }

                TextField(
                    value = selectedCategory,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
                    trailingIcon = {
                        Icon(Icons.Filled.ArrowDropDown, contentDescription = "드롭다운 열기")
                    }
                )
            }
        }

        var statusMessage by remember { mutableStateOf("") }

        BasicTextField(
            value = statusMessage,
            onValueChange = { statusMessage = it },
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 18.sp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray)
                .padding(8.dp)
        )

        Button(
            onClick = {

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "확인")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun SetupScreenPreview() {
    SetupScreen(onRepeatClick = {})
}