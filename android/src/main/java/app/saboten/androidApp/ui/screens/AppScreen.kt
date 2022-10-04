@file:OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)

package app.saboten.androidApp.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import app.saboten.androidApp.ui.NavGraphs
import app.saboten.androidApp.ui.destinations.*
import app.saboten.androidApp.ui.navDestination
import app.saboten.androidApp.ui.screens.main.MainBottomNavigation
import app.saboten.androidUi.motions.materialTransitionZaxisIn
import app.saboten.androidUi.motions.materialTransitionZaxisOut
import app.saboten.androidUi.scaffolds.BasicScaffold
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import androidx.compose.animation.*
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import app.saboten.androidApp.extensions.collectInLaunchedEffect
import com.ramcosta.composedestinations.navigation.DependenciesContainerBuilder
import com.ramcosta.composedestinations.navigation.dependency
import commonClient.presentation.AppViewModel
import commonClient.presentation.AppViewModelDelegate
import commonClient.presentation.HomeScreenViewModelDelegate

@Composable
fun AppScreen(appViewModel: AppViewModel) {

    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberAnimatedNavController(bottomSheetNavigator)

    ModalBottomSheetLayout(bottomSheetNavigator) {
        MainDestinationScaffold(appViewModel, navController) {
            dependency(appViewModel)
        }
    }

}

@Composable
private fun MainDestinationScaffold(
    appViewModel: AppViewModel,
    navController: NavHostController,
    dependenciesContainerBuilder: @Composable DependenciesContainerBuilder<*>.() -> Unit,
) {

    val animatedNavHostEngine = rememberAnimatedNavHostEngine(
        rootDefaultAnimations = RootNavGraphDefaultAnimations(
            enterTransition = { materialTransitionZaxisIn },
            exitTransition = { materialTransitionZaxisOut },
            popEnterTransition = { materialTransitionZaxisIn },
            popExitTransition = { materialTransitionZaxisOut }
        )
    )

    val currentBackStackEntryAsState by navController.currentBackStackEntryAsState()

    val destination = currentBackStackEntryAsState?.navDestination

    val isBottomBarVisible = remember(destination) {
        destination != null && destination in listOf(
            HomeScreenDestination,
            ProfileScreenDestination,
            CategoryScreenDestination,
            NotificationScreenDestination
        )
    }

    appViewModel.effect.collectInLaunchedEffect {
        when (it) {
            AppViewModelDelegate.Effect.ShowNetworkErrorUi -> {
                navController.navigate(AppInitializeFailedDialogDestination.route)
            }
        }
    }

    BasicScaffold(
        bottomBar = {
            AnimatedVisibility(
                isBottomBarVisible,
                enter = slideInVertically { it },
                exit = slideOutVertically { it }
            ) {
                MainBottomNavigation(navController, destination)
            }
        }
    ) {

        DestinationsNavHost(
            NavGraphs.root,
            engine = animatedNavHostEngine,
            navController = navController,
            dependenciesContainerBuilder = dependenciesContainerBuilder
        )

    }

}