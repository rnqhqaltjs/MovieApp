package com.same.alarm.list.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.same.alarm.list.ListRoute
import com.same.alarm.navigation.Route

fun NavController.navigateToList(navOptions: NavOptions? = null) {
    navigate(Route.List, navOptions = navOptions)
}

fun NavGraphBuilder.listScreen(
) {
    composable<Route.List> {
        ListRoute()
    }
}