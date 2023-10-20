package com.example.giangnguyen.presentation.navigation

import com.example.giangnguyen.presentation.navigation.Routes.Nothing
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Singleton

@Singleton
class NavigationManager {
    val commands = MutableStateFlow(Nothing)
    fun navigate(
        route: RouteInfo
    ) {
        commands.value = route
    }
}