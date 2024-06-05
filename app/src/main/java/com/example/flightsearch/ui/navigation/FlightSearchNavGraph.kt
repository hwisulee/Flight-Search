package com.example.flightsearch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flightsearch.ui.home.HomeDestination
import com.example.flightsearch.ui.home.HomeScreen
import com.example.flightsearch.ui.item.ItemSearchDestination
import com.example.flightsearch.ui.item.ItemSearchScreen

@Composable
fun FlightSearchNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToItemSearch = { navController.navigate(ItemSearchDestination.route) }
            )
        }
        composable(route = ItemSearchDestination.route) {
            ItemSearchScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}