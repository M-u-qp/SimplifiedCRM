package com.example.simplifiedcrm.ui.navigation.navgraph

sealed class Route(val route: String) {
    data object OnboardingScreen : Route(route = "onboardingScreen")
    data object HomeScreen : Route(route = "homeScreen")
    data object ProfileScreen : Route(route = "profileScreen")
    data object SettingsScreen : Route(route = "settingsScreen")
    data object AppStartNavigation : Route(route = "appStartNavigation")
    data object Navigation : Route(route = "navigation")
    data object StartNavigator : Route(route = "startNavigator")

}