package com.same.alarm.list.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.same.alarm.edit.EditRoute
import com.same.alarm.list.ListRoute
import com.same.alarm.model.Alarm
import com.same.alarm.navigation.MainTabRoute
import com.same.alarm.navigation.Route

fun NavController.navigateToList(navOptions: NavOptions? = null) {
    navigate(MainTabRoute.List, navOptions = navOptions)
}

fun NavGraphBuilder.listNavGraph(
    onEditClicked: (Alarm) -> Unit,
    onAlarmUpdated: () -> Unit
) {
    composable<MainTabRoute.List> {
        ListRoute(
            onEditClicked = onEditClicked
        )
    }

    composable<Route.Edit> {
        EditRoute(
            onAlarmUpdated = onAlarmUpdated
        )
    }
}