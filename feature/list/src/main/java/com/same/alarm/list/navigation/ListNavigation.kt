package com.same.alarm.list.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
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
    onConfirmClick: () -> Unit
) {
    composable<MainTabRoute.List>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(700)
            ) + fadeIn(animationSpec = tween(700))
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Down,
                animationSpec = tween(700)
            ) + fadeOut(animationSpec = tween(700))
        }
    ) {
        ListRoute(
            onEditClicked = onEditClicked
        )
    }

    composable<Route.Edit> {
        EditRoute(
            onConfirmClick = onConfirmClick
        )
    }
}