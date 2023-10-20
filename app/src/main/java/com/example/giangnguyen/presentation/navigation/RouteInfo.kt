package com.example.giangnguyen.presentation.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument

interface RouteInfo {
    val arguments: List<NamedNavArgument>
    val destination: String
}

data class TopLevelRouteInfo(
    val destination: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int
)