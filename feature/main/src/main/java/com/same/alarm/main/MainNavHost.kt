package com.same.alarm.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.same.alarm.calendar.navigation.calendarScreen
import com.same.alarm.setup.navigation.setupScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    padding: PaddingValues,
)  {
    NavHost(
        modifier = modifier,
        navController = navigator.navController,
        startDestination = navigator.startDestination,
    ) {
        setupScreen(
            onRepeatClick = {
                navigator.navigateToRepeatSetup()
            }
        )
        calendarScreen()
    }
}