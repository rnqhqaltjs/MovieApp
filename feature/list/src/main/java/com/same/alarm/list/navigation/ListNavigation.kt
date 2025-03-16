package com.same.alarm.list.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.same.alarm.list.ListRoute
import com.same.alarm.navigation.Route

fun NavController.navigateToList(navOptions: NavOptions? = null) {
    navigate(Route.List, navOptions = navOptions)
}

fun NavGraphBuilder.listScreen() {
    composable<Route.List>(
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
        ListRoute()
    }
}