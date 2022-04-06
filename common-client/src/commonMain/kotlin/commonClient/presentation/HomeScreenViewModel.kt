package commonClient.presentation

import common.model.reseponse.category.Category
import commonClient.data.LoadState
import commonClient.di.HiltViewModel
import commonClient.di.Inject
import commonClient.domain.usecase.category.GetCategoriesUseCase
import commonClient.presentation.HomeScreenViewModelDelegate.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

interface HomeScreenViewModelDelegate : UnidirectionalViewModelDelegate<State, Effect, Event> {

    data class State(
        val categoriesState: LoadState<List<Category>> = LoadState.Loading(),
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

    override val state: StateFlow<State> = combine(
        getCategoriesUseCase(), flowOf(true)
    ) { categoriesState, _ ->
        State(
            categoriesState = categoriesState
        )
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