package commonClient.presentation

import common.model.reseponse.category.Category
import common.model.reseponse.user.User
import common.model.reseponse.user.UserInfo
import commonClient.data.LoadState
import commonClient.di.HiltViewModel
import commonClient.di.Inject
import commonClient.domain.entity.AppTheme
import commonClient.domain.usecase.category.GetCategoriesUseCase
import commonClient.domain.usecase.settings.ObserveAppThemeSettingsUseCase
import commonClient.domain.usecase.settings.UpdateAppThemeSettingsUseCase
import commonClient.domain.usecase.user.GetMeUseCase
import commonClient.presentation.HomeScreenViewModelDelegate.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

interface HomeScreenViewModelDelegate : UnidirectionalViewModelDelegate<State, Effect, Event> {

    data class State(
        val categories: LoadState<List<Category>> = LoadState.loading(),
    )

    sealed class Effect {

    }

    sealed class Event {
        object ReloadContents : Event()
    }

}

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase
) : PlatformViewModel(), HomeScreenViewModelDelegate {

    private val effectChannel = Channel<Effect>(Channel.UNLIMITED)
    override val effect: Flow<Effect> = effectChannel.receiveAsFlow()

    override val state: StateFlow<State> =
        combine(getCategoriesUseCase(), flowOf(true)) { categories, _ ->
            State(categories = categories)
        }.asStateFlow(State(), platformViewModelScope)

    override fun event(e: Event) {
        platformViewModelScope.launch {
            when (e) {
                Event.ReloadContents -> {

                }
            }
        }
    }

}