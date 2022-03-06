@file:OptIn(ExperimentalMaterialNavigationApi::class)

package app.saboten.androidUiSamples

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.plusAssign
import app.saboten.androidUi.motions.materialTransitionZaxisIn
import app.saboten.androidUi.motions.materialTransitionZaxisOut
import app.saboten.androidUiSamples.screens.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

@Composable
fun UiSamplesApp(viewModel: UiSamplesViewModel) {

    val navController = rememberAnimatedNavController()
    val bottomSheetNavigator = rememberBottomSheetNavigator()

    navController.navigatorProvider += bottomSheetNavigator

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetBackgroundColor = Color.Unspecified
    ) {
        Scaffold {

            AnimatedNavHost(
                navController = navController,
                startDestination = UiSamplesAppRoutes.Home.route,
                enterTransition = { materialTransitionZaxisIn },
                exitTransition = { materialTransitionZaxisOut }
            ) {

                composable(UiSamplesAppRoutes.Home.route) {
                    HomeScreen(navController, viewModel)
                }

                composable(UiSamplesAppRoutes.Buttons.route) {
                    ButtonsScreen(navController)
                }

                composable(UiSamplesAppRoutes.Dialogs.route) {
                    DialogsScreen(navController)
                }

                composable(UiSamplesAppRoutes.Colors.route) {
                    ColorsScreen(navController)
                }

                composable(UiSamplesAppRoutes.Typographies.route) {
                    TypographiesScreen(navController)
                }

                composable(UiSamplesAppRoutes.TextFields.route) {
                    TextFieldsScreen(navController)
                }

                composable(UiSamplesAppRoutes.Lists.route) {
                    ListsScreen(navController)
                }

            }

        }
    }

}