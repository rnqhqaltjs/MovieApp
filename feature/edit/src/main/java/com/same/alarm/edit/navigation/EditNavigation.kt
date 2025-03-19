package com.same.alarm.edit.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.same.alarm.navigation.Route

fun NavController.navigateToEdit(alarmId: Int, navOptions: NavOptions? = null) {
    navigate(Route.Edit(alarmId), navOptions = navOptions)
}