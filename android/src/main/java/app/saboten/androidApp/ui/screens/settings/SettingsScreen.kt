package app.saboten.androidApp.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import app.saboten.androidApp.extensions.extract
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.scaffolds.BasicScaffold
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import commonClient.domain.entity.settings.AppTheme
import commonClient.presentation.SettingsScreenViewModel
import commonClient.presentation.SettingsScreenViewModelDelegate

@Composable
@Destination
fun SettingsScreen(
    navigator: DestinationsNavigator
) {
    val viewModel: SettingsScreenViewModel = hiltViewModel()
    SettingsScreenContent(viewModel)
}

@Composable
fun SettingsScreenContent(
    vm: SettingsScreenViewModelDelegate = hiltViewModel<SettingsScreenViewModel>()
) {

    val (state, effect, event) = vm.extract()

    BasicScaffold(
        topBar = {
            BasicTopBar({

            })
        }
    ) {
        Column {

            Button({
                event(SettingsScreenViewModelDelegate.Event.ChangeTheme(AppTheme.SYSTEM))
            }) {
                Text(
                    "System"
                )
            }
            Button({
                event(
                    SettingsScreenViewModelDelegate.Event.ChangeTheme(
                        if (state.appTheme == AppTheme.LIGHT) AppTheme.DARK else AppTheme.LIGHT
                    )
                )
            }) {
                Text(
                    if (state.appTheme == AppTheme.LIGHT) "Switch to Dark" else "Switch to Light"
                )
            }

        }
    }

}