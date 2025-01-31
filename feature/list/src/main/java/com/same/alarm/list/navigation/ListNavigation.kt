package com.same.alarm.list.navigation

import ListRoute
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object List

fun NavController.navigateToList(navOptions: NavOptions? = null) {
    navigate(List, navOptions = navOptions)
}

fun NavGraphBuilder.listScreen() {
    composable<List> { _ ->
        ListRoute()
    }
}