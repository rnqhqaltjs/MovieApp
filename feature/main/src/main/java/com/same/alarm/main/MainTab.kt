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
        contentDescription = "설정",
        MainTabRoute.Setup,
    ),
    LIST(
        iconResId = R.drawable.ic_list,
        contentDescription = "리스트",
        MainTabRoute.List
    ),
    CALENDAR(
        iconResId = R.drawable.ic_feed,
        contentDescription = "피드",
        MainTabRoute.Feed
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