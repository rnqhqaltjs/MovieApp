package com.same.alarm.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun EditRoute(
    alarmId: Int,
    editViewModel: EditViewModel = hiltViewModel()
) {
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