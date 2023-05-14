package commonClient.presentation

import common.coroutines.PlatformDispatchers
import commonClient.data.LoadState
import commonClient.data.isFailed
import commonClient.data.isSuccess
import commonClient.data.map
import commonClient.domain.entity.settings.AppTheme
import commonClient.domain.entity.user.UserInfo
import commonClient.domain.usecase.auth.RefreshTokenUseCase
import commonClient.domain.usecase.settings.ObserveAppThemeSettingsUseCase
import commonClient.domain.usecase.user.GetMeUseCase
import commonClient.domain.usecase.user.ObserveMeUseCase
import commonClient.utils.toLoadState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

data class GlobalAppState(
    val appTheme: AppTheme = AppTheme.SYSTEM,
    val meState: LoadState<UserInfo?> = LoadState.idle(),
    val appLoadingState: LoadState<Unit> = LoadState.idle()
)

sealed interface GlobalAppSideEffect {
    object ShowNetworkErrorUi : GlobalAppSideEffect
}

class GlobalAppViewModel(
    private val getMeUseCase: GetMeUseCase,
    private val observeMeUseCase: ObserveMeUseCase,
    private val observeAppThemeSettingsUseCase: ObserveAppThemeSettingsUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase
) : PlatformViewModel<GlobalAppState, GlobalAppSideEffect>() {

    override val container: Container<GlobalAppState, GlobalAppSideEffect> = container(GlobalAppState())

    init {
        initializeApp()
    }

    /*
    * 앱이 처음 켜질때 필요한 비동기 로직들을 작성합니다.
    * 이 부분은 앱이 처음 실행될 때만 실행됩니다.
    */
    private fun initializeApp() = intent {

        platformViewModelScope.launch {
            observeMeUseCase()
                .toLoadState()
                .onEach { reduce { state.copy(meState = it) } }
                .collect()
        }

        platformViewModelScope.launch {
            observeAppThemeSettingsUseCase()
                .filterNotNull()
                .onEach { reduce { state.copy(appTheme = it) } }
                .collect()
        }

        platformViewModelScope.launch {
            refreshTokenUseCase(true)
                .toLoadState()
                .onEach { refreshTokenState ->

                    if (refreshTokenState.isFailed()) {
                        postSideEffect(GlobalAppSideEffect.ShowNetworkErrorUi)
                    }

                    if (refreshTokenState.isSuccess()) {
                        kotlin.runCatching { getMeUseCase() }
                    }

                    reduce { state.copy(appLoadingState = refreshTokenState.map { }) }
                }
                .collect()
        }
    }

    fun retry() = intent {
        initializeApp()
    }

}