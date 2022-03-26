package commonClient.presentation

import common.model.reseponse.category.Category
import common.model.reseponse.user.UserInfo
import commonClient.data.LoadState
import commonClient.di.HiltViewModel
import commonClient.di.Inject
import commonClient.domain.entity.AppTheme
import commonClient.domain.usecase.category.GetCategoriesUseCase
import commonClient.domain.usecase.settings.ObserveAppThemeSettingsUseCase
import commonClient.domain.usecase.user.ObserveMeUseCase
import commonClient.presentation.AppViewModelDelegate.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


interface AppViewModelDelegate : UnidirectionalViewModelDelegate<State, Effect, Event> {

    data class State(
        val me: UserInfo? = null,
        val appTheme: AppTheme = AppTheme.SYSTEM,
        val categoriesState: LoadState<List<Category>> = LoadState.loading()
    )

    sealed class Effect {

    }

    sealed class Event {

    }

}

@HiltViewModel
class AppViewModel @Inject constructor(
    observeMeUseCase: ObserveMeUseCase,
    observeAppThemeSettingsUseCase: ObserveAppThemeSettingsUseCase,
    getCategoriesUseCase: GetCategoriesUseCase
) : PlatformViewModel(), AppViewModelDelegate {

    private val effectChannel = Channel<Effect>(Channel.UNLIMITED)
    override val effect: Flow<Effect> = effectChannel.receiveAsFlow()

    override val state: StateFlow<State> = combine(
        observeMeUseCase(),
        observeAppThemeSettingsUseCase(),
        getCategoriesUseCase()
    ) { me, appTheme, categoriesState ->
        State(
            me = me,
            appTheme = appTheme,
            categoriesState = categoriesState
        )
    }.distinctUntilChanged().asStateFlow(State(), platformViewModelScope)

    override fun event(e: Event) {
        platformViewModelScope.launch {

        }
    }

}