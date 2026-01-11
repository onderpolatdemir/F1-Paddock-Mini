package com.onder.f1PaddockMini.navigation

sealed class Screen(val route: String) {
    object Standings : Screen("standings")
    object Racing : Screen("racing")
    object RaceResults : Screen("race_results/{year}/{round}") {
        fun createRoute(year: String, round: String) = "race_results/$year/$round"
    }
    object QualifyingResults : Screen("qualifying_results/{year}/{round}") {
        fun createRoute(year: String, round: String) = "qualifying_results/$year/$round"
    }
}