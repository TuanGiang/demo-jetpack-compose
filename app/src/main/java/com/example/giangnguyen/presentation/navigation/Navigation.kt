package com.example.giangnguyen.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.giangnguyen.presentation.match_details.MatchDetailsScreen
import com.example.giangnguyen.presentation.match_list.MatchListScreen
import com.example.giangnguyen.presentation.team_details.TeamDetailsScreen
import com.example.giangnguyen.presentation.team_list.TeamListScreen

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Routes.matchListScreen.destination,
        modifier = modifier
    ) {
        composable(
            Routes.matchListScreen.destination
        ) {
            MatchListScreen(
                modifier = Modifier.fillMaxSize(),
                navigateMatchDetails = { matchModel ->
                    navController.navigate("${Routes.matchDetailsScreen.destination}/${matchModel.dateString}/${matchModel.home}/${matchModel.away}")
                }
            )
        }
        composable(
            "${Routes.matchDetailsScreen.destination}/{dateString}/{home}/{away}",
            arguments = listOf(
                navArgument("dateString") {
                    type = NavType.StringType
                },
                navArgument("home") {
                    type = NavType.StringType
                },
                navArgument("away") {
                    type = NavType.StringType
                }
            ),
        ) {
            MatchDetailsScreen(
                modifier = Modifier.fillMaxSize(),
                navigateMatchDetails = { matchModel ->
                    navController.navigate("${Routes.matchDetailsScreen.destination}/${matchModel.dateString}/${matchModel.home}/${matchModel.away}")
                },
                backPressed = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            Routes.teamListScreen.destination
        ) {
            TeamListScreen(
                navigateToTeamDetails = { team ->
                    navController.navigate("${Routes.teamDetailsScreen.destination}/${team.name}")
                },
                modifier = Modifier.fillMaxSize(),
            )
        }

        composable(
            "${Routes.teamDetailsScreen.destination}/{name}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                },
            ),
        ) {
            TeamDetailsScreen(
                modifier = Modifier.fillMaxSize(),
                navigateMatchDetails = { matchModel ->
                    navController.navigate("${Routes.matchDetailsScreen.destination}/${matchModel.dateString}/${matchModel.home}/${matchModel.away}")
                },
                backPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}