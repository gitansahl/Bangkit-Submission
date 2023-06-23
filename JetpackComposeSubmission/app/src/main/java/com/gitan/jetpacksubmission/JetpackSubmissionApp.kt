package com.gitan.jetpacksubmission

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gitan.jetpacksubmission.ui.components.TopBar
import com.gitan.jetpacksubmission.ui.navigation.Screen
import com.gitan.jetpacksubmission.ui.screen.detail.DetailScreen
import com.gitan.jetpacksubmission.ui.screen.home.HomeScreen
import com.gitan.jetpacksubmission.ui.screen.profile.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetpackSubmissionApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            if (currentRoute == Screen.Home.route) {
                TopBar(onProfileClick = {
                    navController.navigate(Screen.Profile.route)
                })
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navigateToDetail = { castId ->
                    navController.navigate(Screen.DetailCast.createRoute(castId))
                })
            }
            composable(Screen.Profile.route) {
                ProfileScreen(navigateBack = {
                    navController.navigateUp()
                })
            }
            composable(
                route = Screen.DetailCast.route,
                arguments = listOf(navArgument("castId") {
                    type = NavType.LongType
                })
            ) {
                val id = it.arguments?.getLong("castId") ?: -1L
                DetailScreen(
                    castId = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}