package com.same.alarm.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.same.alarm.edit.navigation.navigateToEdit
import com.same.alarm.feed.navigation.navigateToFeed
import com.same.alarm.list.navigation.navigateToList
import com.same.alarm.navigation.MainTabRoute
import com.same.alarm.navigation.Route
import com.same.alarm.setup.navigation.navigateToSetup

internal class MainNavigator(
    val navController: NavHostController,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = MainTab.LIST.route

    val currentTab: MainTab?
        @Composable get() = MainTab.find { tab ->
            currentDestination?.hasRoute(tab::class) == true
        }

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainTab.SETUP -> navController.navigateToSetup(MainTabRoute.Setup, navOptions)
            MainTab.LIST -> navController.navigateToList()
            MainTab.CALENDAR -> navController.navigateToFeed(navOptions)
        }
    }

    fun navigateToEdit(alarmId: Int) {
        navController.navigateToEdit(Route.Edit(alarmId))
    }

    fun navigateToList() {
        navController.navigateToList()
    }

    fun popBackStack() {
        navController.popBackStack()
    }

    fun popBackStackIfNotSetup() {
        if (!isSameCurrentDestination<MainTabRoute.Setup>()) {
            popBackStack()
        }
    }

    private inline fun <reified T : Route> isSameCurrentDestination(): Boolean {
        return navController.currentDestination?.hasRoute<T>() == true
    }

    @Composable
    fun shouldShowBottomBar() = MainTab.contains {
        currentDestination?.hasRoute(it::class) == true
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}

