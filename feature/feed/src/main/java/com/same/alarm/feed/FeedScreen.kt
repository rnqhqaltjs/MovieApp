package com.same.alarm.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.same.alarm.common.AlarmConfig.DAYS_OF_WEEK
import java.util.Locale

@Composable
fun FeedRoute(
    viewModel: FeedViewModel = hiltViewModel(),
) {
    val selectedDays by viewModel.selectedDays.collectAsStateWithLifecycle()

    FeedScreen(
        selectedDays = selectedDays,
        onDaySelected = viewModel::toggleDay,
    )
}

@Composable
fun FeedScreen(
    selectedDays: List<Int>,
    onDaySelected: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(255, 106, 51, 204),
                        Color(255, 211, 115, 204)
                    )
                )
            )
            .padding(bottom = 72.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(11.dp))

            Text(
                text = "홈",
                style = TextStyle(
                    fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    lineHeight = 20.57.sp,
                    letterSpacing = (-0.51).sp,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(25.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DAYS_OF_WEEK.forEachIndexed { index, day ->
                    val isSelected = index in selectedDays

                    Text(
                        text = day.getDisplayName(java.time.format.TextStyle.SHORT, Locale.KOREA),
                        fontSize = 16.sp,
                        color = if (isSelected) Color.White else Color(0xFF484848),
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                        fontFamily = FontFamily(Font(com.same.alarm.designsystem.R.font.inter)),
                        modifier = Modifier
                            .size(35.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) Color(0xFFFF7542) else Color.White)
                            .clickable { onDaySelected(index) }
                            .wrapContentSize(Alignment.Center)
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = { },
            containerColor = Color(0xFFFF7542),
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .size(70.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "추가",
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeedScreenPreview() {
    FeedScreen(
        selectedDays = listOf(0, 2, 4),
        onDaySelected = {}
    )
}