package app.saboten.androidApp.ui.screens.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import app.saboten.androidApp.ui.screens.AppNavGraph
import app.saboten.androidApp.ui.screens.main.home.HomeScreen
import app.saboten.androidApp.ui.screens.main.notifications.NotificationsScreen
import app.saboten.androidApp.ui.screens.main.profile.ProfileScreen

fun NavGraphBuilder.mainScreenRoutes() {

    navigation(AppNavGraph.Main.Home.route, AppNavGraph.Main.route) {

        composable(AppNavGraph.Main.Home.route) {
            HomeScreen()
        }

        composable(AppNavGraph.Main.Profile.route) {
            ProfileScreen()
        }

        composable(AppNavGraph.Main.Notifications.route) {
            NotificationsScreen()
        }

    }

}