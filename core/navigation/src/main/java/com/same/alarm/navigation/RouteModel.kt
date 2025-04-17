package com.same.alarm.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data class Edit(val alarmId: Int) : Route
}

sealed interface MainTabRoute : Route {
    @Serializable
    data object Setup : MainTabRoute

    @Serializable
    data object Calendar : MainTabRoute

    @Serializable
    data object List : MainTabRoute
}

sealed interface AuthRoute : Route {
    @Serializable
    data object Login : AuthRoute

    @Serializable
    data object UserInfoInput : AuthRoute
}