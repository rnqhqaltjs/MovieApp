package com.same.alarm.edit

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EditRoute(
    alarmId: Int,
    editViewModel: EditViewModel = hiltViewModel()
) {
    val alarm by editViewModel.alarmState.collectAsStateWithLifecycle()
    Log.d("alarm", alarmId.toString())
    LaunchedEffect(Unit) {
        editViewModel.loadAlarm(alarmId)
    }

    Log.d("alarm",alarm.toString())
    EditScreen(

    )
}

@Composable
fun EditScreen(

) {

}

@Preview(showBackground = true)
@Composable
fun EditScreenPreview() {
    EditScreen(

    )
}