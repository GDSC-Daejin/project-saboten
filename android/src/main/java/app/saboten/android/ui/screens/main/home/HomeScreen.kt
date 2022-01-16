package app.saboten.android.ui.screens.main.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import commonClient.extension.extract
import commonClient.presentation.HomeScreenViewModel
import commonClient.presentation.HomeScreenViewModelDelegate

@Composable
fun HomeScreen(vm: HomeScreenViewModelDelegate = hiltViewModel<HomeScreenViewModel>()) {

    val (state, effect, event) = vm.extract()

}