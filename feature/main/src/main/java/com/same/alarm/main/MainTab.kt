package com.same.alarm.main

import androidx.compose.runtime.Composable
import com.same.alarm.navigation.MainTabRoute
import com.same.alarm.navigation.Route

internal enum class MainTab(
    val iconResId: Int,
    internal val contentDescription: String,
    val route: MainTabRoute,
) {
    SETUP(
        iconResId = R.drawable.ic_setup,
        contentDescription = "알람",
        MainTabRoute.Setup,
    ),
    CALENDAR(
        iconResId = R.drawable.ic_calendar,
        contentDescription = "캘린더",
        MainTabRoute.Calendar
    );

    companion object {
        @Composable
        fun find(predicate: @Composable (MainTabRoute) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }

        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}