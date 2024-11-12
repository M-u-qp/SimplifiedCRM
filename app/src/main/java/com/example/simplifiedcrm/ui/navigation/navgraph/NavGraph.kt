package com.example.simplifiedcrm.ui.navigation.navgraph

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import com.example.simplifiedcrm.R
import com.example.simplifiedcrm.ui.navigation.BottomNavigation
import com.example.simplifiedcrm.ui.navigation.BottomNavigationItem
import com.example.simplifiedcrm.ui.screens.home.HomeScreen
import com.example.simplifiedcrm.ui.screens.onboarding.OnboardingScreen
import com.example.simplifiedcrm.ui.screens.onboarding.OnboardingViewModel
import com.example.simplifiedcrm.ui.screens.profile.ProfileScreen
import com.example.simplifiedcrm.ui.screens.profile.ProfileViewModel
import com.example.simplifiedcrm.ui.screens.task_creation.TaskCreationScreen
import com.example.simplifiedcrm.ui.screens.task_creation.TaskCreationViewModel
import com.example.simplifiedcrm.ui.screens.tasks.TasksScreen

@Composable
fun NavGraph(navController: NavHostController) {

    val homeText = stringResource(id = R.string.home)
    val tasksText = stringResource(id = R.string.tasks)
    val profileText = stringResource(id = R.string.profile)
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.icons8_home, text = homeText),
            BottomNavigationItem(icon = R.drawable.icons8_tasks, text = tasksText),
            BottomNavigationItem(icon = R.drawable.icons8_user, text = profileText)
        )
    }
    val backstackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    selectedItem = remember(key1 = backstackState) {
        when (backstackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.TasksScreen.route -> 1
            Route.ProfileScreen.route -> 2
            else -> 0
        }
    }
    val isBottomBarVisible = remember(key1 = backstackState) {
        backstackState?.destination?.route == Route.HomeScreen.route ||
                backstackState?.destination?.route == Route.TasksScreen.route ||
                backstackState?.destination?.route == Route.ProfileScreen.route
    }

    Scaffold(
        bottomBar = {
            if (isBottomBarVisible) {
                BottomNavigation(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 10.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    items = bottomNavigationItems,
                    selected = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTab(
                                navController = navController,
                                route = Route.HomeScreen.route
                            )

                            1 -> navigateToTab(
                                navController = navController,
                                route = Route.TasksScreen.route
                            )

                            2 -> navigateToTab(
                                navController = navController,
                                route = Route.ProfileScreen.route
                            )
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            if (isBottomBarVisible) {
                FloatingActionButton(
                    onClick = { navController.navigate(Route.TaskCreationScreen.route) },
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(id = R.drawable.icons8_plus),
                        contentDescription = null
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            CompositionLocalProvider(
                value = compositionLocalOf { 0.dp } provides paddingValues.calculateBottomPadding()
            ) {

                NavHost(
                    navController = navController,
                    startDestination = Route.AppStartNavigation.route
                ) {
                    navigation(
                        route = Route.AppStartNavigation.route,
                        startDestination = Route.OnboardingScreen.route
                    ) {
                        composable(route = Route.OnboardingScreen.route) {
                            val viewModel: OnboardingViewModel = hiltViewModel()
                            OnboardingScreen(
                                viewModel = viewModel,
                                navigateToHome = { navController.navigate(Route.HomeScreen.route) },
                                event = viewModel
                            )
                        }
                        composable(route = Route.HomeScreen.route) {
                            HomeScreen()
                        }
                        composable(route = Route.TasksScreen.route) {
                            TasksScreen()
                        }
                        composable(route = Route.ProfileScreen.route) {
                            val viewModel: ProfileViewModel = hiltViewModel()
                            ProfileScreen(
                                viewModel = viewModel,
                                navigateToLogin = { navController.navigate(Route.AppStartNavigation.route) }
                            )
                        }
                        composable(route = Route.TaskCreationScreen.route) {
                            val viewModel: TaskCreationViewModel = hiltViewModel()
                            TaskCreationScreen(
                                viewModel = viewModel,
                                event = viewModel,
                                navigateToHome = { navController.navigate(Route.HomeScreen.route) },
                                navigateUp = { navController.navigateUp() }
                            )
                        }
                    }

                }
            }
        }
    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screen ->
            popUpTo(screen) { saveState = true }
            restoreState = true
            launchSingleTop = true
        }
    }
}




