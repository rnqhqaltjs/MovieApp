package com.same.alarm.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object List : Route

//    @Serializable
//    data class SessionDetail(val sessionId: String) : Route
}

sealed interface MainTabRoute : Route {
    @Serializable
    data object Setup : MainTabRoute

    @Serializable
    data object Calendar : MainTabRoute
}