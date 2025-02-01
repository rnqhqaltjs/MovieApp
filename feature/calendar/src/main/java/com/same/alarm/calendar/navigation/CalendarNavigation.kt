package com.same.alarm.calendar.navigation

import CalendarRoute
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.same.alarm.navigation.MainTabRoute

fun NavController.navigateToCalendar(navOptions: NavOptions? = null) {
    navigate(MainTabRoute.Calendar, navOptions = navOptions)
}

fun NavGraphBuilder.calendarScreen() {
    composable<MainTabRoute.Calendar> { _ ->
        CalendarRoute()
    }
}