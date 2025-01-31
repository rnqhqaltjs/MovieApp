package com.same.alarm

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.same.alarm.list.navigation.listScreen
import com.same.alarm.setup.navigation.Setup
import com.same.alarm.setup.navigation.setupScreen

@Composable
fun ALNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Setup,
    ) {
        setupScreen()
        listScreen()
    }
}