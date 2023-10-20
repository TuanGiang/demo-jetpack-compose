package com.example.giangnguyen.presentation


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.giangnguyen.presentation.navigation.AppBottomNavigationBar
import com.example.giangnguyen.presentation.navigation.Navigation
import com.example.giangnguyen.presentation.navigation.Routes

@Composable
fun SportApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination =
        navBackStackEntry?.destination?.route ?: Routes.matchListScreen.destination
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Navigation(navController = navController, modifier = Modifier.weight(1.0f))
        AppBottomNavigationBar(
            selectedDestination = selectedDestination,
            navigateToTopLevelDestination = {
                navController.navigate(it.destination) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            }
        )
    }

}