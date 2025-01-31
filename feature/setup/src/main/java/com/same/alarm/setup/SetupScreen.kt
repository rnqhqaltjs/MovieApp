package com.same.alarm.setup

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupScreen () {
    var selectedTime: TimePickerState? by remember { mutableStateOf(null) }

    DialUseStateExample(
        onDismiss = {
//            showDialExample = false
        },
        onConfirm = {
                time ->
            selectedTime = time
//            showDialExample = false
        },
    )

    if (selectedTime != null) {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, selectedTime!!.hour)
        cal.set(Calendar.MINUTE, selectedTime!!.minute)
        cal.isLenient = false
//        Text("Selected time = ${formatter.format(cal.time)}")
    } else {
        Text("No time selected.")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialUseStateExample(
    onConfirm: (TimePickerState) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = false,
    )

    Column {
        TimeInput(
            state = timePickerState,
        )
        Button(onClick = onDismiss) {
            Text("Dismiss picker")
        }
        Button(onClick = { onConfirm(timePickerState) }) {
            Text("Confirm selection")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    var selectedTime: TimePickerState? by remember { mutableStateOf(null) }

    DialUseStateExample(
        onDismiss = {
//            showDialExample = false
        },
        onConfirm = {
                time ->
            selectedTime = time
//            showDialExample = false
        },
    )

    if (selectedTime != null) {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, selectedTime!!.hour)
        cal.set(Calendar.MINUTE, selectedTime!!.minute)
        cal.isLenient = false
//        Text("Selected time = ${formatter.format(cal.time)}")
    } else {
        Text("No time selected.")
    }
}