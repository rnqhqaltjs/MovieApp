package com.same.alarm.setup.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatusMessageInput(
    statusMessage: String,
    onStatusMessageChange: (String) -> Unit
) {
    BasicTextField(
        value = statusMessage,
        onValueChange = onStatusMessageChange,
        textStyle = androidx.compose.ui.text.TextStyle(fontSize = 18.sp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray)
            .padding(8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun StatusMessageInputPreview() {
    StatusMessageInput("") { }
}