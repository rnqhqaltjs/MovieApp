package com.same.alarm.setup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
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
import com.same.alarm.designsystem.noRippleClickable
import com.same.alarm.ui.TimeWheelPicker
import java.time.LocalTime

@Composable
fun SetupRoute(
    onDetailClick: () -> Unit,
    onShowSnackBar: (String) -> Unit,
    setupViewModel: SetupViewModel
) {
    val title by setupViewModel.title.collectAsStateWithLifecycle()
    val statusMessage by setupViewModel.statusMessage.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        setupViewModel.addEvent.collect {
            onShowSnackBar("추가 성공")
        }
    }

    SetupScreen(
        title = title,
        statusMessage = statusMessage,
        updateTitle = setupViewModel::updateTitle,
        updateStatusMessage = setupViewModel::updateStatusMessage,
        onDetailClick = onDetailClick
    )
}

@Composable
fun SetupScreen(
    title: String,
    statusMessage: String,
    updateTitle: (String) -> Unit,
    updateStatusMessage: (String) -> Unit,
    onDetailClick: () -> Unit
) {
    var time by remember { mutableStateOf(LocalTime.of(9, 0)) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()
    ) {

//        Spacer(modifier = Modifier.height(83.dp))

        TimeWheelPicker { selectedTime ->
            time = selectedTime
        }
//
//        Spacer(modifier = Modifier.height(144.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.wrapContentSize()
        ) {
            if (title.isEmpty()) {
                Text(
                    text = "기업디 회의",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
                    modifier = Modifier.alpha(0.5f)
                )
            }

            BasicTextField(
                value = title,
                onValueChange = updateTitle,
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
                    textAlign = TextAlign.Center
                ),
                singleLine = true
            )
        }

//        Spacer(modifier = Modifier.heightIn(31.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.wrapContentSize()
        ) {
            if (statusMessage.isEmpty()) {
                Text(
                    text = "발표문 프린트 챙기기",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
                    modifier = Modifier.alpha(0.5f)
                )
            }

            BasicTextField(
                value = statusMessage,
                onValueChange = updateStatusMessage,
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
                    textAlign = TextAlign.Center
                ),
                singleLine = true
            )
        }

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
        title = "",
        statusMessage = "",
        updateTitle = {},
        updateStatusMessage = {},
        onDetailClick = {}
    )
}