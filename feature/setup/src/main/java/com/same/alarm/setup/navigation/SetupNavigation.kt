package com.same.alarm.setup.navigation

import SetupRoute
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object Setup

fun NavController.navigateToSetup(navOptions: NavOptions? = null) {
    navigate(Setup, navOptions = navOptions)
}

fun NavGraphBuilder.setupScreen() {
    composable<Setup> { _ ->
        SetupRoute()
    }
}