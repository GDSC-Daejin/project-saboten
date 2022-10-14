package commonClient.presentation

import common.coroutines.PlatformDispatchers
import commonClient.data.LoadState
import commonClient.data.isFailed
import commonClient.data.map
import commonClient.domain.entity.settings.AppTheme
import commonClient.domain.entity.user.UserInfo
import commonClient.domain.usecase.auth.RefreshTokenUseCase
import commonClient.domain.usecase.settings.ObserveAppThemeSettingsUseCase
import commonClient.domain.usecase.user.ObserveMeUseCase
import commonClient.presentation.AppViewModelDelegate.*
import commonClient.utils.toLoadState
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*


interface AppViewModelDelegate : UnidirectionalViewModelDelegate<State, Effect, Event> {

    data class State(
        val me: UserInfo? = null,
        val appTheme: AppTheme = AppTheme.SYSTEM,
        val appLoadingState: LoadState<Unit> = LoadState.loading()
    )

    sealed class Effect {
        object ShowNetworkErrorUi : Effect()
    }

    sealed class Event {
        object Retry : Event()
    }

}

class AppViewModel(
    observeMeUseCase: ObserveMeUseCase,
    observeAppThemeSettingsUseCase: ObserveAppThemeSettingsUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase
) : PlatformViewModel(), AppViewModelDelegate {

    private val effectChannel = Channel<Effect>(Channel.UNLIMITED)
    override val effect: Flow<Effect> = effectChannel.receiveAsFlow()

    private val appLoadingState = MutableStateFlow<LoadState<Unit>>(LoadState.loading())

    override val state: StateFlow<State> = combine(
        observeMeUseCase(),
        observeAppThemeSettingsUseCase(),
        appLoadingState
    ) { me, appTheme, appLoadingState ->
        State(
            me = me,
            appTheme = appTheme,
            appLoadingState = appLoadingState
        )
    }.distinctUntilChanged().asStateFlow(State(), platformViewModelScope)

    init {
        initializeApp()
    }

    /*
    * 앱이 처음 켜질때 필요한 비동기 로직들을 작성합니다.
    * 이 부분은 앱이 처음 실행될 때만 실행됩니다.
    */
    private fun initializeApp() {
        platformViewModelScope.launch(PlatformDispatchers.IO) {
            refreshTokenUseCase(true)
                .toLoadState()
                .onEach { refreshTokenState ->
                    if (refreshTokenState.isFailed()) effectChannel.send(Effect.ShowNetworkErrorUi)
                    appLoadingState.emit(refreshTokenState.map { })
                }
                .collect()
        }
    }

    override fun event(e: Event) {
        when (e) {
            Event.Retry -> initializeApp()
        }
    }

}