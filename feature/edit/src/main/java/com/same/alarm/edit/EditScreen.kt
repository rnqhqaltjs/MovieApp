package com.same.alarm.edit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.same.alarm.designsystem.component.TimeWheelPicker
import com.same.alarm.designsystem.noRippleClickable
import com.same.alarm.edit.model.EditState
import java.time.LocalTime

@Composable
fun EditRoute(
    onDetailClick: () -> Unit,
    onShowSnackBar: (String) -> Unit,
    editViewModel: EditViewModel,
    onNavigateToList: () -> Unit
) {
    val title by editViewModel.title.collectAsStateWithLifecycle()
    val statusMessage by editViewModel.statusMessage.collectAsStateWithLifecycle()
    val time by editViewModel.time.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        editViewModel.updateEvent.collect {
            when (it) {
                is EditState.Success -> {
                    onShowSnackBar("수정 성공")
                    onNavigateToList()
                }
                is EditState.Failure -> onShowSnackBar(it.error)
            }
        }
    }

    EditScreen(
        title = title,
        statusMessage = statusMessage,
        time = time,
        onDetailClick = onDetailClick,
        onTitleChange = editViewModel::updateTitle,
        onStatusMessageChange = editViewModel::updateStatusMessage,
        onTimeChange = editViewModel::updateTime,
    )
}

@Composable
fun EditScreen(
    title: String,
    statusMessage: String,
    time: LocalTime,
    onDetailClick: () -> Unit,
    onTitleChange: (String) -> Unit,
    onStatusMessageChange: (String) -> Unit,
    onTimeChange: (LocalTime) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(38.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.height(40.dp)
            ) {
                if (title.isEmpty()) {
                    Text(
                        text = "제목",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
                        modifier = Modifier.alpha(0.5f)
                    )
                }

                BasicTextField(
                    value = title,
                    onValueChange = onTitleChange,
                    textStyle = TextStyle(
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
                        textAlign = TextAlign.Center
                    ),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.height(30.dp)
            ) {
                if (statusMessage.isEmpty()) {
                    Text(
                        text = "설명",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
                        modifier = Modifier.alpha(0.5f)
                    )
                }

                BasicTextField(
                    value = statusMessage,
                    onValueChange = onStatusMessageChange,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
                        textAlign = TextAlign.Center
                    ),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(102.dp))

            TimeWheelPicker(
                time = time,
                onTimeSelected = onTimeChange
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_down),
            contentDescription = "arrow_down",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
                .graphicsLayer(
                    scaleX = 0.8f,
                    scaleY = 0.8f
                )
                .noRippleClickable { onDetailClick() },
            tint = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditScreenPreview() {
    EditScreen(
        title = "Sample Title",
        statusMessage = "Sample Status Message",
        time = LocalTime.now(),
        onTitleChange = {},
        onStatusMessageChange = {},
        onTimeChange = {},
        onDetailClick = {}
    )
}