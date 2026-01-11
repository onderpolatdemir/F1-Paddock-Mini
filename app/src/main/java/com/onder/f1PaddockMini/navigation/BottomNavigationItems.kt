package com.onder.f1PaddockMini.navigation

import com.onder.f1PaddockMini.R

data class BottomNavItem(
    val label: String,
    val icon: Int,
    val route: String
)

object BottomNavigationItems {
    val items = listOf(
        BottomNavItem(
            label = "Standings",
            icon = R.drawable.ic_standings,
            route = Screen.Standings.route
        ),
        BottomNavItem(
            label = "Racing",
            icon = R.drawable.ic_racing,
            route = Screen.Racing.route
        )
    )
}