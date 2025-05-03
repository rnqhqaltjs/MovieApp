package com.same.alarm.feed.navigation

import FeedRoute
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.same.alarm.navigation.MainTabRoute

fun NavController.navigateToFeed(navOptions: NavOptions? = null) {
    navigate(MainTabRoute.Feed, navOptions = navOptions)
}

fun NavGraphBuilder.feedNavGraph() {
    composable<MainTabRoute.Feed> { _ ->
        FeedRoute()
    }
}