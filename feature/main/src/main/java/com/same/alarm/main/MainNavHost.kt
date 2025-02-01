package com.same.alarm.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.same.alarm.calendar.navigation.calendarScreen
import com.same.alarm.navigation.MainTabRoute
import com.same.alarm.setup.navigation.setupScreen

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MainTabRoute.Setup,
    ) {
        setupScreen()
        calendarScreen()
    }
}