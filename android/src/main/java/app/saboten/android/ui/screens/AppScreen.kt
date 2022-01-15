@file:OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)

package app.saboten.android.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.plusAssign
import app.saboten.android.ui.providers.ProvideNavController
import app.saboten.android.ui.screens.login.LoginScreen
import app.saboten.android.ui.screens.main.mainScreenRoutes
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

@Composable
fun AppScreen() {

    val navController = rememberAnimatedNavController()
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    navController.navigatorProvider += bottomSheetNavigator

    ProvideNavController(navController) {
        ModalBottomSheetLayout(bottomSheetNavigator) {
            AnimatedNavHost(
                navController,
                startDestination = AppNavGraph.Login.route
            ) {

                composable(AppNavGraph.Login.route) { LoginScreen() }

                mainScreenRoutes()

            }
        }
    }

}