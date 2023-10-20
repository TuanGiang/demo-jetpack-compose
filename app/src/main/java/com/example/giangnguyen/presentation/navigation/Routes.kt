package com.example.giangnguyen.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.navigation.NamedNavArgument
import com.example.giangnguyen.R

object Routes {

    val matchListScreen = object : RouteInfo {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = "matches"
    }
    val matchDetailsScreen = object : RouteInfo {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = "match-details"
    }

    val teamListScreen = object : RouteInfo {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = "teams"
    }

    val teamDetailsScreen = object : RouteInfo {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = "teams-details"
    }


    val Nothing = object : RouteInfo {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = ""
    }
}

val TOP_LEVEL_ROUTES = listOf(
    TopLevelRouteInfo(
        destination = Routes.matchListScreen.destination,
        selectedIcon = Icons.Default.DateRange,
        unselectedIcon = Icons.Default.DateRange,
        iconTextId = R.string.tab_matches
    ),
    TopLevelRouteInfo(
        destination = Routes.teamListScreen.destination,
        selectedIcon = Icons.Default.Person,
        unselectedIcon = Icons.Default.Person,
        iconTextId = R.string.tab_teams
    ),
)