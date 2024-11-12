package com.example.simplifiedcrm.ui.navigation.navgraph

sealed class Route(val route: String) {
    data object OnboardingScreen : Route(route = "onboardingScreen")
    data object HomeScreen : Route(route = "homeScreen")
    data object TasksScreen : Route(route = "tasksScreen")
    data object ProfileScreen : Route(route = "profileScreen")
    data object TaskCreationScreen : Route(route = "taskCreationScreen")
    data object SettingsScreen : Route(route = "settingsScreen")
    data object AppStartNavigation : Route(route = "appStartNavigation")
}