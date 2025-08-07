package com.same.alarm.setup

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
import com.same.alarm.setup.model.SetupState
import java.time.LocalTime

@Composable
fun SetupRoute(
    onDetailClick: () -> Unit,
    onShowSnackBar: (String) -> Unit,
    setupViewModel: SetupViewModel
) {
    val time by setupViewModel.time.collectAsStateWithLifecycle()
    val title by setupViewModel.title.collectAsStateWithLifecycle()
    val statusMessage by setupViewModel.statusMessage.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        setupViewModel.addEvent.collect { 
            when (it) {
                is SetupState.Success -> onShowSnackBar("추가 성공")
                is SetupState.Failure -> onShowSnackBar(it.error)
            }
        }
    }

    SetupScreen(
        time = time,
        title = title,
        statusMessage = statusMessage,
        updateTime = setupViewModel::updateTime,
        updateTitle = setupViewModel::updateTitle,
        updateStatusMessage = setupViewModel::updateStatusMessage,
        onDetailClick = onDetailClick
    )
}

@Composable
fun SetupScreen(
    time: LocalTime,
    title: String,
    statusMessage: String,
    updateTime: (LocalTime) -> Unit,
    updateTitle: (String) -> Unit,
    updateStatusMessage: (String) -> Unit,
    onDetailClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
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
                    onValueChange = updateTitle,
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
                    onValueChange = updateStatusMessage,
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
                onTimeSelected = updateTime
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_down),
            contentDescription = "arrow_down",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 94.dp)
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
fun SetupScreenPreview() {
    SetupScreen(
        time = LocalTime.of(9, 0),
        title = "",
        statusMessage = "",
        updateTime = {},
        updateTitle = {},
        updateStatusMessage = {},
        onDetailClick = {}
    )
}