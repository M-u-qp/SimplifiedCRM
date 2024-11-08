package com.example.simplifiedcrm.ui.navigation.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.simplifiedcrm.ui.navigation.Navigator
import com.example.simplifiedcrm.ui.screens.onboarding.OnboardingScreen

@Composable
fun NavGraph(startDestination: String) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnboardingScreen.route
        ) {
            composable(route = Route.OnboardingScreen.route) {
                OnboardingScreen()
            }
        }
        navigation(
            route = Route.Navigation.route,
            startDestination = Route.StartNavigator.route
        ) {
            composable(route = Route.StartNavigator.route) {
                Navigator()
            }
        }
    }
}