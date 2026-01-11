package com.onder.f1PaddockMini.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.onder.f1PaddockMini.pages.QualifyingResultsPage
import com.onder.f1PaddockMini.pages.RaceResultsPage
import com.onder.f1PaddockMini.pages.SchedulePage
import com.onder.f1PaddockMini.pages.StandingsPage

@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            // Only show bottom bar on main screens
            if (currentRoute == Screen.Standings.route || currentRoute == Screen.Racing.route) {
                BottomBar(
                    currentRoute = currentRoute,
                    onItemClick = { route ->
                        navController.navigate(route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            popUpTo(navController.graph.startDestinationId) {
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
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Standings.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Standings.route) {
                StandingsPage()
            }
            
            composable(Screen.Racing.route) {
                SchedulePage(
                    onNavigateToRaceResults = { year, round ->
                        navController.navigate(Screen.RaceResults.createRoute(year, round))
                    },
                    onNavigateToQualifyingResults = { year, round ->
                        navController.navigate(Screen.QualifyingResults.createRoute(year, round))
                    }
                )
            }
            
            composable(
                route = Screen.RaceResults.route,
                arguments = listOf(
                    navArgument("year") { type = NavType.StringType },
                    navArgument("round") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val year = backStackEntry.arguments?.getString("year") ?: ""
                val round = backStackEntry.arguments?.getString("round") ?: ""
                RaceResultsPage(
                    year = year,
                    round = round
                )
            }
            
            composable(
                route = Screen.QualifyingResults.route,
                arguments = listOf(
                    navArgument("year") { type = NavType.StringType },
                    navArgument("round") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val year = backStackEntry.arguments?.getString("year") ?: ""
                val round = backStackEntry.arguments?.getString("round") ?: ""
                QualifyingResultsPage(
                    year = year,
                    round = round
                )
            }
        }
    }
}