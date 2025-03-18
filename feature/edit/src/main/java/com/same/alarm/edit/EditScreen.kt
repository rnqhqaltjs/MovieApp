package com.same.alarm.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EditRoute(
    editViewModel: EditViewModel = hiltViewModel()
) {
    val alarmState by editViewModel.alarmState.collectAsStateWithLifecycle()

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