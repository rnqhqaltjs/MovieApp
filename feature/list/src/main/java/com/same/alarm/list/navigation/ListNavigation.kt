package com.same.alarm.list.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.same.alarm.list.ListRoute
import com.same.alarm.navigation.MainTabRoute

fun NavController.navigateToList() {
    navigate(MainTabRoute.List) {
        popUpTo(graph.startDestinationId) {
            inclusive = false
        }
        launchSingleTop = true
    }
}

fun NavGraphBuilder.listNavGraph(
    onEditClicked: (Int) -> Unit
) {
    composable<MainTabRoute.List> {
        ListRoute(
            onEditClicked = onEditClicked
        )
    }
}