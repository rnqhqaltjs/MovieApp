package com.same.alarm.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.same.alarm.calendar.CalendarScreen
import com.same.alarm.setup.SetupScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 2 } // 탭 수
    )

    // Tab titles
    val tabs = listOf("Setup", "List")

    Column(modifier = Modifier.fillMaxSize()) {
        // 탭 레이아웃
        TabRow(selectedTabIndex = pagerState.currentPage) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        // 탭 클릭 시 페이지 전환
//                        coroutineScope.launch {
//                            pagerState.scrollToPage(index)
//                        }
                    },
                    text = { Text(text = title) }
                )
            }
        }

        // HorizontalPager 사용하여 탭 내용 표시
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            userScrollEnabled = true // 스와이프만 가능
        ) { page ->
            when (page) {
                0 -> SetupScreen()  // 첫 번째 탭 화면
                1 -> CalendarScreen()   // 두 번째 탭 화면
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen()
}